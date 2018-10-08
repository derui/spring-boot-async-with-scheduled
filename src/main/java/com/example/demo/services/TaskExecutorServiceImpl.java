package com.example.demo.services;

import com.example.demo.Task;
import com.example.demo.repositories.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskExecutorServiceImpl implements TaskExecutorService {
    private final static Logger logger = LoggerFactory.getLogger(TaskExecutorServiceImpl.class);
    private final TaskRepository taskRepository;

    @Autowired
    public TaskExecutorServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void execute(Task task) {
        task.setDone(task.getDone() + 1);
        taskRepository.save(task);
    }
}
