# TDD in the real life

Initial scenario:
* read products from DB
* retrieve price from another service

Desired features:
* pay with external service
* if pay succeeded save bill results on S3
* send message `payment_succedeed` on SQS `events_queue`
