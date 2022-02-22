<!DOCTYPE html>
<html>
    <head>
        <title>Fair Trade Garden</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <style>
        .payment-info {
            background: blue;
            padding: 10px;
            border-radius: 6px;
            color: #fff;
            font-weight: bold
        }

        .product-details {
            padding: 10px
        }

        body {
            background: #eee
        }

        .cart {
            background: #fff
        }

        .p-about {
            font-size: 12px
        }

        .table-shadow {
            -webkit-box-shadow: 5px 5px 15px -2px rgba(0, 0, 0, 0.42);
            box-shadow: 5px 5px 15px -2px rgba(0, 0, 0, 0.42)
        }

        .type {
            font-weight: 400;
            font-size: 10px
        }

        label.radio {
            cursor: pointer
        }

        label.radio input {
            position: absolute;
            top: 0;
            left: 0;
            visibility: hidden;
            pointer-events: none
        }

        label.radio span {
            padding: 1px 12px;
            border: 2px solid #ada9a9;
            display: inline-block;
            color: #8f37aa;
            border-radius: 3px;
            text-transform: uppercase;
            font-size: 11px;
            font-weight: 300
        }

        label.radio input:checked+span {
            border-color: #fff;
            background-color: blue;
            color: #fff
        }

        .credit-inputs {
            background: rgb(102, 102, 221);
            color: #fff !important;
            border-color: rgb(102, 102, 221)
        }

        .credit-inputs::placeholder {
            color: #fff;
            font-size: 13px
        }

        .credit-card-label {
            font-size: 9px;
            font-weight: 300
        }

        .form-control.credit-inputs:focus {
            background: rgb(102, 102, 221);
            border: rgb(102, 102, 221)
        }

        .line {
            border-bottom: 1px solid rgb(102, 102, 221)
        }

        .information span {
            font-size: 12px;
            font-weight: 500
        }

        .information {
            margin-bottom: 5px
        }

        .items {
            -webkit-box-shadow: 5px 5px 4px -1px rgba(0, 0, 0, 0.25);
            box-shadow: 5px 5px 4px -1px rgba(0, 0, 0, 0.08)
        }

        .spec {
            font-size: 11px
        }
        </style>
    </head>
    <body>
    <div class="container mt-5 p-3 rounded cart">
        <div class="row no-gutters">
            <div class="col-md-8">
                <div class="product-details mr-2">
                    <h1 class="mb-0">Fair Trade Garden</h1>
                    <#list products as product>
                    <div class="d-flex justify-content-between align-items-center mt-3 p-2 items rounded">
                        <div class="d-flex flex-row"><img class="rounded" src="${product.image}" width="80">
                            <div class="ml-2">
                                <span class="font-weight-bold d-block">${product.name}</span>
                                <span class="spec">${product.description}</span>
                                <form method="post" action="add-to-cart">
                                    <input type="hidden" name="name" value="${product.name}">
                                    <input type="hidden" name="price" value="${product.price}"">
                                    <button type="submit" class="btn btn-primary" id="button-${product.name}">+</button>
                                </form>
                            </div>
                        </div>
                        <div class="d-flex flex-row align-items-center"><span class="d-block ml-5 font-weight-bold">${product.price}€</span></div>
                    </div>
                    </#list>
                </div>
            </div>
            <div class="col-md-4">
                <div class="payment-info">
                    <h2>Cart</h2>
                    <#list cartItems as item>
                    <div class="d-flex justify-content-between information"><span class="cart-item">${item.name}</span><span>${item.price}€</span></div>
                    </#list>
                    <hr>
                    <div class="d-flex justify-content-between information"><span style="font-size:1.2em;">TOTAL</span><span id="total" style="font-size:1.2em;">${total}€</span></div>
                    <form method="post" action="/pay-by-cash">
                        <input type="hidden" name="price" value="${total}">
                        <button id="button-cash" class="btn btn-primary btn-block d-flex justify-content-between mt-3" type="submit"><span>Pay by cash<i class="fa fa-long-arrow-right ml-1"></i></span></button>
                    </form>
                    <form method="post" action="/pay-external">
                        <input type="hidden" name="price" value="${total}">
                        <button id="button-external" class="btn btn-primary btn-block d-flex justify-content-between mt-3" type="submit"><span>Pay by card<i class="fa fa-long-arrow-right ml-1"></i></span></button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>

