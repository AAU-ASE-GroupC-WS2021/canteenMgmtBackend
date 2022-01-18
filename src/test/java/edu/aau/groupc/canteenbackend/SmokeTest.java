package edu.aau.groupc.canteenbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("H2Database")
@SpringBootTest
class SmokeTest {

    @Test
    void contextLoads() {
        // tests if the application actually loads
    }

}
