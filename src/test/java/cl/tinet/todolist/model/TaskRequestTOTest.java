package cl.tinet.todolist.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verify {@link TaskRequestTO}.
 */
class TaskRequestTOTest {

    /**
     * Dummy title.
     */
    private static final String TITLE = "title";

    /**
     * Dummy descripcion.
     */
    private static final String DESCRIPTION = "description";

    /**
     * Verify a TaskRequestTO instance.
     */
    @Test
    void instanceVerify() {
        TaskRequestTO to = new TaskRequestTO();
        to.setTitle(TITLE);
        to.setDescription(DESCRIPTION);

        assertEquals(TITLE, to.getTitle());
        assertEquals(DESCRIPTION, to.getDescription());

        assertTrue(to.toString().contains(DESCRIPTION));
    }

}