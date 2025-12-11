package org.example.homework12.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void addBookTest() throws Exception {
        String json = """
            {
              "title": "Gilgamesh Epic",
              "author": "Unknown",
              "year": -2100,
              "description": "First ever piece of literary art"
            }
            """;

        // 1. POST запрос
        var result = mockMvc.perform(
                        post("/api/books/add")
                                .contentType("application/json")
                                .content(json)
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();

        // 2. Получение Location
        String location = result.getResponse().getHeader("Location");

        // 3. Работает ли GET по этому URI
        Assertions.assertNotNull(location);
        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Gilgamesh Epic"))
                .andExpect(jsonPath("$.author").value("Unknown"))
                .andExpect(jsonPath("$.year").value(-2100));
    }

    @Test
    @Order(2)
    public void getAllBooksTest() throws Exception {

    }

    @Test
    @Order(3)
    public void getBookByIdTest() throws Exception {

    }

    @Test
    @Order(4)
    public void changeBookTest() throws Exception {

    }
}
