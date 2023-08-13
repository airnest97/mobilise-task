# mobilise-task
A demo task for mobilise global

# Book Management System using Spring Boot

The Book Management System is a comprehensive web application developed using Spring Boot, aimed at providing users with a feature-rich platform to efficiently manage and organize their personal book collections. This README provides an overview of the system, installation instructions, usage guidelines, API documentation, and details for contributing to the project.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Postman Documentation](#postman-documentation)
- [Contributing](#contributing)


## Features

This Book Management System comes equipped with a range of powerful features designed to make book collection management seamless and efficient:

- **Book Addition**: Users can easily add new books to their collection by providing details such as title, author, ISBN, and publication year.
- **Data Validation**: Comprehensive validation ensures accurate book information entry, minimizing errors.
- **Effortless Updates**: Users can update existing book details, ensuring their collection remains up-to-date.
- **Quick Deletion**: The system allows for swift removal of books that are no longer part of the collection.
- **Detailed Listing**: A comprehensive list of all books in the collection provides a clear overview.
- **Advanced Search**: Users can search for specific books by title, author, or ISBN.
- **Integration Support**: The system offers well-defined API endpoints for seamless integration with external applications.

## Technologies Used

The Book Management System leverages modern technologies to deliver a robust and efficient experience:

- **Java**: The core programming language for developing the application logic.
- **Spring Boot**: A powerful framework for building robust and scalable applications.
- **Spring Data JPA**: Provides data access and manipulation capabilities using the Java Persistence API.
- **Spring Web**: Facilitates the creation of web APIs and interfaces.
- **H2**: A widely-used relational database management system.
- **Maven**: Manages project dependencies and provides a structured build process.
- **Git**: Version control for collaborative development.
- **Docker**: Containerization lets you build, test, and deploy applications quickly.
- **Redis**: An in-memory database that stores data in the server memory, a popular tool to cache data.

## Getting Started

### Prerequisites

Before getting started, ensure you have the following components installed:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)


### Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/airnest97/mobilise-task.git
   cd mobilise-task

2. **Configure the Database:**

   Modify the `src/main/resources/application.properties` file to include your database connection details:

   ```properties
   spring.datasource.url=jdbc:h2:mem:your_database_name
   spring.datasource.username=your_database_username
   spring.datasource.password=your_database_password

## Build and Run the Application:

Execute the following command to build and run the application:

    ```bash
    mvn spring-boot:run

## Access the Application

Open your browser and navigate to `http://localhost:6082` to access the Book Management System.

## Usage

### Book Management

- **Add New Books:** Use the web interface to add new books to your collection.
- **Update Book Details:** Modify existing book information to keep your collection up-to-date.
- **Delete Books:** Remove books that are no longer part of your collection.
- **Search:** Utilize the search feature to find specific books based on title, author, or ISBN.

### API Integration

If you need to integrate the Book Management System with other applications, you can use the provided API endpoints for seamless data exchange.

## API Endpoints

The Book Management System offers the following API endpoints:

### Book Endpoints

- List all books: `GET /api/v1/book/all?pageNo=pageNo&noOfItem=noOfItem`
- Get book by ID: `GET /api/v1/book/{id}`
- Add new book: `POST /api/v1/book`
- Update book: `PATCH /api/v1/book/{id}`
- Delete book: `DELETE /api/v1/book/{id}`
- Search books: `GET /api/v1/books/searchBook?title=title&page=page&size=size`

### Author Endpoints

- List all authors: `GET /api/v1/author/all?pageNo=pageNo&noOfItem=noOfItem`
- Get author by ID: `GET /api/v1/author/{id}`
- Add new author: `POST /api/v1/author`
- Update author: `PATCH /api/v1/author/{id}`
- Delete author: `DELETE /api/v1/author/{id}`
- Search author: `GET /api/v1/books/searchAuthor?title=title&page=page&size=size`


## postman documentation

For more detailed information about these API endpoints, refer to the API documentation.
- [Postman Documentation Collection](https://documenter.getpostman.com/view/21596187/2s9Xy5Lpys)


## Contributing

Ccontributions are welcomed from the community. If you encounter any issues or have suggestions for improvements, feel free to:

- Open an issue to discuss the problem or enhancement.
- Fork the repository, make changes, and submit a pull request for review.

