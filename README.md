# Canteen Management Backend
[![CircleCI](https://circleci.com/gh/AAU-ASE-GroupC-WS2021/canteenMgmtBackend/tree/main.svg?style=shield)](https://circleci.com/gh/AAU-ASE-GroupC-WS2021/canteenMgmtBackend/tree/main)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=elsantner_canteenMgmtBackend&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=elsantner_canteenMgmtBackend)

Backend for our canteen management system.

Built with Spring Boot.

## Setup
Upon cloning the repository, everything should be set up to work with [IntelliJ Ultimate](https://www.jetbrains.com/idea/download/).

## Building from Source
Run `mvn -B -DskipTests clean package` in the project root.

Alternatively, execute the above command as a Run Configuration in your IntelliJ IDE.

## Running and test execution
Once build, the project can either be run directly through IntelliJ or using the command prompt.

Using cmd, it can either be run using `mvn spring-boot:run` or `java -jar ./target/canteen_backend.jar` inside the project root.

Tests can be executed directly through IntelliJ or using `mvn test`.

### Performance Tests:
Performance tests are implemented using [JMeter](https://jmeter.apache.org/download_jmeter.cgi). This tool allows for the easy creation of performance tests by simulating a configurable number of users sending configurable HTTP requests. The tool also comes with a GUI for test plan creation.

Once configured, test plans can be saved as `jmx` files. Placing those files into the `src/test/jmeter` folder of the backend will automatically include them for performance test execution.

#### Test Execution
Performance tests are configured to be run on CircleCI as regular nightly builds.

To run them locally, first start the backend by running `mvn spring-boot:run` (requires local PostgreSQL setup) and then run the tests using `mvn verify -Pperformance`.

**Please Note**: The current version of JMeter used in this setup is partly incompatible with Java 17. In particular, HTML test reports are not generated in a Java 17 environment.
To bypass this issue, you can start the backend in a standard Java 17 environment, and then run the performance tests in a Java <17 environment. To change the Java version used by Maven you can set the `JAVA_HOME` environment variable to the install-directory of your JDK.

A run config is provided which uses Java 11 to execute all performance tests. If Java 11 is not installed, you might need to manually change the JDK in the run configuration to use it.
(Note: This run config launches _only_ the performance tests. The application still has to be started explicitly.)

## Database configuration
To allow for local testing, the database used by this application can be configured in the `src/main/resources/application.properties`. The property `app.database.connection_string` allow to configure the database connection string.

By default, the application is configured to use a public testing database on AWS. However, it is advisable to set up a local database for testing purposes. This can either be done by just installing [PostgreSQL](https://www.postgresql.org/download/) on your local machine or setting up a Docker container.

If Docker is installed, executing the command `docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword postgres:12.9` should set up a Docker container with a PostgreSQL instance accessible through port 5432. For more details, have a look at [this tutorial post](https://www.optimadata.nl/blogs/1/n8dyr5-how-to-run-postgres-on-docker-part-1).

## HTTPS
By default, the application is configured to run in plain HTTP on port 8080.
Please note that once deployed, HTTPS is automatically used by Heroku. This requires the [Frontend application](https://github.com/AAU-ASE-GroupC-WS2021/canteenMgmtFrontend) (in file `lib/services/abstract_service.dart`) to be configured correctly before being deployed.