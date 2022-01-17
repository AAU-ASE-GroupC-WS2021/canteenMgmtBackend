# Canteen Management Backend
[![CircleCI](https://circleci.com/gh/AAU-ASE-GroupC-WS2021/canteenMgmtBackend/tree/main.svg?style=shield)](https://circleci.com/gh/AAU-ASE-GroupC-WS2021/canteenMgmtBackend/tree/main)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=elsantner_canteenMgmtBackend&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=elsantner_canteenMgmtBackend)

Backend for our canteen management system.

Built with Spring Boot.

## Setup
Upon cloning the repository, everything should be set up to work with [IntelliJ Ultimate](https://www.jetbrains.com/idea/download/).

## Building from Source
Run `mvn -B -DskipTests clean package` in the project root.

Alternatively, exectute the above command as a Run Configuration in your IntelliJ IDE.

## Running and test execution
Once build, the project can either be run directly through IntelliJ or using the command prompt.

Using cmd, it can either be run using `mvn spring-boot:run` or `java -jar ./target/canteen-backend-X.Y.Z-SNAPSHOT-exec.jar` inside the project root.

Tests can be executed directly through IntelliJ or using `mvn test`.

## Database configuration
To allow for local testing, the database used by this application can be configured in the `src/main/resources/application.properties`. The three properties of `spring.datasource` allow to configure the database url, username and password. 

By default, the application is configured to use a public testing database on AWS. However, it is advisible to set up a local database for testing purposes. This can either be done by just installing [PostgreSQL](https://www.postgresql.org/download/) on your local machine or setting up a Docker container.

If Docker is installed, executing the command `docker run --name some-postgres -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword -d postgres` should set up a Docker container with a PostgreSQL instance accessible through port 5432. For more details, have a look at [this tutorial post](https://www.optimadata.nl/blogs/1/n8dyr5-how-to-run-postgres-on-docker-part-1).

## HTTPS and CSRF
By default, the application is configured to run in plain HTTP on port 8080. 
Please notethat once deployed, HTTPS is automatically used by Heroku. This requires the [Frontend application](https://github.com/AAU-ASE-GroupC-WS2021/canteenMgmtFrontend) (in file `lib/services/abstract_service.dart`) to be configured correctly before being deployed.

This application uses Spring Securities built-in prevention mechanism for [CSRF](https://en.wikipedia.org/wiki/Cross-site_request_forgery). This requires every state-changing request (POST, PUT, DELETE) to have a correct CSRF token set.
In detail, upon the first request, the application returns a `XSRF-TOKEN` as a cookie. This token must be set as the header `X-XSRF-TOKEN` (**notice the leading X-**) for all future requests in the session. For more details, please see [this tutorial](https://www.baeldung.com/csrf-stateless-rest-api)
