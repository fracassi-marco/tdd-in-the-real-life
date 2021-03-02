<html>
    <body>
    <h1>Cart</h1>
    <ul>
        <#list products as product>
        <li>${product.name} - ${product.price}€</li>
        </#list>
    </ul>
    </body>
</html>