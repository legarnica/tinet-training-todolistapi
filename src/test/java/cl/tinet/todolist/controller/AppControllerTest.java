package cl.tinet.todolist.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AppController.class)
class AppControllerTest {

    /**
     * Mock to perform the request.
     */
    @Autowired
    private MockMvc mockMvc;

    @Test
    void isAlive() throws Exception {
        mockMvc.perform(
                get("/").contentType("application/json")
        ).andExpect(status().isOk());
    }
}