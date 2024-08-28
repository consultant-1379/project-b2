package com.ericsson.securityassessment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void testEnterID(){
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetTop10(){
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/",
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<String>() {});

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
