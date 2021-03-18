package tdd.reallife

import daikon.HttpServer
import daikon.core.HttpStatus.OK_200
import io.github.bonigarcia.wdm.WebDriverManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By.className
import org.openqa.selenium.By.id
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions

class EndToEndTests {

    private var app = App()
    private val s3Endpoint = "http://localhost:4566"
    private val connection = DbConnection("localhost:5432")
    private lateinit var browser: WebDriver

    @BeforeEach
    fun beforeEach() {
        WebDriverManager.firefoxdriver().setup()
        browser = FirefoxDriver(FirefoxOptions().setHeadless(true))
        app.start()
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
        browser.get("http://localhost:4545/")
        browser.findElement(id("button-Potato")).click()
        assertThat(browser.findElements(className("cart-item")).single().text).isEqualTo("Potato")

        browser.findElement(id("button-cash")).click()
        assertThat(browser.findElements(className("cart-item"))).isEmpty()
    }

    @Test
    fun payByExternalSystem() {
        HttpServer(4546) {
            post("/") { _, res, _ -> res.status(OK_200) }
        }.start().use {
            browser.get("http://localhost:4545/")
            browser.findElement(id("button-Potato")).click()
            assertThat(browser.findElements(className("cart-item")).single().text).isEqualTo("Potato")

            browser.findElement(id("button-external")).click()
            assertThat(browser.findElement(id("button-external"))).isNotNull
            assertThat(browser.findElements(className("cart-item"))).isEmpty()

            S3Object(s3Endpoint, "bills", "bill_1.txt").assertExists()
        }
    }

    private fun insertProduct(name: String) {
        connection.create().use {
            it.prepareStatement("INSERT INTO products VALUES ('$name', 'very good', 'http://any.img')").execute()
        }
    }

    private fun deleteProduct(name: String) {
        connection.create().use {
            it.prepareStatement("DELETE FROM products WHERE name = '$name'").execute()
        }
    }
}