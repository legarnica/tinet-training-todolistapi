package cl.tinet.todolist.dao;

import cl.tinet.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository or Dao class for Task entity.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    /**
     * find a task by id.
     *
     * @return Optional task.
     */
    Optional<Task> findById(Long id);
}
