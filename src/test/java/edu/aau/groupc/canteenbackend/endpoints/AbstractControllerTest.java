package edu.aau.groupc.canteenbackend.endpoints;

import edu.aau.groupc.canteenbackend.entities.Dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractControllerTest {
    @LocalServerPort
    private int port;

    @Value("${server.ssl.enabled}")
    private boolean httpsEnabled;

    @Value("${app.auth.header}")
    protected String tokenHeader;

    @Autowired
    protected RestTemplate restTemplate;

    private final HttpHeaders defaultHeaders;

    public AbstractControllerTest() {
        defaultHeaders = new HttpHeaders();
        defaultHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    private String getProtocol() {
        return httpsEnabled ? "https" : "http";
    }

    protected String createURLWithPort(String uri) {
        return getProtocol() + "://localhost:" + port + uri;
    }

    protected ResponseEntity<String> makeGetRequest(String uri) {
        return makeGetRequest(uri, new HttpHeaders());
    }

    protected ResponseEntity<String> makeGetRequest(String uri, HttpHeaders headers) {
        setDefaultHeaders(headers);
        return restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                String.class);
    }

    protected ResponseEntity<String> makePutRequest(String uri, Dish newDish) {
        return restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.PUT,
                new HttpEntity<>(newDish, defaultHeaders),
                String.class);
    }

    protected ResponseEntity<String> makeDeleteRequest(String uri, Dish newDish) {
        return restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.DELETE,
                new HttpEntity<>(newDish, defaultHeaders),
                String.class);
    }

    protected ResponseEntity<String> makePostRequest(String uri, String body) {
        return makePostRequest(uri, body, new HttpHeaders());
    }

    protected ResponseEntity<String> makePostRequest(String uri, String body, HttpHeaders headers) {
        setDefaultHeaders(headers);
        return restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                String.class);
    }

    private void setDefaultHeaders(HttpHeaders headers) {
        for (String k: defaultHeaders.keySet()) {
            List<String> values = defaultHeaders.get(k);
            if (values != null) {
                for (String v: values) {
                    headers.addIfAbsent(k, v);
                }
            }
        }
    }
}
