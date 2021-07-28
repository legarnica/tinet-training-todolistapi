package cl.tinet.todolist.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that test CustomResponseTest pojo.
 */
@ExtendWith(SpringExtension.class)
class CustomResponseTest {

    /**
     * Dummy value for the message attribute.
     */
    private final static String MESSAGE = "message";

    /**
     * Dummy value for the body attribute.
     */
    private final static String BODY = "body";

    /**
     * Verify the correct instance of CustomResponse.
     */
    @Test
    void CustomResponseInstance() {
        CustomResponse<String> custom = new CustomResponse<>();
        custom.setMessage(MESSAGE);
        custom.setBody(BODY);
        assertEquals(MESSAGE, custom.getMessage());
        assertEquals(BODY, custom.getBody());
        assertTrue(custom.toString().contains(BODY));
    }

}