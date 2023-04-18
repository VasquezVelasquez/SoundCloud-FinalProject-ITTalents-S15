package com.example.soundcloudfinalprojectittalentss15.schedulers;

import com.example.soundcloudfinalprojectittalentss15.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private UserService userService;

//    @Scheduled(cron = "0 * * * * *") // Run every minute
    @Scheduled(fixedRate = 60000) // Run every 1 minute
    public void deleteNonVerifiedUsers() {
        userService.deleteNonVerifiedUsers();
    }
}
