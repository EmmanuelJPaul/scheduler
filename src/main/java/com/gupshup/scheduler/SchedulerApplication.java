package com.gupshup.scheduler;

import com.gupshup.scheduler.task.MessengerTask;
import com.gupshup.scheduler.task.PollerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

import java.util.Timer;

@ComponentScan({"com.*"})
@SpringBootApplication()
public class SchedulerApplication {

    @Value("${gupshup.poll.delay}")
    private long delay;

    @Autowired
    private Timer timer;

    @Autowired
    private PollerTask pollerTask;
    
    @Autowired
    private MessengerTask messengerTask;
    
    public static void main(String[] args) {
	SpringApplication.run(SchedulerApplication.class, args);
    }
    
    @EventListener(ApplicationReadyEvent.class)
    public void startScheduler() {
        timer.scheduleAtFixedRate(pollerTask, 0, delay);
        timer.scheduleAtFixedRate(messengerTask, 5000, delay);
    }
}
