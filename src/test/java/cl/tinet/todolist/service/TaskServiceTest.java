package cl.tinet.todolist.service;

import cl.tinet.todolist.dao.TaskRepository;
import cl.tinet.todolist.exceptions.TaskException;
import cl.tinet.todolist.model.Task;
import cl.tinet.todolist.model.TaskRequestTO;
import cl.tinet.todolist.model.TaskResponseTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
     * Dummy value for active state Task attribute.
     */
    private final static int ACTIVE_STATE = 1;

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
     * Dummy Task.
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
        Exception exception = assertThrows(TaskException.class, () -> {
            taskService.getTasks();
        });

        assertEquals("error to get tasks", exception.getMessage());
    }

    /**
     * verify if the getTaskById works with a valid id.
     */
    @Test
    void getTaskByIdOk() {
        String id = "1";
        Long taskId = Long.valueOf(id);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        Task responseTask = taskService.getTaskById(id);

        assertEquals(task.getId(), responseTask.getId());
    }

    /**
     * verify if the getTaskById works with a valid id,
     * but no result.
     */
    @Test
    void getTaskByIdNOk() {
        String id = "200";
        Long taskId = Long.valueOf(id);
        Task emptyTask = Task.builder().build();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(emptyTask));
        Exception exception = assertThrows(TaskException.class, () -> {
            taskService.getTaskById(id);
        });
        assertEquals("getTaskById - error", exception.getMessage());
    }

    /**
     * verify if the getTaskById service, handle an invalid
     * parameter.
     *
     * The repository is not call, the service throws an exception
     * before to execute repository.
     *
     */
    @Test
    void getTaskByIdNotOKParsing() {
        String id = "q";
        Exception exception = assertThrows(TaskException.class, () -> {
            taskService.getTaskById(id);
        });
        assertEquals("getTaskById - error", exception.getMessage());
    }

    /**
     * Verify insertion of a task.
     */
    @Test
    void setTaskOk() {
        when(taskRepository.save(any())).thenReturn(task);
        TaskResponseTO response = taskService.setTask(taskRequestTO);
        assertEquals(TITLE, response.getTitle());
    }

    /**
     * Verify insertion of a task failed.
     */
    @Test
    void setTaskNoOk() {
        task.setId(null);
        when(taskRepository.save(any())).thenReturn(task);
        Exception exception = assertThrows(TaskException.class, () -> {
            taskService.setTask(taskRequestTO);
        });

        assertEquals("setTask - error", exception.getMessage());
    }

    /**
     * Verify update service of a task.
     */
    @Test
    void updateTaskOk() {
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));
        when(taskRepository.save(any())).thenReturn(task);
        Task response = taskService.updateTask("1", taskRequestTO);
        assertEquals(TITLE, response.getTitle());
    }

    /**
     * Verify logical delete service of a task.
     */
    @Test
    void deleteTask() {
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));
        when(taskRepository.save(any())).thenReturn(task);
        Task response = taskService.deleteTask("1");
        assertFalse(response.isActive());
    }

    /**
     * Verify toggle state of a task.
     */
    @Test
    void updateState() {
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));
        when(taskRepository.save(any())).thenReturn(task);
        Task taskBeforeUpdate = new Task();
        taskBeforeUpdate.setState(task.getState());
        Task taskAfterUpdate = taskService.updateState("1");
        assertNotEquals(taskAfterUpdate.getState(), taskBeforeUpdate.getState());
    }

    /**
     * Verify toggle state to Done of a task.
     */
    @Test
    void updateStateDone() {
        task.setState(ACTIVE_STATE);
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));
        when(taskRepository.save(any())).thenReturn(task);
        Task taskBeforeUpdate = new Task();
        taskBeforeUpdate.setState(task.getState());
        Task taskAfterUpdate = taskService.updateState("1");
        assertNotEquals(taskAfterUpdate.getState(), taskBeforeUpdate.getState());
    }

}