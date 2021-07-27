package cl.tinet.todolist.controller;

import cl.tinet.todolist.exceptions.TaskException;
import cl.tinet.todolist.model.Task;
import cl.tinet.todolist.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskController.class)
class TaskControllerTest {
    /**
     * Dummy value for id Task attribute.
     */
    private final static Long ID = 1L;

    /**
     * Dummy value for title Task attribute.
     */
    private final static String TITLE = "TITLE";

    /**
     * Dummy value for description Task attribute.
     */
    private final static String DESCRIPTION = "DESCRIPTION";

    /**
     * Dummy value for state Task attribute.
     */
    private final static int STATE = 0;

    /**
     * Dummy value for active Task attribute.
     */
    private final static boolean ACTIVE = true;

    /**
     * Dummy value for createAt Task attribute.
     */
    private final static String CREATE_AT = "01-01-2021 00:00:00";

    /**
     * Dummy value for updatedAt Task attribute.
     */
    private final static String UPDATED_AT = "01-01-2021 00:00:00";

    /**
     * Mock to perform the request.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mock service to replace the real service behavior.
     */
    @MockBean
    private TaskService taskService;

    /**
     * Dummy Task to be used in test.
     */
    private Task task;

    /**
     * Setup the necessary state to perform this test.
     */
    @BeforeEach
    void setup() {
        task = Task.builder()
                .id(ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .state(STATE)
                .active(ACTIVE)
                .createAt(CREATE_AT)
                .updatedAt(UPDATED_AT).build();
    }

    /**
     * Performs a valid request to get all task
     *
     */
    @Test
    void getAllTaskOK() throws Exception{
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        when(taskService.getTasks()).thenReturn(tasks);

        MvcResult mvcResult = mockMvc.perform(
                get("/api/v1/task").contentType("application/json")
        ).andExpect(status().isOk()).andReturn();
    }

    /**
     * Performs an invalid request to get all task
     *
     */
    @Test
    void getAllTaskNOK() throws Exception{
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        when(taskService.getTasks()).thenThrow(TaskException.class);

        MvcResult mvcResult = mockMvc.perform(
                get("/api/v1/task").contentType("application/json")
        ).andExpect(status().is(HttpStatus.EXPECTATION_FAILED.value())).andReturn();
    }
}