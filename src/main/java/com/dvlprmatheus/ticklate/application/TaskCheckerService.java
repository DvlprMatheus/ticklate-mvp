package com.dvlprmatheus.ticklate.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dvlprmatheus.ticklate.domain.Notifier;
import com.dvlprmatheus.ticklate.domain.Task;
import com.dvlprmatheus.ticklate.domain.TaskRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskCheckerService {

    private final TaskRepository taskRepository;
    private final Notifier notifier;

    private static final String OVERDUE_MESSAGE = "[TICKLATE] - The task '%s' is overdue.";

    public TaskCheckerService(TaskRepository taskRepository, Notifier notifier) {
        this.taskRepository = taskRepository;
        this.notifier = notifier;
    }

    @Transactional
    public void checkOverdueTasks() {
        List<Task> tasks = taskRepository.findByDueDateBeforeAndNotifiedFalse(LocalDateTime.now());
        for (Task task : tasks) {
            notifier.sendReminder(String.format(OVERDUE_MESSAGE, task.getTitle()));
            task.markAsNotified();
        }
        taskRepository.saveAll(tasks);
    }
}
