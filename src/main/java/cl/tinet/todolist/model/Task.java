package cl.tinet.todolist.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

/**
 * Entity class that admin task table in todolist database.
 */
@Entity
@Table(name = "task")
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    /**
     * id field column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    /**
     * title field column.
     */
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * description field column.
     */
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * state field column.
     * 0: to-do
     * 1: done
     */
    @NotNull
    @Column(name = "state", nullable = false)
    private Integer state;

    /**
     * create_at field column.
     */
    @NotNull
    @Column(name = "create_at", nullable = false)
    private String createAt;

    /**
     * updated_at field column.
     */
    @NotNull
    @Column(name = "updated_at", nullable = false)
    private String updatedAt;

}
