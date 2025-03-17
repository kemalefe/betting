
# Sample Betting Application

This Spring Boot app is a sample sports events betting application that allows you to manage betting odds for various match events. It also handles the creation, update, and finalization of bet slips. The system uses Caffeine as an in-memory cache to store event data and betting odds. The application integrates with external event services and includes features for caching, asynchronous processing, and event updates.

Features:
* Manage event betting odds.
* Handles betting odds changes asynchronously.
* Create and finalize bet slips.
* Stream real-time betting odds updates using WebFlux.
* Handle validation for bet amounts and coupon counts.

## Prerequisites
Before running this application, ensure that the following are installed:

* Java 17 or later
* Gradle
* H2DB or your preferred database for persistence (if applicable)

## Installation

#### 1. Clone the repository

```bash
git clone https://github.com/kemalefe/betting.git
```

#### 2. Install dependencies and build
```bash
gradle clean build --refresh-dependencies
```

## Configuration

The application provides configuration options through the application.properties or application.yml file. Here, you can set values for server ports, database connections, and caching properties. All betting application specific properties placed below property.

```yaml
betting:
  bet-finalize-timeout: 2 # as seconds
  max-coupon-count: 500
  max-total-bet-amount: 10000
  odds:
    min-value: 1.5
    max-value: 3.0
    update-interval: 1000 # as milliseconds
  producer:
    threads: 5
  consumer:
    threads: 10
    prefetch-count: 10
  bulletin:
    duration: 1 # as seconds
```

## Running the Application
Run with Gradle:
```bash
gradle bootRun
```
Alternatively, you can build the JAR file and run it directly:
```bash
java -jar build/libs/betting-0.0.1-SNAPSHOT.jar 
```

## Testing
Unit Tests
To run unit tests:
```bash
gradle test
```
Unit tests are available for services and controllers to verify the core logic and APIs.

## API Reference

#### 1. Get Bulletin
Streams real-time bulletin updates. It provides updates at a regular interval based (1 sec.) on the configured duration.
```http
GET /api/bulletin
```

#### 2. Get Events
Retrieves a list of all events.

```http
GET /api/events
```
Response:
```json
[
  {
    "id": 1,
    "leagueName": "Premier League",
    "homeTeam": "Manchester United",
    "awayTeam": "Chelsea",
    "matchStartTime": "2024-03-15T20:00:00",
    "betOddsDto": {
    "eventId": 1,
    "homeWinBetOdds": 2.13,
    "drawBetOdds": 1.85,
    "awayWinBetOdds": 1.53
    }
  },
  {...}
]
```

#### 3. Get Event
Retrieves a specific event by its ID.

```http
GET /api/events/{id}
```
Response:
```json
{
    "id": 1,
    "leagueName": "Premier League",
    "homeTeam": "Manchester United",
    "awayTeam": "Chelsea",
    "matchStartTime": "2024-03-15T20:00:00",
    "betOddsDto": {
      "eventId": 1,
      "homeWinBetOdds": 2.13,
      "drawBetOdds": 1.85,
      "awayWinBetOdds": 1.53
    }
}
```

#### 3. Add Event
Adds a new event to the system.

```http
POST /api/events
```
Request:
```json
{
  "leagueName": "Premier League",
  "homeTeam": "Manchester United",
  "awayTeam": "Chelsea",
  "matchStartTime": "2024-04-15T20:00:00",
  "betOddsDto": {
    "homeWinBetOdds": 1.72,
    "drawBetOdds": 2.21,
    "awayWinBetOdds": 1.87
  }
}
```
Response:
```json
{
    "id": 1,
    "leagueName": "Premier League",
    "homeTeam": "Manchester United",
    "awayTeam": "Chelsea",
    "matchStartTime": "2024-03-15T20:00:00",
    "betOddsDto": {
    "eventId": 1,
    "homeWinBetOdds": 2.13,
    "drawBetOdds": 1.85,
    "awayWinBetOdds": 1.53
    }
}
```
#### 3. Bet Slip Initialize
Initializes a bet slip with the provided customer and bet slip data.
The betOdds in the request is the last updated bet odds value seen by customer.
If that value has been changed during initialization request sent to server, system will respond with changed bet odds value.
If customer accepts new changed odds rate, he/she could proceed to bet slip finalization. The ```betOddsChanged``` field indicates the change.

```http
POST /api/bet-slip/initialize
```
| Header          | Type   | Description               |
|:----------------|:-------|:--------------------------|
| `x-customer-id` | `Long` | **Required**. Customer ID |

Request:
```json
{
  "eventId": 3,
  "betType": "DRAW",
  "betOdds": 1.55,
  "couponCount": 20,
  "betAmount": 50.00,
  "currencyCode": "TL"
}
```
Response: 
```json
{
  "inquiryId":"ae1158b4-f603-4d9b-b155-70f2338b36d5",
  "betOddsChanged":true,
  "timeoutSeconds":2,
  "betSlipDto":{
    "id":null,
    "customerId":null,
    "eventId":3,
    "betType":"DRAW",
    "betOdds":1.73,
    "couponCount":20,
    "betAmount":50.00,
    "currencyCode":"TL"
  }
}
```
#### 3. Bet Slip Finalize
Finalises the bet slip based on the given query ID. If user tries to finalise an initialised slip after n seconds (configured in application.yml), system will automatically initialise a new slip with previous slip data with updated odds. Otherwise system will return successfully created slip data.

```http
GET /api/bet-slip/finalize/{inquiry-id}
```
| Header          | Type   | Description               |
|:----------------|:-------|:--------------------------|
| `x-customer-id` | `Long` | **Required**. Customer ID |

Request:
```json
{
  "eventId": 3,
  "betType": "DRAW",
  "betOdds": 1.55,
  "couponCount": 20,
  "betAmount": 50.00,
  "currencyCode": "TL"
}
```
Response-1: Bet slip was not created, returned re-initialised bet slip with updated odds value.
```json
{
  "inquiryId":"b7b3d7a9-923f-4ac9-8e97-e2e909505eb1",
  "betOddsChanged":true,
  "timeoutSeconds":30,
  "betSlipDto":{
    "id":null,
    "customerId":null,
    "eventId":19,
    "betType":"DRAW",
    "betOdds":2.88,
    "couponCount":112,
    "betAmount":89.00,
    "currencyCode":"TL"
  },
  "success":false
}
```
Response-2: Returned successfully created bet slip
```json
{
  "inquiryId":null,
  "betOddsChanged":false,
  "timeoutSeconds":null,
  "betSlipDto":{
    "id":1,
    "customerId":1111,
    "eventId":19,
    "betType":"DRAW",
    "betOdds":2.80,
    "couponCount":112,
    "betAmount":89.00,
    "currencyCode":"TL"
  },
  "success":true
}
```