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
     * Value of done task.
     */
    private static final int DONE = 1;

    /**
     * Value of a active task.
     */
    private static final boolean ACTIVE = true;

    /**
     * Value of a inactive task.
     */
    private static final boolean INACTIVE = false;

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
            log.error("[getTaskById - error] [while trying to transform][taskId][{}]", taskId, e);
            throw new TaskException("getTaskById - error");
        }
        if(task.getId() == null) {
            log.error("[getTaskById - error] [task with][id][{}][was not found]", taskId);
            throw new TaskException("getTaskById - error");
        }
        log.info("[getTasks - success] [task] [{}] [was found]",task);

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
        log.info("[setTask] [the] [task] [{}] [will be saved]", task);
        Task taskSaved = taskRepository.save(task);
        log.info("[setTask] [the] [task] [{}] [was saved]", taskSaved);
        if(taskSaved.getId() == null) {
            log.error("[setTask - error] [task with][id][{}][was not saved]", taskSaved);
            throw new TaskException("setTask - error");
        }
        log.info("[setTask - success] [task] [{}] [was set]",taskSaved);
        TaskResponseTO responseTO = TaskResponseTO.builder()
                .title(taskSaved.getTitle())
                .description(taskSaved.getDescription())
                .state(taskSaved.getState())
                .createAt(taskSaved.getCreateAt())
                .updatedAt(taskSaved.getUpdatedAt()).build();
        log.info("[setTask - success] [responseTO] [{}]",responseTO);
        return responseTO;
    }

    /**
     * Update task service.
     *
     * @param requestId task id.
     * @return updated task.
     */
    @Transactional
    public Task updateTask(String requestId, TaskRequestTO requestBody) {
        log.info("[updateTask - init] [requestId, requestBody] [{}, {}]", requestId, requestBody);
        Task task = this.getTaskById(requestId);
        task.setTitle(requestBody.getTitle());
        task.setDescription(requestBody.getDescription());
        task.setUpdatedAt(getActualDate());

        log.info("[updateTask] [task to be saved] [task] [{}]", task);
        Task updatedTask = taskRepository.save(task);
        log.info("[updateTask - success] [updatedTask] [{}]", updatedTask);

        return updatedTask;
    }

    /**
     * Delete task service.
     *
     * @param requestId task id.
     * @return updated task.
     */
    @Transactional
    public Task deleteTask(String requestId) {
        log.info("[deleteTask - init] [requestId] [{}]", requestId);
        Task taskToBeDeleted = this.getTaskById(requestId);
        taskToBeDeleted.setActive(INACTIVE);
        taskToBeDeleted.setUpdatedAt(getActualDate());
        log.info("[deleteTask] [task to be deleted] [taskToBeDeleted] [{}]", taskToBeDeleted);
        Task deletedTask = taskRepository.save(taskToBeDeleted);
        log.info("[deleteTask - success] [deletedTask] [{}]", deletedTask);
        return deletedTask;
    }

    /**
     * Delete task service.
     *
     * @param requestId task id.
     * @return updated task.
     */
    @Transactional
    public Task updateState(String requestId) {
        log.info("[updateState - init] [requestId] [{}]", requestId);
        Task taskBeforeUpdated = this.getTaskById(requestId);
        int newState = (taskBeforeUpdated.getState() == DONE) ? UNDONE : DONE;
        taskBeforeUpdated.setState(newState);
        taskBeforeUpdated.setUpdatedAt(getActualDate());

        log.info("[updateState] [task to be updated] [taskBeforeUpdated] [{}]", taskBeforeUpdated);
        Task updatedTask = taskRepository.save(taskBeforeUpdated);
        log.info("[updateState - success] [deletedTask] [{}]", updatedTask);

        return updatedTask;
    }

    /**
     * Get actual locale date, in string format.
     *
     * @return String actual locale date.
     */
    private String getActualDate() {
        log.info("[getActualDate - init]");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Locale locale = new Locale("es", "CH");
        TimeZone timeZone = TimeZone.getTimeZone("America/Santiago");
        Date date = getInstance(timeZone, locale).getTime();
        log.info("[getActualDate - end]: [date] [{}]", date);
        return format.format(date);
    }

}
