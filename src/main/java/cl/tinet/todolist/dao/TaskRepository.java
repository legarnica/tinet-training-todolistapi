package cl.tinet.todolist.dao;

import cl.tinet.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository or Dao class for Task entity.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.active = TRUE")
    List<Task> findAll();

    /**
     * find a task by id.
     *
     * @return Optional task.
     */
    @Query("SELECT t FROM Task t WHERE t.id = ?1 and t.active = TRUE")
    Optional<Task> findById(Long id);
}
