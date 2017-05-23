package com.gcmassari.mastermind.cronjobs;


import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gcmassari.mastermind.data.DataService;
import com.gcmassari.mastermind.data.GlobalParameters;

@Component
public class RemoveSessionsAfterSomeTimeCronJob {

    static {
        // TODO GC remove sysout, log this information
        System.out.println("RemoveSessionsAfterSomeTimeCronJob: cron job executed every 10 min."); // Each 10 min <-> "*/4 * * * * ?"
    }

    @Autowired
    private DataService dataService;

    @Scheduled(cron="0 */10 * * * ?") // every 10 minutes
    public void removeOldSessions() {
        DateTime now = new DateTime();
        DateTime date = now.minusMinutes(GlobalParameters.DEFAULT_MAX_SESSION_AGE_IN_MINUTES);
        dataService.removeGameSessionsOlderThan(date.toDate());
    }
}
