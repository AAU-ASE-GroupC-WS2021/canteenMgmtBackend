ARG BUILD_IMAGE=maven:3.8-openjdk-17
ARG RUNTIME_IMAGE=openjdk:17-jdk-slim

# pull all maven dependencies
FROM ${BUILD_IMAGE} as dependencies

WORKDIR /build
COPY pom.xml /build/

RUN mvn -B dependency:go-offline

# build spring boot app
FROM dependencies as build

WORKDIR /build
COPY src /build/src

RUN mvn -B clean package

FROM ${RUNTIME_IMAGE}

WORKDIR /app
COPY --from=build /build/target/canteen_backend.jar /app/canteen_backend-exec.jar

CMD ["java", "-jar", "/app/canteen_backend-exec.jar"]