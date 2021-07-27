package cl.tinet.todolist.service;

import cl.tinet.todolist.dao.TaskRepository;
import cl.tinet.todolist.exceptions.TaskException;
import cl.tinet.todolist.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Test class for Task service.
 */
@ExtendWith(SpringExtension.class)
class TaskServiceTest {
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
     * Dummy Task to be used in test.
     */
    private Task task;

    /**
     * Dummy List to use in test.
     */
    private List<Task> tasks = new ArrayList<>();

    /**
     * Dummy service.
     */
    private TaskService taskService;

    /**
     * Mock service to replace the real repository behavior.
     */
    @MockBean
    private TaskRepository taskRepository;

    /**
     * Setup the necessary state to perform this test.
     */
    @BeforeEach
    void setup() {
        taskService = new TaskService(taskRepository);
        task = Task.builder()
                .id(ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .state(STATE)
                .active(ACTIVE)
                .createAt(CREATE_AT)
                .updatedAt(UPDATED_AT).build();

        tasks.add(task);
    }

    /**
     * Test getTask service when is OK
     */
    @Test
    void getTasksOk() {
        when(taskRepository.findAll()).thenReturn(tasks);
        List<Task> tasksTest = taskService.getTasks();
        assertEquals(tasks.size(), tasksTest.size());
    }

    /**
     * Test getTask service when is not OK
     */
    @Test
    void getTasksNOk() {
        when(taskRepository.findAll()).thenThrow(TaskException.class);
        boolean isNotOk = false;
        try{
            taskService.getTasks();
        }catch(TaskException e){
            isNotOk = true;
        }
        assertTrue(isNotOk);
    }
}