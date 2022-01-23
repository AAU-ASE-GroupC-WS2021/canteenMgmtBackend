package edu.aau.groupc.canteenbackend.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractControllerTest {
    @LocalServerPort
    private int port;

    @Value("${server.ssl.enabled}")
    private boolean httpsEnabled;

    @Autowired
    protected RestTemplate restTemplate;

    protected HttpHeaders headers = new HttpHeaders();

    private String getProtocol() {
        return httpsEnabled ? "https" : "http";
    }

    protected String createURLWithPort(String uri) {
        return getProtocol() + "://localhost:" + port + uri;
    }

    protected ResponseEntity<String> makeGetRequest(String uri) {
        return restTemplate.exchange(
                createURLWithPort(uri),
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                String.class);
    }
}
