package cl.tinet.todolist.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that test Task entity.
 */
@ExtendWith(SpringExtension.class)
class TaskTest {
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
     * Test Task members and default constructor.
     */
    @Test
    void taskEntity() {
        Task task = new Task();
        task.setId(ID);
        task.setTitle(TITLE);
        task.setDescription(DESCRIPTION);
        task.setState(STATE);
        task.setActive(ACTIVE);
        task.setCreateAt(CREATE_AT);
        task.setUpdatedAt(UPDATED_AT);
        assertEquals(ID, task.getId());
        assertEquals(TITLE, task.getTitle());
        assertEquals(DESCRIPTION, task.getDescription());
        assertEquals(STATE, task.getState());
        assertEquals(ACTIVE, task.isActive());
        assertEquals(CREATE_AT, task.getCreateAt());
        assertEquals(UPDATED_AT, task.getUpdatedAt());
        assertTrue(task.toString().contains(DESCRIPTION));
    }

    /**
     * Test for builder Task service.
     */
    @Test
    void taskBuilderEntity() {
        Task task = Task.builder()
                .id(ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .state(STATE)
                .active(ACTIVE)
                .createAt(CREATE_AT)
                .updatedAt(UPDATED_AT).build();
        assertEquals(UPDATED_AT, task.getUpdatedAt());
    }

}