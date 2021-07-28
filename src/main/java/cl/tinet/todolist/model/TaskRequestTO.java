package cl.tinet.todolist.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * Transport class for Task insert or update.
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
public class TaskRequestTO {
    /**
     * title field column.
     */
    @NotBlank(message = "title is mandatory")
    private String title;

    /**
     * description field column.
     */
    @NotBlank(message = "description is mandatory")
    private String description;
}
