package edu.aau.groupc.canteenbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration to parse predefined connection string to PostgreSQL-accepted connection string
 * This is necessary since Heroku only automatically provides a connection string in this format.
 */
@Configuration
public class DataSourceConfig {

    @Value("${app.database.connection_string}")
    private String connString;

    @Bean
    public DataSource datasource() {
        Map<String, String> connInfo = parseConnectionString();

        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://" + connInfo.get("urlAndDatabase"))
                .username(connInfo.get("username"))
                .password(connInfo.get("password"))
                .build();
    }

    private Map<String, String> parseConnectionString() {
        Map<String, String> map = new HashMap<>();
        String withoutPrefix = connString.replace("postgres://", "");
        String[] usernameRest = withoutPrefix.split(":", 2);
        map.put("username", usernameRest[0]);
        String[] passwordRest = usernameRest[1].split("@", 2);
        map.put("password", passwordRest[0]);
        map.put("urlAndDatabase", passwordRest[1]);
        return map;
    }
}