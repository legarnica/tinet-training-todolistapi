package cl.tinet.todolist.controller;

import cl.tinet.todolist.exceptions.TaskException;
import cl.tinet.todolist.model.Task;
import cl.tinet.todolist.model.TaskRequestTO;
import cl.tinet.todolist.model.TaskResponseTO;
import cl.tinet.todolist.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
     * Dummy Task to save.
     */
    private Task taskToSave;

    /**
     * Dummy TaskResponseTO.
     */
    private TaskResponseTO taskResponseTO;

    /**
     * Dummy TaskRequestTO.
     */
    private TaskRequestTO taskRequestTO;

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

        taskToSave = Task.builder()
                .title(TITLE)
                .description(DESCRIPTION)
                .state(STATE)
                .active(ACTIVE)
                .createAt(CREATE_AT)
                .updatedAt(UPDATED_AT).build();

        taskResponseTO = TaskResponseTO.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .state(task.getState())
                .createAt(task.getCreateAt())
                .updatedAt(task.getUpdatedAt()).build();

        taskRequestTO = new TaskRequestTO();

        taskRequestTO.setTitle(task.getTitle());
        taskRequestTO.setDescription(task.getDescription());
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

        mockMvc.perform(
                get("/api/v1/task").contentType("application/json")
        ).andExpect(status().isOk());
    }

    /**
     * Performs an invalid request to get all task
     *
     */
    @Test
    void getAllTaskNOK() throws Exception{
        when(taskService.getTasks()).thenThrow(TaskException.class);
        mockMvc.perform(
                get("/api/v1/task").contentType("application/json")
        ).andExpect(status().is(HttpStatus.EXPECTATION_FAILED.value()));
    }

    /**
     * Performs a valid request to get task by id 1
     *
     */
    @Test
    void getTaskByIdOk() throws Exception{
        String id = "1";
        when(taskService.getTaskById(id)).thenReturn(task);

        MvcResult mvcResult = mockMvc.perform(
                get("/api/v1/task/1").contentType("application/json")
        ).andExpect(status().isOk()).andReturn();
        String task = mvcResult.getResponse().getContentAsString();

        assertTrue(task.contains(DESCRIPTION));
    }

    /**
     * Performs an invalid request to get task by any id.
     */
    @Test
    void getTaskByIdNOk() throws Exception {
        when(taskService.getTaskById(anyString())).thenThrow(TaskException.class);
        mockMvc.perform(
                get("/api/v1/task/1").contentType("application/json")
        ).andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Performs a valid request to set task.
     *
     */
    @Test
    void setTaskOk() throws Exception{
        when(taskService.setTask(taskRequestTO)).thenReturn(taskResponseTO);
        String body = asJsonString(taskRequestTO);
        mockMvc.perform(
                post("/api/v1/task")
                        .content(body)
                        .contentType("application/json")
        ).andExpect(status().isCreated());

    }

    /**
     * Performs a invalid request to set task.
     *
     */
    @Test
    void setTaskNoOk() throws Exception {
        when(taskService.setTask(any())).thenThrow(TaskException.class);
        String body = asJsonString(taskRequestTO);
        mockMvc.perform(
                post("/api/v1/task")
                        .content(body)
                        .contentType("application/json")
        ).andExpect(status().isNotModified());
    }
    /**
     * Performs a valid request to update task.
     *
     */
    @Test
    void updateTaskOk() throws Exception{
        when(taskService.updateTask(any(), any())).thenReturn(task);
        String body = asJsonString(taskRequestTO);
        mockMvc.perform(
                put("/api/v1/task/1")
                        .content(body)
                        .contentType("application/json")
        ).andExpect(status().isCreated());

    }

    /**
     * Performs a invalid request to update task.
     *
     */
    @Test
    void updateTaskNoOk() throws Exception {
        when(taskService.updateTask(any(), any())).thenThrow(TaskException.class);
        String body = asJsonString(taskRequestTO);
        mockMvc.perform(
                put("/api/v1/task/1")
                        .content(body)
                        .contentType("application/json")
        ).andExpect(status().isNotModified());
    }
}