package cl.tinet.todolist.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verify {@link TaskResponseTOTest}
 */
class TaskResponseTOTest {

    /**
     * Dummy value for title TaskResponseTO attribute.
     */
    private final static String TITLE = "TITLE";

    /**
     * Dummy value for description TaskResponseTO attribute.
     */
    private final static String DESCRIPTION = "DESCRIPTION";

    /**
     * Dummy value for state TaskResponseTO attribute.
     */
    private final static int STATE = 0;

    /**
     * Dummy value for createAt Task attribute.
     */
    private final static String CREATE_AT = "01-01-2021 00:00:00";

    /**
     * Dummy value for updatedAt Task attribute.
     */
    private final static String UPDATED_AT = "01-01-2021 00:00:00";

    /**
     * Test Task members and default constructor.
     */
    @Test
    void instanceVerification() {
        TaskResponseTO task = new TaskResponseTO();
        task.setTitle(TITLE);
        task.setDescription(DESCRIPTION);
        task.setState(STATE);
        task.setCreateAt(CREATE_AT);
        task.setUpdatedAt(UPDATED_AT);
        assertEquals(TITLE, task.getTitle());
        assertEquals(DESCRIPTION, task.getDescription());
        assertEquals(STATE, task.getState());
        assertEquals(CREATE_AT, task.getCreateAt());
        assertEquals(UPDATED_AT, task.getUpdatedAt());
        assertTrue(task.toString().contains(DESCRIPTION));
    }

}