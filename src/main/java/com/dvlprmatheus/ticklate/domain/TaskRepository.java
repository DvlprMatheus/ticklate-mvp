package com.dvlprmatheus.ticklate.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    /**
     * Find all tasks that are due before the current time and not notified
     * 
     * @param now The current time
     * @return ListL<Task>
     */
    @Query("SELECT t FROM Task t WHERE t.dueDate < :now AND t.notified = false")
    List<Task> findByDueDateBeforeAndNotifiedFalse(@Param("now") LocalDateTime now);
}
