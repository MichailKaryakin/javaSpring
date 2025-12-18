package org.example.homework12.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void addBookTest() throws Exception {
        // Json с добавляемой книгой
        String json = """
                {
                  "title": "Gilgamesh Epic",
                  "author": "Unknown",
                  "year": -2100,
                  "description": "First ever piece of literary art"
                }
                """;

        // POST запрос
        var result = mockMvc.perform(
                        post("/api/books/add")
                                .contentType("application/json")
                                .content(json)
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();

        // Получение Location
        String location = result.getResponse().getHeader("Location");

        // Работает ли GET по этому URI
        Assertions.assertNotNull(location);
        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Gilgamesh Epic"))
                .andExpect(jsonPath("$.author").value("Unknown"))
                .andExpect(jsonPath("$.year").value(-2100));
    }

    @Test
    @Order(2)
    public void addInvalidBookTest() throws Exception {
        // Неправильный Json
        String json = """
                {
                  "title": "Gilgamesh Epic",
                  "author": "Unknown"
                }
                """;

        // Проверка на bad request
        mockMvc.perform(
                        post("/api/books/add")
                                .contentType("application/json")
                                .content(json)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void getAllBooksTest() throws Exception {
        // Проверка получения всех книг
        mockMvc.perform(get("/api/books/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(Matchers.greaterThan(0)))
                .andExpect(jsonPath("$[*].id").exists())
                .andExpect(jsonPath("$[*].title").exists())
                .andExpect(jsonPath("$[*].author").exists())
                .andExpect(jsonPath("$[*].year").exists())
                .andExpect(jsonPath("$[*].description").exists());
    }

    @Test
    @Order(4)
    public void getBookByIdTest() throws Exception {
        // Проверка получения книги по id = 1, должно быть ok
        mockMvc.perform(get("/api/books/book-by-id?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.author").exists())
                .andExpect(jsonPath("$.year").exists())
                .andExpect(jsonPath("$.description").exists());

        // Проверка получения книги по id = 9999999, должно быть not found
        mockMvc.perform(get("/api/books/book-by-id?id=9999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    public void changeBookTest() throws Exception {
        // Json с обновлёнными полями
        String json = """
                {
                  "title": "Les Miserables",
                  "author": "Victor Hugo",
                  "year": 1862,
                  "description": "One of the greatest 19th century novels"
                }
                """;

        // Проверка обновления книги с id = 1
        mockMvc.perform(put("/api/books/change?id=1")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());

        // Проверка обновления книги с id = 99999999, должно быть not found
        mockMvc.perform(put("/api/books/change?id=99999999")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isNotFound());

        // Проверка полей обновлённой по id = 1 книги
        mockMvc.perform(get("/api/books/book-by-id?id=1"))
                .andExpect(jsonPath("$.title").value("Les Miserables"))
                .andExpect(jsonPath("$.author").value("Victor Hugo"))
                .andExpect(jsonPath("$.year").value(1862))
                .andExpect(jsonPath("$.description").value("One of the greatest 19th century novels"));
    }
}
