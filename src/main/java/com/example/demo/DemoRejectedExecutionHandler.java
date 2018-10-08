package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;

public class DemoRejectedExecutionHandler extends ThreadPoolExecutor.CallerRunsPolicy {

    private static final Logger logger = LoggerFactory.getLogger(DemoRejectedExecutionHandler.class);

    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        logger.warn("Rejected and re-run a task {}", r);
        super.rejectedExecution(r, executor);
    }
}
