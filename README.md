# Currency Conversion API Wrapper

This project is a Spring Boot application that provides an API wrapper for exchange-api. It receives a source currency code, a value, and a target currency code, calls an external API to get the exchange rate, and returns the converted value.

## Prerequisites

- Java 21
- Maven
- An internet connection to access the external exchange rate API

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/LuizSSampaio/currency-api.git
cd currency-api
```

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

## API Endpoints

### Convert Currency

**URL:** `/currency`

**Method:** `GET`

**Request Parameters:**

- `currencyCode` (String): The source currency code (e.g., "USD").
- `value` (BigDecimal): The amount to be converted.
- `targetCurrencyCode` (String): The target currency code (e.g., "EUR").

**Response:**

- `200 OK`: Returns the converted value rounded to 2 decimal places.

**Example Request:**

```bash
GET /currency?currencyCode=USD&value=100.00&targetCurrencyCode=EUR
```

**Example Response:**

```
85.00
```

## Dependencies

- Spring Boot Web
- Jackson Databind
- Java HTTP Client

## Acknowledgments

- [Exchange API](https://github.com/fawazahmed0/exchange-api) for providing the exchange rate data.