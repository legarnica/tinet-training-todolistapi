package cl.tinet.todolist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Transport class for Task insert or update.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TaskResponseTO {
    /**
     * title field column.
     */
    private String title;

    /**
     * description field column.
     */
    private String description;

    /**
     * state field column.
     */
    private Integer state;

    /**
     * create_at field column.
     */
    private String createAt;

    /**
     * updated_at field column.
     */
    private String updatedAt;
}
