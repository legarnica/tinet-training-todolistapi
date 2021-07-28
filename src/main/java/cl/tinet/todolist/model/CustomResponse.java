package cl.tinet.todolist.model;


import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class CustomResponse<T> {
    /**
     * The info message of response.
     */
    private String message;

    /**
     * The body of response.
     */
    private T body;
}
