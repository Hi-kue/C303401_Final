# COMP309 Final Project - Bank Management Application

## Table of Contents

- [COMP309 Final Project - Bank Management Application](#comp309-final-project---bank-management-application)
  - [Table of Contents](#table-of-contents)
  - [Introduction](#introduction)
  - [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
  - [License](#license)

## Introduction

This project is a complete implementation of the COMP309 Final Project, which involves building a
bank management application using React for the frontend and Spring Boot for the backend. Features
for this project include:
- **Create, Read, Update, and Delete (CRUD)** operations for bank entities.
- **Validation** using Zod for input validation and jakarta.validation for backend validation.
- **Database** using MySQL for storing bank data.
- State management and effieicnt data fetching from the backend using Axios.
- **Styling** using Tailwind CSS for a modern and responsive design.

## Getting Started

### Prerequisites

Inorder to properly run this project, you must have the following software/tools installed
on your machine:

- [Node.js](https://nodejs.org/en/download/)
- [Git](https://git-scm.com/downloads)
- [Visual Studio Code](https://code.visualstudio.com/)
- [MySQL](https://www.mysql.com/downloads/)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [JDK](https://www.oracle.com/java/technologies/downloads/)
- [npm](https://www.npmjs.com/get-npm)
- [pnpm](https://pnpm.io/installation)

> [!NOTE]
> It is recommended to have the latest versions of each of the above software/tools installed, although
> older versions may also work but might cause some issues and bugs that will need to be addressed
> by you as the developer.

### Installation

1. Clone the repository:

```bash
git clone https://github.com/Hi-kue/C303401_Final.git .
```
2. Navigate to the server directory that contains the Spring Boot application;

```bash
cd ./server/
```
3. Setup the project dependencies and run the backend application using the following command:

```bash
mvn spring-boot:run

```
4. Head over to the `client` directory and install the project dependencies using the following command:

```bash
cd ../client/
npm install | pnpm install
```
5. Start the frontend development server using the following command:

```bash
npm run dev | pnpm run dev
```
5. Open your browser and navigate to `http://localhost:5173` to view the frontend application.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.