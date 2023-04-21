# Twitter-like Microservice using Spring and React

This is a Twitter-like microservice application that utilizes the Spring framework for the backend and React for the frontend. The application is built using microservice architecture that separates each functionality into individual microservices.

## Technologies used

## Backend
* **Spring Framework** - used for building the backend microservices.
* **Eureka** - used for service discovery and load balancing
* **Spring Cloud Gateway** used for provide APIs  routing
* **Rabbit MQ** - used as a message broker between the microservices.
* **Docker** - used to containerize each microservice and to simplify deployment.
* **Hibernate** - for SQL database mapping
* **Spring Websockts** -  for messaging and notification with stomp and sockjs
* **Spring Security** - for authentication and authorization management with JWT

### Frontend
* **React Framework & Typescript** - used for building the user interface 
* **Tailwind** - for styles
* **Redux & Redux Tollkit** - for state management

## Microservices

The application consists of the following microservices:

1. **Eureka Server** - handles service registration and discovery
2. **Auth Service** - handles user authentication, authorization and routing
2. **Tweet Service** - manages the creation, deletion, and retrieval of tweets.
3. **Profile Service** - manages the creation, deletion, and retrieval of user profiles.
4. **Search Service** - handles searching for tweets and users.
5. **Media Service** - handles media upload and reterive
6. **JWT Service** - handles JWT generation and validation
7. **Messages Service** - handles messages between users
8. **Notification Service** - handles user notifictions
9. **Poll Service** - handles poll tweets 

Each microservice is developed independently and deployed in its own container.

## How to run the application

Before running the application, you need to have the following installed:
* Docker
* Node.js
* Java Development Kit (JDK) 11+
* maven 

Then follow these steps to run the application:

1. Clone the repository to your local machine.
git clone git@github.com:mohamedhalim1998/Twitter-Clone.git
3. Run the docker-compose file to start all microservices.
docker compose up
4. run frontend 
npm start
4. Navigate to http://localhost:3000 in a web browser to view the application.
