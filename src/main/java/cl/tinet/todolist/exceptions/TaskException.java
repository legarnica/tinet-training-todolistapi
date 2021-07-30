package cl.tinet.todolist.exceptions;

public class TaskException extends RuntimeException {

    /**
     * Constant message for task not found.
     */
    public static final String TASK_NOT_FOUND_MSG = "Task not found";

    /**
     * Constant message for NumberFormatException message.
     */
    public static final String NUMBER_FORMAT_EXCEPTION_MSG = "NumberFormatException";

    /**
     * Constant message for NumberFormatException message.
     */
    public static final String TASK_WAS_NOT_UPDATED_MSG = "Task save or update failed";

    /**
     * Class constructor.
     *
     * @param message message passed.
     */
    public TaskException(String message) {
        super(message);
    }


    /**
     * Class constructor.
     *
     * @param e exception passed.
     */
    public TaskException(Exception e) {
        super(e);
    }


    /**
     * Class constructor.
     *
     * @param message message passed.
     * @param e exception passed.
     */
    public TaskException(String message, Exception e) {
        super(message, e);
    }
}
