package com.example.demo;

import com.example.demo.services.TaskRegistrer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableJpaRepositories
public class DemoApplication {
    final static Logger logger = LoggerFactory.getLogger(DemoApplication.class);
    @Autowired
    private TaskRegistrer registrer;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Scheduled(fixedDelay = 200)
    public void runTask() {
        Task task = registrer.register();
        registrer.execute(task.getId());
        logger.info("end scheduled task");
    }
}
