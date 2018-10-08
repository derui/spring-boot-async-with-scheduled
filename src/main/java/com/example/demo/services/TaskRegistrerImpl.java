package com.example.demo.services;

import com.example.demo.Task;
import com.example.demo.repositories.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class TaskRegistrerImpl implements TaskRegistrer {
    private static final Logger logger = LoggerFactory.getLogger(TaskRegistrerImpl.class);
    private final TaskRepository repository;
    private final TaskExecutorService service;

    @Autowired
    public TaskRegistrerImpl(TaskRepository repository, TaskExecutorService service) {
        this.repository = repository;
        this.service = service;
    }

    private Random random = new Random(System.currentTimeMillis());

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Task register() {
        Task task = new Task();
        task.setAmount(random.nextInt(10));
        Task registeredTask = repository.save(task);
        return registeredTask;
    }

    @Override
    @Async
    public void execute(int id) {
        Optional<Task> task = repository.findById(id);

        task.ifPresent(v -> {

            while (v.getDone() < v.getAmount()) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                service.execute(v);
            }

            logger.info("Done task with async {}", id);
        });
    }
}
