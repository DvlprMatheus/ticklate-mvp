package com.dvlprmatheus.ticklate.infra;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dvlprmatheus.ticklate.application.TaskCheckerService;

@Component
public class Scheduler {

    private final TaskCheckerService taskCheckerService;

    public Scheduler(TaskCheckerService checker) {
        this.taskCheckerService = checker;
    }

    @Scheduled(fixedDelay = 5000) // Check for overdue tasks every 5 seconds
    public void run() {
        taskCheckerService.checkOverdueTasks();
    }
}
