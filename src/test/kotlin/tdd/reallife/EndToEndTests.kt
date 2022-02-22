package tdd.reallife

import daikon.HttpServer
import daikon.core.HttpStatus.OK_200
import io.github.bonigarcia.wdm.WebDriverManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.By.className
import org.openqa.selenium.By.id
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

class EndToEndTests {

    private var app = App()
    private val awsEndpoint = "http://localhost:4566"
    private val connection = DbConnection("localhost:5432")
    private val sqsMessage = SqsMessage(awsEndpoint, "events_queue")
    private lateinit var browser: WebDriver

    @BeforeEach
    fun beforeEach() {
        WebDriverManager.chromedriver().setup()
        browser = ChromeDriver()
        app.start()
        sqsMessage.empty()
        DbCartRepository(connection).empty()
        insertProduct("Potato")
    }

    @AfterEach
    fun afterEach() {
        browser.close()
        app.close()
        deleteProduct("Potato")
    }

    @Test
    fun payByCash() {
        openHomePage()
        clickOn(id("button-Potato"))
        assertThatItemIsIntoCart("Potato")

        clickOn(id("button-cash"))
        assertThatCartIsEmpty()
    }

    @Test
    fun payByExternalSystem() {
        HttpServer(4546) {
            post("/") { _, res, _ -> res.status(OK_200) }
        }.start().use {
            openHomePage()
            clickOn(id("button-Potato"))
            assertThatItemIsIntoCart("Potato")

            clickOn(id("button-external"))
            assertThatCartIsEmpty()

            assertBillsExistsOnS3("bill_1.txt")
        }
    }

    @Test
    fun bigCart() {
        openHomePage()
        clickOn(id("button-Potato"))
        clickOn(id("button-Potato"))
        clickOn(id("button-Potato"))
        clickOn(id("button-Potato"))

        SqsMessage(awsEndpoint, "events_queue").assertExists("big_cart_created")
    }

    private fun clickOn(id: By?) {
        browser.findElement(id).click()
    }

    private fun openHomePage() {
        browser.get("http://localhost:4545/")
    }

    private fun insertProduct(name: String) {
        connection.create().use {
            it.prepareStatement("INSERT INTO products VALUES ('$name', 'very good', 'http://s3.amazonaws.com/pix.iemoji.com/images/emoji/apple/ios-12/256/potato.png')").execute()
        }
    }

    private fun deleteProduct(name: String) {
        connection.create().use {
            it.prepareStatement("DELETE FROM products WHERE name = '$name'").execute()
        }
    }

    private fun assertBillsExistsOnS3(fileName: String) {
        S3Object(awsEndpoint, "bills", fileName).assertExists()
    }

    private fun assertThatCartIsEmpty() {
        assertThat(browser.findElements(className("cart-item"))).isEmpty()
    }

    private fun assertThatItemIsIntoCart(item: String) {
        assertThat(browser.findElements(className("cart-item")).single().text).isEqualTo(item)
    }
}