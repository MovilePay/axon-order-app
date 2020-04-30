# Example of CQRS and Event Sourcing with Axon Framework

## Presentation
Video: https://www.youtube.com/watch?v=K6JKX1URZgc

Slide: https://www.slideshare.net/acgarciacampos/cqrs-and-event-sourcing-with-axon-framework

## Up Axon Server before run services
```
docker run -d -p 8024:8024 -p 8124:8124 -p 8224:8224 --name axonserver axoniq/axonserver
```

## APIs

### Create Order
```
curl --location --request POST 'http://localhost:8087/orders' \
--header 'Content-Type: application/json' \
--data-raw '{
    "customerName": "Diego",
    "address": "Rua Ribas do Rio Pardo"
}' -s | jq '.'
```

### Add Item
```
curl --location --request POST 'http://localhost:8087/orders/{order_id}/items' \
--header 'Content-Type: application/json' \
--data-raw '{
        "name": "Paper",
        "amount": 20
}' -s | jq '.'
```

### Remove Item
```
curl --location --request DELETE 'http://localhost:8087/orders/1a9cb9a5-7344-4cf8-b1c2-98cdbb313142/items/e020daab-45e1-456e-9253-fe222de96cf6' -s | jq '.'
```

### Confirm Order
```
curl --location --request PUT 'http://localhost:8087/orders/{order_id}/confirmations' -s | jq '.'
```

### Cancel Order
```
curl --location --request DELETE 'http://localhost:8087/orders/{order_id}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "movilepay"
}' -s | jq '.'
```

### Get All Orders
```
curl --location --request GET 'http://localhost:8088/orders' -s | jq '.'
```

### Get Order by ID
```
curl --location --request GET 'http://localhost:8088/orders/{order_id}' -s | jq '.'
```

### Get Order by Status
```
curl --location --request GET 'http://localhost:8088/orders?status=CONFIRMED' -s | jq '.'
```
