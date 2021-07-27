package cl.tinet.todolist.controller;

import cl.tinet.todolist.exceptions.TaskException;
import cl.tinet.todolist.model.Task;
import cl.tinet.todolist.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class, to expose the tasks services.
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class TaskController {

    /**
     * References to TaskService.
     */
    private final TaskService taskService;

    /**
     * Constructor of the class.
     * @param taskService see {@link #taskService}
     */
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Get all task
     *
     * @return a list of all task
     */
    @GetMapping("/task")
    public ResponseEntity<List<Task>> getAllTask() {
        log.info("[getAllTask - init]");
        List<Task> tasks = new ArrayList<>();
        ResponseEntity<List<Task>> response = null;
        try{
            tasks = taskService.getTasks();
            response = new ResponseEntity<>(tasks, HttpStatus.OK);
            return response;
        }catch (TaskException e) {
            response = new ResponseEntity<>(tasks, HttpStatus.EXPECTATION_FAILED);
            return response;
        }
    }

    /**
     * Get a task by id.
     *
     * @param id identification of a task.
     * @return a task.
     */
    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id){
        log.info("[getTaskById - init] [id][{}]", id);
        Task task = null;
        ResponseEntity<Task> response = null;
        try{
            task = taskService.getTaskById(id);
            response = new ResponseEntity<>(task, HttpStatus.OK);
            return response;
        }catch (TaskException e) {
            response = new ResponseEntity<>(task, HttpStatus.EXPECTATION_FAILED);
            return response;
        }
    }
}
