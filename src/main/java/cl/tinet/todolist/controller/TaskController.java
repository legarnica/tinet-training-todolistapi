package cl.tinet.todolist.controller;

import cl.tinet.todolist.exceptions.TaskException;
import cl.tinet.todolist.model.CustomResponse;
import cl.tinet.todolist.model.Task;
import cl.tinet.todolist.model.TaskRequestTO;
import cl.tinet.todolist.model.TaskResponseTO;
import cl.tinet.todolist.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
     * CustomResponse tiped with Task.
     */
    private CustomResponse<Task> responseTaskBody = new CustomResponse<>();

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
    public ResponseEntity<CustomResponse<List<Task>>> getAllTask() {
        log.info("[getAllTask - init]");
        ResponseEntity<CustomResponse<List<Task>>> responseEntity = null;
        CustomResponse<List<Task>>  responseBody = new CustomResponse<>();
        try{
            responseBody.setBody(taskService.getTasks());
            responseBody.setMessage(HttpStatus.OK.getReasonPhrase());
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }catch (TaskException e) {
            responseBody.setMessage(HttpStatus.EXPECTATION_FAILED.getReasonPhrase());
            responseEntity = new ResponseEntity<>(responseBody, HttpStatus.EXPECTATION_FAILED);
            return responseEntity;
        }
    }

    /**
     * Get a task by id.
     *
     * @param id identification of a task.
     * @return a task.
     */
    @GetMapping("/task/{id}")
    public ResponseEntity<CustomResponse<Task>> getTaskById(@PathVariable String id){
        log.info("[getTaskById - init] [id][{}]", id);
        CustomResponse<Task> responseBody = new CustomResponse<>();
        try{
            responseBody.setBody(taskService.getTaskById(id));
            responseBody.setMessage(HttpStatus.OK.getReasonPhrase());
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }catch (TaskException e) {
            responseBody.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Task insert service.
     *
     * @param requestBody Task data to insert.
     * @return ResponseEntity
     */
    @PostMapping("/task")
    public ResponseEntity<CustomResponse<TaskResponseTO>> setTask(@Valid @RequestBody TaskRequestTO requestBody) {
        log.info("[setTask - init] [requestBody][{}]", requestBody);
        CustomResponse<TaskResponseTO> responseBody = new CustomResponse<>();
        try{
            TaskResponseTO taskResponse = taskService.setTask(requestBody);
            responseBody.setBody(taskResponse);
            responseBody.setMessage(HttpStatus.CREATED.getReasonPhrase());
            return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
        }catch (TaskException e) {
            responseBody.setMessage(HttpStatus.NOT_MODIFIED.getReasonPhrase());
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_MODIFIED);
        }
    }

    /**
     /**
     * Task to update service.
     *
     * @return ResponseEntity
     */
    @PutMapping("/task/{id}")
    public ResponseEntity<CustomResponse<Task>> updateTask(
            @PathVariable String id, @Valid @RequestBody TaskRequestTO requestBody) {
        log.info("[updateTask - init] [requestBody][{}]", id);
        CustomResponse<Task> responseBody = new CustomResponse<>();
        try{
            Task taskResponse = taskService.updateTask(id, requestBody);
            responseBody.setBody(taskResponse);
            responseBody.setMessage(HttpStatus.CREATED.getReasonPhrase());
            return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
        }catch (TaskException e) {
            responseBody.setMessage(HttpStatus.NOT_MODIFIED.getReasonPhrase());
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_MODIFIED);
        }
    }

    /**
     /**
     * Task to delete service.
     *
     * @return ResponseEntity
     */
    @DeleteMapping("/task/{id}")
    public ResponseEntity<CustomResponse<Task>> deleteTask(@PathVariable String id) {
        log.info("[deleteTask - init] [requestBody][{}]", id);
        CustomResponse<Task> responseBody = new CustomResponse<>();
        try{
            Task taskResponse = taskService.deleteTask(id);
            responseBody.setBody(taskResponse);
            responseBody.setMessage(HttpStatus.OK.getReasonPhrase());
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }catch (TaskException e) {
            responseBody.setMessage(HttpStatus.NOT_MODIFIED.getReasonPhrase());
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_MODIFIED);
        }
    }

     /**
     * Toggle state of a task, to mark done or undone.
     * If the task is done, then will be undone
     * and vice versa.
     *
     * @param id task id.
     * @return ResponseEntity.
     */
    @GetMapping("/task/toggle/{id}")
    public ResponseEntity<CustomResponse<Task>> toggleState(@PathVariable String id) {
        log.info("[toggleState - init] [id][{}]", id);
        try{
            Task taskResponse = taskService.updateState(id);
            responseTaskBody.setBody(taskResponse);
            responseTaskBody.setMessage(HttpStatus.OK.getReasonPhrase());
            return new ResponseEntity<>(responseTaskBody, HttpStatus.OK);
        }catch (TaskException e) {
            responseTaskBody.setMessage(HttpStatus.NOT_MODIFIED.getReasonPhrase());
            return new ResponseEntity<>(responseTaskBody, HttpStatus.NOT_MODIFIED);
        }
    }
}
