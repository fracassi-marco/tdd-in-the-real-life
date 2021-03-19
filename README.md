# TDD in the real life

## Initial scenario:
* read products from DB
* retrieve price from external service

## Problems:
* no tests
* mixin of data access and business logic
* random prices
* OOP?

## Desired features:
* pay with external service and if payment succeeded empty cart
* if payment succeeded save bill results on S3 bucket `bills`
* if cart has more than three items send `big_cart_created` message on SQS `events_queue`
