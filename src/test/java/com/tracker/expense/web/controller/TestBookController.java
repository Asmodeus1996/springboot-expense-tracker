package com.tracker.expense.web.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestBookController {

    @Autowired
    private MockMvc mockMvc;

    MockHttpServletResponse response;

    @Test
    @DisplayName("All Books")
    @Order(2)
    public void testgetAllBook() {
        //step1 : create request
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get("/book/allbooks");

        //step2 execute request and collect response
        try {
            response = mockMvc.perform(req).andReturn().getResponse();
        } catch (Exception e) {

            e.printStackTrace();
        }

        //step3 assert/check response
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        try {
            assertNotNull(response.getContentAsString());
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        response = null;

    }

    @Test
    @DisplayName("Add Book")
    @Order(1)
    public void testAddBook() {
        //step1 : create request
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.post("/book/addBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"title\" : \"Fiction\" ,\n" +
                        "    \"author\" : \"Mr Fiction\",\n" +
                        "    \"cost\" : 10000.0\n" +
                        "}");

        //step2 execute request and collect response
        try {
            response = mockMvc.perform(req).andReturn().getResponse();
        } catch (Exception e) {

            e.printStackTrace();
        }

        //step3 assert/check response
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        try {
            assertNotNull(response.getContentAsString());
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        response = null;

    }

    @Test
    @DisplayName("Buy Book")
    @Order(3)
    public void testBuyBook() {
        //step1 : create request
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.post("/book/buyBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                                "    \"userName\" : \"Rado\",\n" +
                                "    \"bookId\" :191,\n" +
                                "    \"cost\" : 3000.0\n" +
                                "    \n" +
                                "\n" +
                                "    \n" +
                                "\n" +
                                "}");

        //step2 execute request and collect response
        try {
            response = mockMvc.perform(req).andReturn().getResponse();
        } catch (Exception e) {

            e.printStackTrace();
        }

        //step3 assert/check response
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        try {
            assertNotNull(response.getContentAsString());
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        response = null;

    }

}