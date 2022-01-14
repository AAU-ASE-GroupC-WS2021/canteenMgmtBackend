# Canteen Management Backend
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

## HTTPS and Certificate Issues
By default, the application is configured to run in secure mode using HTTPS on port 8443. Since we are dealing with local development environments, we are currently unable to create a CA-approved SSL certificate. This means that you will get a security warning when trying to access an application endpoint through your web browser. Please trust the service by adding an exception in your browser for now.

Alternatively, you have the option to disable HTTPS by setting `server.ssl.enabled` to `false` in the `src/main/resources/application.properties`. Please note that this will require also changing the protocol in the [Frontend application](https://github.com/AAU-ASE-GroupC-WS2021/canteenMgmtFrontend) (in file `lib/services/abstract_service.dart`).
