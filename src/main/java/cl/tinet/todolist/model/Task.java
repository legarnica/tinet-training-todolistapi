package cl.tinet.todolist.model;


import com.sun.istack.NotNull;//NOSONAR
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
     * active field column.
     */
    @NotNull
    @Column(name = "active", nullable = false)
    private boolean active;

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
