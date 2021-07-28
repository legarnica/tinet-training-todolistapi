package cl.tinet.todolist.service;

import cl.tinet.todolist.dao.TaskRepository;
import cl.tinet.todolist.exceptions.TaskException;
import cl.tinet.todolist.model.Task;
import cl.tinet.todolist.model.TaskRequestTO;
import cl.tinet.todolist.model.TaskResponseTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static java.util.Calendar.getInstance;

/**
 * Service class for Task entity.
 */
@Service
@Slf4j
public class TaskService {

    /**
     * Value of undone task.
     */
    private static final int UNDONE = 0;

    /**
     * Value of a active task.
     */
    private static final boolean ACTIVE = true;

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

    /**
     * Service that gets a task using his id.
     *
     * @param taskId id of the task
     * @return {@code Task} a task.
     */
    @Transactional(readOnly = true)
    public Task getTaskById(String taskId){
        log.info("[getTaskById - init] [id] [{}]", taskId);
        Task task = null;
        try {
            task = taskRepository.findById(Long.valueOf(taskId)).orElseGet(Task::new);
        }catch (NumberFormatException e){
            log.error("[getTaskById - error] [taskId] [{}]",taskId, e);
            throw new TaskException("id structure error");
        }
        if(task.getId() == null) {
            log.error("[getTaskById - error] [task] [{}]",task);
            throw new TaskException("error to get task");
        }
        log.info("[getTasks - end] [task] [{}]",task);
        return task;
    }

    /**
     * Insert a task in database.
     *
     * @param taskRequestTO data of task request.
     * @return data of inserted task response.
     */
    @Transactional
    public TaskResponseTO setTask(TaskRequestTO taskRequestTO) {
        log.info("[setTask - init] [taskRequestTO] [{}]", taskRequestTO);

        Task task = new Task();
        task.setTitle(taskRequestTO.getTitle());
        task.setDescription(taskRequestTO.getDescription());
        task.setState(UNDONE);
        task.setActive(ACTIVE);
        task.setCreateAt(getActualDate());
        task.setUpdatedAt(getActualDate());

        Task taskSaved = taskRepository.save(task);

        if(taskSaved.getId() == null) {
            log.error("[setTask - error] [taskSaved] [{}]",taskSaved);
            throw new TaskException("error setting task");
        }

        return TaskResponseTO.builder()
                .title(taskSaved.getTitle())
                .description(taskSaved.getDescription())
                .state(taskSaved.getState())
                .createAt(taskSaved.getCreateAt())
                .updatedAt(taskSaved.getUpdatedAt()).build();
    }

    /**
     * Update task service.
     *
     * @param requestId task id.
     * @return updated task.
     */
    @Transactional
    public Task updateTask(String requestId, TaskRequestTO requestBody) {
        Task task = this.getTaskById(requestId);
        task.setTitle(requestBody.getTitle());
        task.setDescription(requestBody.getDescription());
        task.setUpdatedAt(getActualDate());

        Task updatedTask = null;

        try {
            updatedTask = taskRepository.save(task);
        }catch (Exception e) {
            log.error("[deleteTask - error] [task] [{}]",task);
            throw new TaskException("error updating task");
        }

        return updatedTask;
    }

    /**
     * Get actual locale date, in string format.
     *
     * @return String actual locale date.
     */
    private String getActualDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Locale locale = new Locale("es", "CH");
        TimeZone timeZone = TimeZone.getTimeZone("America/Santiago");

        Date date = getInstance(timeZone, locale).getTime();

        return format.format(date);

    }
}
