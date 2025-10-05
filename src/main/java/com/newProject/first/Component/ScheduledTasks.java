package com.newProject.first.Component;

import com.newProject.first.DAO.refreshTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTasks {

    refreshTokenRepo tokenRepo;

    @Autowired
    public ScheduledTasks(refreshTokenRepo tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    // cron : every 12 h
    @Scheduled(cron ="0 0 */12 * * ?")
    public void removeExpiredTokens()
    {
        tokenRepo.deleteExpiredTokens(new Date());
    }
}
