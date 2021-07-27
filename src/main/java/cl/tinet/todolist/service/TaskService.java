package cl.tinet.todolist.service;

import cl.tinet.todolist.dao.TaskRepository;
import cl.tinet.todolist.exceptions.TaskException;
import cl.tinet.todolist.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

/**
 * Service class for Task entity.
 */
@Service
@Slf4j
public class TaskService {

    /**
     * References to TaskRepository.
     */
    private final TaskRepository taskRepository;

    /**
     * Constructor of the class.
     * @param taskRepository see {@link #taskRepository}
     */
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Service that gets the list of all task in database.
     *
     * @return {@code List<Task>} list of all tasks.
     */
    @Transactional(readOnly = true)
    public List<Task> getTasks(){
        log.info("[getTasks - init]");
        List<Task> tasks = new ArrayList<>();
        try{
            tasks = taskRepository.findAll();
        }catch (Exception e){
            log.error("[getTasks - error] [tasks] [{}]",tasks , e);
            throw new TaskException("error to get tasks");
        }
        log.info("[getTasks - end]");
        return tasks;
    }
}
