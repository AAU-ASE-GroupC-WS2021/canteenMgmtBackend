package edu.aau.groupc.canteenbackend.endpoints;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DishControllerTest {
    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testGetDishes() throws JSONException {
        ResponseEntity<String> response = makeGetRequest("/dish");

        String expected = "[{\"name\":\"Salad\",\"price\":1.5,\"type\":\"STARTER\"}," +
                           "{\"name\":\"Cheese Burger\",\"price\":4.0,\"type\":\"MAIN\"}]";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private ResponseEntity<String> makeGetRequest(String uri) {
        return restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                String.class);
    }
}
