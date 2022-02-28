# Getir Test Case - Barış Aslan

Technologies: Java 11, Spring Boot, MongoDB

You can use endpoints with swagger: http://localhost:8080/swagger-ui/index.html

## Build

* In the root directory of project:

```bash
$ ./gradlew assemble
```

## Run

* In the root directory of project:

```bash
$ docker-compose up -d
```

# Usage

Authenticate with the token (Bearer) you get from the login service.

## Customer Endpoint

### Register

Method: POST

URL: localhost:8080/api/customers/register

Request Body:
```json
{
  "email": "test@gmail.com",
  "password": "123"
}
```

### Login

Method: POST

URL: localhost:8080/api/customers/login

Request Body:
```json
{
  "email": "test@gmail.com",
  "password": "123"
}
```

### Get Customer Orders

Method: GET

URL: localhost:8080/api/customers/{email_address}/orders/


## Book Endpoint

### Add Book

Method: POST

URL: localhost:8080/api/books/

Request Body:
```json
{
  "title": "Hayvanlar Çiftliği",
  "stockCount": 100,
  "price": 27.4
}
```

### Update Book Stocks

Method: PATCH

URL: localhost:8080/api/books/

Request Body:
```json
{
  "id": "621b94528ab67b3cc3b0f5eb2",
  "stockChangeCount": -2
}
```

## Order Endpoint

### Create Order

Method: POST

URL: localhost:8080/api/orders/

Request Body:
```json
{
  "customerEmail": "test@gmail.com",
  "orderItems": [
    {
      "bookId": "621be0eb9a8f897920070c01",
      "count": 1
    },
    {
      "bookId": "621be0f89a8f897920070c02",
      "count": 2
    },
    {
      "bookId": "621be1049a8f897920070c03",
      "count": 1
    }
  ]
}
```

### Get Order By Id

Method: GET

URL: localhost:8080/api/orders/{order_id}

### Get Orders Between Dates

Method: GET

URL: localhost:8080/api/orders/

Request Body:
```json
{
  "startDate": "01/01/2022 00:00:00",
  "endDate": "01/10/2022 00:00:00"
}
```

## Statistics Endpoint

### Get Monthly Order Statistics

Method: GET

URL: localhost:8080/api/statistics/
