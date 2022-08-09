package com.example.obrestdatajpa.controllers;

import com.example.obrestdatajpa.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:"+port);
        testRestTemplate = new TestRestTemplate((restTemplateBuilder));
    }

    @Test
    void create() {

        //Configuration
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String JSON = """
                {
                    "title": "El Hobbit - La desolación del Smaug",
                    "author": "JRR Tolkien",
                    "pages": 340,
                    "price": 29.99,
                    "releaseDate": "1990-04-11",
                    "online": true
                }
                """;

        //Execute
        HttpEntity<String> request = new HttpEntity<>(JSON, headers);
        ResponseEntity<Book> response = testRestTemplate.exchange("/api/books/create", HttpMethod.POST, request, Book.class);
        Book result = response.getBody();

        //Asserts
        assertNotNull(result);
    }

    @Test
    void findAll() {

        //Execute
        ResponseEntity<Book[]> response = testRestTemplate.getForEntity("/api/books/find", Book[].class);

        //Asserts (OK)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        //Asserts (Size > 0)
        List<Book> books = Arrays.asList(response.getBody());
        assertTrue(books.size() > 0);
    }
}