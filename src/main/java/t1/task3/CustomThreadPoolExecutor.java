package t1.task3;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomThreadPoolExecutor {
    private final LinkedList<Runnable> queue;
    private final AtomicBoolean terminated = new AtomicBoolean(false);
    private final Thread[] threads;

    CustomThreadPoolExecutor(int poolSize) {
        this.queue = new LinkedList<>();

        if (poolSize <= 0) {
            throw new IllegalArgumentException("Pool size must be positive");
        }

        this.threads = new Thread[poolSize];

        for (int i = 0; i < poolSize; i++) {
            Thread thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted() && !terminated.get()) {
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

            thread.setName("Worker-" + (i + 1));
            thread.start();
            this.threads[i] = thread;
        }
    }

    private Runnable take() {
        synchronized (queue) {
            while (queue.isEmpty() && !terminated.get()) {
                try {
                    System.out.println(Thread.currentThread().getName() + " wait");
                    queue.wait();
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

        synchronized (queue) {
           if (terminated.get()) {
               throw new IllegalStateException("Thread pool has been shutdown");
           }

           queue.add(task);
           queue.notifyAll();
        }
    }

    public void shutdown() {
        synchronized (queue) {
            System.out.println("Thread pool terminated");
            terminated.set(true);
            queue.notifyAll();
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
