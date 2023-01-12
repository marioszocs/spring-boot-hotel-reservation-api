# Hotel Reservation API

## Overview:

- This project is a Hotel Reservation RESTful API designed to create hotels and associated reservations.
- The user can create a hotel, search for available hotels, search for all hotels within the database, all reservations within the database, make reservations to a specific hotel, delete reservations as well as hotels.
- Necessary validation is incorporated within the API that prevents illogical operations from occurring such as making an overlapping reservation to a hotel or deleting a hotel that contains reservations.
- The Hotel-Reservation-API follows a standard "`User` <-> `Controller` <-> `Validator` (Only interacts with Controller) <-> `Service` <-> `Repository` <-> `Database`" API schema.

## Valid API Calls:

### hotel-controller

Use Case: **Create Hotel**
- Endpoint: `/hotel`
- Verb: `POST`
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



Use Case: **Update existing Hotel**
- Endpoint: `/hotel`
- Verb: `PATCH`
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



Use Case: **Get a user specified Hotel**
- Endpoint: `/hotel/{id}`
- Verb: `GET`
- Description: Get a user specified hotel object.
- Validation (Throws an `InvalidRequestException` when):
    -  ID does not exist
-  Request Body: N/A


Use Case: **Get all existing Hotels**
- Endpoint: `/hotels`
- Verb: `GET`
- Description: Get all existing hotel inventories
- Validation (Throws an `InvalidRequestException` when):
    - N/A
-  Request Body: N/A


Use Case: **Delete a user specified Hotel**
- Endpoint: `/hotel/{id}`
- Verb: `DELETE`
- Description:
- Validation (Throws an `InvalidRequestException` when):
    - ID does not exist
    - Reservations for this hotel object exist (A Reservations object that contains a foreign key associated with the user inputted ID exists)
- Request Body:
    -  Path Variable Integer "id"

### reservation-controller

Use Case: **Create a reservation**
- Endpoint: `/reservation`
- Verb: `POST`
- Description: Creates a hotel reservation with an existing hotel.
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

Use Case: **Get all reservations**
- Endpoint: `/reservations`
- Verb: `GET`
- Description: Get all exsiting hotel reservations.
- Validation (Throws an `InvalidRequestException` when):
    - N/A
-  Request Body: N/A


Use Case: **Get a user specified hotel reservation**
- Endpoint: `/reservation/{id}`
- Verb: `GET`
- Description: Get an exsiting user specified Hotel reservation
- Validation (Throws an `InvalidRequestException` when):
    - ID does not exist
- Request Body:
    -  Path variable Integer "id"


Use Case: **Delete a user specified hotel reservation**
- Endpoint: `/reservation/{id}`
- Verb: `DELETE`
- Description: Delete an existing user specified reservation.
- Validation (Throws an `InvalidRequestException` when):
    - ID does not exist
- Request Body:
    - Path variable Integer "id"


Use Case: **Get all available hotels**
- Endpoint: `/hotels/availabilitySearch?dateFrom={from}&dateTo={to}`
- Verb: `GET`
- Description: Gets all available hotel inventories between specified dates. This endpoint takes into account pre-exisiting reservations and hotel availability dates and only returns hotels that do not have overlapping reservations and do not have availibility dates that start or end between the user specified dates.
- Validation (Throws an `InvalidRequestException` when):
    - Dates are not in YYYY-MM-DD format
    - `dateFrom` comes after `dateTo`
    - One or no dates are inputted
- Request Body:
    - Path Variables dateTo and dateFrom, in YYYY-MM-DD format


## Database
- The database structure contains two tables, a `hotel` table that contains the hotel object.
- And a `reservation` table that contains reservations objects and is associated with the `hotel` table through the `hotel table's Id` by storing it as a foreign key in its "hotel Id" value.
- The table structures and values are as follows:

**Hotel:**
- Id: UUID (Primary Key)
- Name: VarChar
- Type: VarChar [ DELUXE, LUXURY, SUITE ]
- Description: VarChar
- Availiable From: Date YYYY-MM-DD
- Available To: Date YYYY-MM-DD
- Status: Boolean

**Reservation:**
-  Id: UUID (Primary Key)
-  Inventory Id: UUID (Foreign Key)
-  Check in Date: Date YYYY-MM-DD
-  Check out Date: Date YYYY-MM-DD
-  Number of Guests: Integer
-  Status: Boolean


## Testing
- Tests for this project incorporated both JUNit and Integration tests.
- I used Mockito is several unit tests and H2 in memory databases for the integration tests.
- Coverage testing for this project covered 93% of classes, 94% of methods and 85% of lines.
- More elaborate descriptions of the tests and their functionalities can be found in the test folder within this project.


## Tech Stack:

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