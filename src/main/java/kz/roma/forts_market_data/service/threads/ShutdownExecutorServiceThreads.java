package kz.roma.forts_market_data.service.threads;

import java.util.concurrent.ExecutorService;

public class ShutdownExecutorServiceThreads implements Runnable{
    private ExecutorService executorService;

    public ShutdownExecutorServiceThreads(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void run() {
        executorService.shutdown();
    }
}
