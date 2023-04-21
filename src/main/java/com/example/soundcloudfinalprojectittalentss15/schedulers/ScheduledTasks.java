package com.example.soundcloudfinalprojectittalentss15.schedulers;

import com.example.soundcloudfinalprojectittalentss15.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

//    @Scheduled(cron = "0 * * * * *") //   A cron expression defines a set of times to execute a task. In this example,
//                                          the task will be executed at the top of every minute.
    @Scheduled(fixedRate = 60000) // The task will be executed every minute, regardless of when the previous execution finished.
    public void deleteNonVerifiedUsers() {
        logger.info("Starting deleteNonVerifiedUsers scheduled task");
        userService.deleteNonVerifiedUsers();
        logger.info("Finished deleteNonVerifiedUsers scheduled task");
    }
}
