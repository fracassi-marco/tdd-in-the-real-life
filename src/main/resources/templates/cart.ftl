<html>
    <body>
    <h1>Products</h1>
    <ul>
        <#list products as product>
        <li>${product.name} - ${product.price}€
            <form method="post" action="add-to-cart">
                <input type="hidden" name="id" value="${product.id}">
                <input type="hidden" name="name" value="${product.name}">
                <input type="hidden" name="price" value="${product.price}"">
                <button type="submit">+</button>
            </form>
        </li>
        </#list>
    </ul>
    <h1>Cart</h1>
    <ul>
        <#list cartItems as item>
        <li>${item.name} - ${item.price}€</li>
        </#list>
    </ul>
    <form method="post" action="/pay-by-cash">
        <button type="submit">Pay by cash</button>
    </form>
    </body>
</html>