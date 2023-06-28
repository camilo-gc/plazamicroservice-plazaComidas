<br />
<div align="center">
<h2 align="center">Plaza microservice</h2>
  <p align="center">
    micro-service in charge of plaza management in the system of a restaurant chain.
  </p>
</div>


### Built With

* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
* ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
* ![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)


<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these steps.

### Prerequisites

* JDK 17 [https://jdk.java.net/java-se-ri/17](https://jdk.java.net/java-se-ri/17)
* Gradle [https://gradle.org/install/](https://gradle.org/install/)
* MySQL [https://dev.mysql.com/downloads/installer/](https://dev.mysql.com/downloads/installer/)

### Recommended Tools
* IntelliJ Community [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)
* Postman [https://www.postman.com/downloads/](https://www.postman.com/downloads/)

### Installation

1. Clone the repository
2. Change directory
   ```sh
   cd plazamicroservice-powerUP-v3
   ```
3. Create a new database in MySQL called plaza-powerup
4. Change the port
    ```yml
   # src/main/resources/application-dev.yml
   server:
      port: 8091
   ```
5. Update the database connection settings
   ```yml
   # src/main/resources/application-dev.yml
   spring:
      datasource:
          url: jdbc:mysql://localhost/plaza-powerup
          username: root
          password: <your-password>
   ```
   

<!-- RUN -->
## RUN

1. Right-click the class PlazaMicroserviceApplication and choose Run
2. Open [http://localhost:8091/swagger-ui/index.html](http://localhost:8091/swagger-ui/index.html) in your web browser


<!-- USAGE -->
## Usage

After entering the authentication token, we will have access to the following services

1. **Assign Orders**

   **Endpoint:** /order/assign

   **Description:** This endpoint is used to assign orders to employees.

   **HTTP Method:** PUT

   **Request Body:**
   ```json
   [
      {
      "id": "3"
      }
   ]
   ```
   
   **Response:**

   200 OK if the orders are successfully assigned.
   ```json
   [
      {
         "id": 0,
         "date": "string",
         "status": "string",
         "id_client": "string",
         "id_chef": "string",
         "id_restaurant": "string"
      }
   ]
   ```
   
   404 Bad Request, Order not found
   ```json
   {
      "error": "string"
   }
   ```
   
   409 Bad Request if the request body is missing required fields or contains invalid data.
   ```json
   {
      "error": "string"
   }
   ```

2. **Get Orders by Status**
   
   **Endpoint:** /order

   **Description:** This endpoint is used to retrieve orders based on their status.

   **HTTP Method:** GET  

   **Query Parameters:**  
   `status` The status of the orders to retrieve (e.g., "pending", "Preparation").  
   `page`(Default value: 1): The page number to display.   
   `size`(Default value: 10): the number of pages on which the information is obtained.  
  
   **Response:**

   200 OK along with the orders information in the response body if the orders are found.
   ```json
   [
      {
         "id": 0,
         "date": "string",
         "status": "string",
         "id_client": "string",
         "id_chef": "string",
         "id_restaurant": "string"
      }
   ]
   ```
   
   404 Not Found if no orders matching the specified status are found.
   ```json
   {
      "error": "string"
   }
   ```

3. **Add a New Order**

   **Endpoint:** /order
   
   **Description:** This endpoint is used to add a new order to the system.
   
   **HTTP Method:** POST
   
   **Request Body:**
   ```json
   {
      "dishes": [
        {
          "quantity": "1",
          "id_dish": "2"
        }
      ],
      "id_restaurant": "4"
   }
   ```
   
   **Response:**

   201 OK if the order is successfully added.
   ```json
   {
      "message": "string"
   }
   ```

   404 Bad Request if the dish is not found.
   ```json
   {
      "error": "string"
   }
   ```
   409 Bad Request if the request body is missing required fields or contains invalid data.
   ```json
   {
      "error": "string"
   }
   ```

4. **Update a Dish**
   
   **Endpoint:** /dish/{id}

   **Description:** This endpoint is used to update information about a dish.

   **HTTP Method:** PUT

   **Path Parameter:**  
   `id`: The ID of the dish to update.  
   
   **Request Body:**
   ```json
   {
      "description": "string",
      "price": "12000"
   }
   ```
   
   **Response:**
   
   200 OK if the dish is successfully updated.
   ```json
   {
      "message": "string"
   }
   ```
   
   403 Bad Request if the owner not allowed for dish update.
   ```json
   {
      "error": "string"
   }
   ```
   
   404 Not Found if the dish with the specified ID does not exist.
   ```json
   {
      "error": "string"
   }
   ```

5. **Enable/Disable a Dish**

   **Endpoint:** /dish/{id}

   **Description:** This endpoint is used to enable or disable a dish.
   
   **HTTP Method:** PATCH
   
   **Path Parameter:**  
   `id` The ID of the dish to enable or disable.

   **Request Body:**
   ```json
   {
      "active": "string"
   }
   ```
   
   **Response:**
   
   200 OK if the dish is successfully enabled or disabled.
   ```json
   {
      "message": "string"
   }
   ```

   403 Bad Request if the owner not allowed for dish update.
   ```json
   {
      "error": "string"
   }
   ```
      
   404 Not Found if the dish with the specified ID does not exist.
   ```json
   {
      "error": "string"
   }
   ```

6. **Add a New Dish**

   **Endpoint:** /dish/new
   
   **Description:** This endpoint is used to add a new dish to the system.
   
   **HTTP Method:** POST
   
   **Request Body:**
   ```json
   {
      "name": "string",
      "id_category": "1890963128568151463521677",
      "description": "string",
      "price": "7363773564650688574167201800755390",
      "id_restaurant": "96531225400813597688522",
      "url_image": "string"
   }
   ```

   **Response:**
   
   201 OK if the dish is successfully added.
   ```json
   {
      "message": "string"
   }
   ```
   
   403 Bad Request if the Owner not allowed for dish creation.
   ```json
   {
      "error": "string"
   }
   ```

   409 Bad Request if the dish already exists.
   ```json
   {
      "error": "string"
   }
   ```

7. **Get Dishes by Restaurant and Category**

   **Endpoint:** /dish/restaurant/{idRestaurant}/category/{idCategory}
   
   **Description:** This endpoint is used to retrieve dishes based on a specific restaurant and category.
   
   **HTTP Method:** GET
      
   **Path Parameters:**   
   `idRestaurant`: The ID of the restaurant.   
   `idCategory`: The ID of the category.

   **Query Parameters:**      
   `page`(Default value: 1): The page number to display.      
   `size`(Default value: 10): the number of pages on which the information is obtained.

   **Response:**
   
   200 OK along with the dishes information in the response body if the dishes are found.
   ```json
   [
      {
         "id": 0,
         "date": "string",
         "status": "string",
         "id_client": "string",
         "id_chef": "string",
         "id_restaurant": "string"
      }
   ]
   ```

   404 Not Found if no dishes matching the specified restaurant and category are found.
   ```json
   {
      "error": "string"
   }
   ```

8. **Add a New Restaurant**

   **Endpoint:** /restaurant/new

   **Description:** This endpoint is used to add a new restaurant to the system.

   **HTTP Method:** POST

   **Request Body:**
   ```json
   {
      "name": "string",
      "address": "string",
      "id_owner": "56",
      "phone": "+763706542055",
      "url_logo": "string",
      "nit": "string"
   }
   ```

   **Response:**
   
   201 OK if the restaurant is successfully added.
   ```json
   {
      "message": "string"
   }
   ```
   
   403 Bad Request if the Role not allowed for restaurant creation.
   ```json
   {
      "error": "string"
   }
   ```

   409 Bad Request if the Restaurant already exists.
   ```json
   {
      "error": "string"
   }
   ```

9. **Add a New Employee to a Restaurant**

   **Endpoint:** /restaurant/new/employee

   **Description:** This endpoint is used to add a new employee to a restaurant.
   
   **HTTP Method:** POST
   
   **Request Body:**
   ```json
   {
      "id_restaurant": "82218",
      "name": "string",
      "surname": "string",
      "dni": "string",
      "phone": "+534147822071",
      "email": "string",
      "password": "string"
   }
   ```
   
   **Response:**
   
   201 OK if the employee is successfully added to the restaurant.
   ```json
   {
      "message": "string"
   }
   ```
   
   403 Bad Request if the 	
   Role not allowed for restaurant creation.
   ```json
   {
      "error": "string"
   }
   ```

   409 Bad Request if the Restaurant already exists.
   ```json
   {
      "error": "string"
   }
   ```

10. **Get All Restaurants**

   **Endpoint:** /restaurant   
   **Description:** This endpoint is used to retrieve information about all the restaurants.   
   **HTTP Method:** GET

   **Query Parameters:**      
   `page`(Default value: 1): The page number to display.      
   `size`(Default value: 10): the number of pages on which the information is obtained.
   
   **Response:**
   
   200 OK along with the restaurants information in the response body if restaurants are found.
   ```json
   [
      {
         "name": "string",
         "url_logo": "string"
      }
   ]
   ```
   
   404 Not Found if no restaurants are found.
   ```json
   {
      "error": "string"
   }
   ```

Please note that some endpoints require authentication. You should include the appropriate authentication headers with your requests.


<!-- DEPENDENCIES -->
## Dependencies

- JPA
- OpenFeign
- Mapstruct
- Lombok
- Jakarta
- JWT

<!-- ROADMAP -->
## Tests

- Right-click the test folder and choose Run tests with coverage

prueba github-actions