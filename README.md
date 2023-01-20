
# Hotel Reservation API

- [Hotel Reservation API](#hotel-reservation-api)
  * [Overview](#overview)
- [Explore REST APIs](#explore-rest-apis)
  * [Hotel](#hotel)
    + [Create hotel](#create-hotel)
    + [Update existing hotel](#update-existing-hotel)
    + [Get all existing hotels](#get-all-existing-hotels)
    + [Get all available hotels](#get-all-available-hotels)
    + [Get a user specified hotel](#get-a-user-specified-hotel)
    + [Delete hotel](#delete-hotel)
  * [Reservation](#reservation)
    + [Create reservation](#create-reservation)
    + [Get all reservations](#get-all-reservations)
    + [Get reservation](#get-reservation)
    + [Delete reservation](#delete-reservation)
- [Database](#database)
  * [hotel](#hotel)
  * [reservation](#reservation)
- [Testing](#testing)
- [Tech Stack](#tech-stack)

## Overview

- This project is a Hotel Reservation `RESTful API` designed to **create hotels and associated reservations.**
- The user can create a hotel, search for available hotels, search for all hotels within the database, search all reservations within the database, make reservations to a specific hotel, delete reservations as well as hotels.
- Necessary validation is incorporated within the API that prevents illogical operations from occurring such as making an overlapping reservation to a hotel or deleting a hotel that contains reservations.
- The Hotel-Reservation-API follows a standard "`User` <-> `Controller` <-> `Validator` (Only interacts with Controller) <-> `Service` <-> `Repository` <-> `Database`" API schema.


# Explore REST APIs

##  Hotel

| Method | Endpoint | Description | Valid API Calls |
| ------ | --- | ----------- | ------------------------- |
| POST | /api/v1/hotel | Create a hotel object | [Create hotel](#create-hotel) |
| PATCH | /api/v1/hotel | Updates an existing hotel | [Update existing hotel](#update-existing-hotel) |
| GET | /api/v1/hotels | Get all existing hotels | [Get all existing hotels](#get-all-existing-hotels) |
| GET | /api/v1/hotels/availabilitySearch?dateFrom={from}&dateTo={to} | Get all available hotels between specified dates. | [Get all available hotels](#get-all-available-hotels) |
| GET | /api/v1/hotel/{id} | Get a user specified hotel  | [Get a user specified hotel](#get-a-user-specified-hotel) |
| DELETE | /api/v1/hotel/{id} | Delete a user specified Hotel  | [Delete hotel](#delete-hotel) |


### Create hotel
- Description: Create a hotel object.
- Validation (Throws an `InvalidRequestException` when):
  - Type is not DELUXE, LUXURY, or SUITE
  - Date is not YYYY-MM-DD
  - Only one date is recieved
  - `availableTo` date comes before `availableFrom` date
  - Name is `null`
- Request Body:
   ```json
      { 
      "name" : "String", 
      "type" : "String: (LUXURY, DELUXE, SUITE)", 
      "description" : "String",   
      "availableFrom" : "YYYY-MM-DD", 
      "availableTo" : "YYYY-MM-DD", 
      "status": true 
      }
   ```


### Update existing hotel
- Description: Updates an existing hotel object.
- Validation (Throws an `InvalidRequestException` when):
  - ID does not exist
  - Type is not DELUXE, LUXURY, or SUITE
  - Date is not YYYY-MM-DD
  - Only one date is recieved
  - `availableTo` date comes before `availableFrom` date
  - Name is `null`
- Request Body:
  ```json
     { 
       "id" : 0, 
       "name" : "String", 
       "type" : "String: (LUXURY, DELUXE, SUITE)", 
       "description" : "String",   
       "availableFrom" : "YYYY-MM-DD", 
       "availableTo" : "YYYY-MM-DD",
       "status": true  
     }
   ```


### Get all existing hotels
- Description: Get all existing hotels
- Validation (Throws an `InvalidRequestException` when):
  - N/A
-  Request Body: N/A


### Get all available hotels
- Description: Gets all available hotel inventories between specified dates. This endpoint takes into account pre-exisiting reservations and hotel availability dates and only returns hotels that do not have overlapping reservations and do not have availibility dates that start or end between the user specified dates.
- Validation (Throws an `InvalidRequestException` when):
  - Dates are not in YYYY-MM-DD format
  - `dateFrom` comes after `dateTo`
  - One or no dates are inputted
- Request Body:
  - Path Variables dateTo and dateFrom, in YYYY-MM-DD format


### Get a user specified hotel
- Description: Get a user specified hotel object.
- Validation (Throws an `InvalidRequestException` when):
  -  ID does not exist
-  Request Body: N/A


### Delete hotel
- Description: Delete a user specified Hotel
- Validation (Throws an `InvalidRequestException` when):
  - ID does not exist
  - Reservations for this hotel object exist (A Reservations object that contains a foreign key associated with the user inputted ID exists)
- Request Body:
  -  Path Variable Integer "id"



##  Reservation

| Method | Endpoint | Description | Valid API Calls |
| ------ | --- | ----------- | ------------------------- |
| POST | /api/v1/reservation | Creates a hotel reservation with an existing hotel id | [Create reservation](#create-reservation) |
| GET | /api/v1/reservation/{id} | Get all exsiting hotel reservations | [Get all reservations](#get-all-reservations) |
| GET | /api/v1/reservations | Get an exsiting user specified Hotel reservation  | [Get reservation](#get-reservation) |
| DELETE | /api/v1/reservation/{id} | Delete an existing user specified reservation  | [Delete reservation](#delete-reservation) |



### Create reservation
- Description: Creates a hotel reservation with an existing hotel id.
- Validation (Throws an `InvalidRequestException` when):
  - The hotel object ID does not exist (The foreign key does not exist)
  - Check in and check out dates are not in YYYY-MM-DD format
  - Check in and check out dates do not exist
  - Check in date comes after check out date
  - Number of guests is not an Integer
  - A reservation object with overlapping dates already exists for the hotel object
- Request Body:
  ```json 
    {
      "hotelId": 0,
      "checkIn": "YYYY-MM-DD",
      "checkOut": "YYYY-MM-DD",
      "guests": 0, 
      "status": true
    } 
  ```


###  Get all reservations
- Description: Get all exsiting hotel reservations.


### Get reservation
- Description: Get an exsiting user specified Hotel reservation
- Validation (Throws an `InvalidRequestException` when):
  - ID does not exist
- Request Body:
  -  Path variable Integer "id"


### Delete reservation
- Description: Delete an existing user specified reservation.
- Validation (Throws an `InvalidRequestException` when):
  - ID does not exist
- Request Body:
  - Path variable Integer "id"



# Database
- The database structure contains two tables, a `hotel` table that contains the hotel object.
- And a `reservation` table that contains reservations objects and is associated with the `hotel` table through the `hotel table's Id` by storing it as a foreign key in its "hotel Id" value.
- The table structures and values are as follows:

## hotel
- Id: Integer (Primary Key)
- Name: VarChar
- Type: VarChar [ DELUXE, LUXURY, SUITE ]
- Description: VarChar
- Availiable From: Date YYYY-MM-DD
- Available To: Date YYYY-MM-DD
- Status: Boolean

## reservation
-  Id: Integer (Primary Key)
-  Inventory Id: Integer (Foreign Key)
-  Check in Date: Date YYYY-MM-DD
-  Check out Date: Date YYYY-MM-DD
-  Number of Guests: Integer
-  Status: Boolean


# Testing
- Tests for this project incorporated both JUNit and Integration tests.
- I used Mockito is several unit tests and H2 in memory databases for the integration tests.
- Coverage testing for this project covered 93% of classes, 94% of methods and 85% of lines.
- More elaborate descriptions of the tests and their functionalities can be found in the test folder within this project.


# Tech Stack

- API Creation:
  - Java
  - MySQL
  - SpringBoot
  - Hibernate
  - JPA
  - Lombok
- Testing:
  - JUnit 5
  - Mockito
  - H2
- User Input Testing:
  - Swagger UI
  - Postman



