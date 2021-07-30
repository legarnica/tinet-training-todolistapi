package cl.tinet.todolist.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
    @Size(min = 3, max = 15)
    private String title;

    /**
     * description field column.
     */
    @NotBlank(message = "description is mandatory")
    @Size(max = 100)
    private String description;
}
