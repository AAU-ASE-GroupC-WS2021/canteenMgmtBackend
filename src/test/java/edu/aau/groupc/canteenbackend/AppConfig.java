package edu.aau.groupc.canteenbackend;

import edu.aau.groupc.canteenbackend.services.DishService;
import edu.aau.groupc.canteenbackend.services.IDishService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public IDishService getSampleService() {
        return new DishService();
    }
}
