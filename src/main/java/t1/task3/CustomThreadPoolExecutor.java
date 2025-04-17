package t1.task3;

import java.util.LinkedList;

public class CustomThreadPoolExecutor {
    private final LinkedList<Runnable> queue;
    private final Object monitor = new Object();
    private volatile boolean terminated = false;
    private final Thread[] threads;

    CustomThreadPoolExecutor(Integer poolSize) {
        this.queue = new LinkedList<>();

        if (poolSize <= 0) {
            throw new IllegalArgumentException("Pool size must be positive");
        }

        this.threads = new Thread[poolSize];

        for (int i = 0; i < poolSize; i++) {
            this.threads[i] = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted() && !terminated) {
                    System.out.println(Thread.currentThread().getName() + " start of loop");
                    Runnable task = this.take();

                    if (task != null) {
                        try {
                            System.out.println(Thread.currentThread().getName() + " run task");
                            task.run();
                        } catch (Exception e) {
                            System.out.println("Task error: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
            });

            this.threads[i].start();
        }
    }

    private Runnable take() {
        synchronized (monitor) {
            while (queue.isEmpty() && !terminated) {
                try {
                    System.out.println(Thread.currentThread().getName() + " wait");
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }

            if (!queue.isEmpty()) {
                return queue.remove();
            }

            return null;
        }
    }

    public void execute(Runnable task) {
        if (task == null) {
            throw new NullPointerException("Task can not be null");
        }

        synchronized (monitor) {
           if (terminated) {
               throw new IllegalStateException("Thread pool has been shutdown");
           }

           queue.add(task);
           monitor.notifyAll();
        }
    }

    public void shutdown() {
        synchronized (monitor) {
            System.out.println("Thread pool terminated");
            terminated = true;
            monitor.notifyAll();
        }
    }

    public void awaitTermination() {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
