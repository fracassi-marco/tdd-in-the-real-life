# TDD in the real life

## Initial scenario:
* read products from DB
* retrieve price from external service

## Problems:
* no tests
* mixin of data access and business logic
* random prices

## Desired features:
* pay with external service and if payment succeeded empty cart
* if payment succeeded save bill results on S3
* send message `payment_succedeed` on SQS `events_queue`
