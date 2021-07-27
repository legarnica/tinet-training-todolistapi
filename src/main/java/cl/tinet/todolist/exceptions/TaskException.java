package cl.tinet.todolist.exceptions;

public class TaskException extends RuntimeException {
    /**
     * constructor de la clase.
     *
     */
    public TaskException(String message) {
        super(message);
    }

    /**
     * constructor de la clase.
     *
     */
    public TaskException(Exception e) {
        super(e);
    }

    /**
     * constructor de la clase.
     *
     */
    public TaskException(String message, Exception e) {
        super(message, e);
    }
}
