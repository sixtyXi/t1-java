package t1.task3;

/*
Попробуйте реализовать собственный пул потоков.
В качестве аргументов конструктора пулу передается его емкость (количество рабочих потоков).
Как только пул создан, он сразу инициализирует и запускает потоки.
Внутри пула очередь задач на исполнение организуется через LinkedList<Runnable>.
При выполнении у пула потоков метода execute(Runnable), указанная задача должна попасть в очередь исполнения,
и как только появится свободный поток – должна быть выполнена.
Также необходимо реализовать метод shutdown(), после выполнения которого новые задачи больше не принимаются пулом
(при попытке добавить задачу можно бросать IllegalStateException), и все потоки для которых больше нет задач завершают свою работу.
Дополнительно можно добавить метод awaitTermination() без таймаута, работающий аналогично стандартным пулам потоков
 */
public class Main {
    public static void main(String[] args) {
        CustomThreadPoolExecutor customThreadPoolExecutor = new CustomThreadPoolExecutor(4);
        customThreadPoolExecutor.execute(() -> System.out.println("task-1"));
        customThreadPoolExecutor.execute(() -> System.out.println("task-2"));
        customThreadPoolExecutor.execute(() -> System.out.println("task-3"));
        customThreadPoolExecutor.execute(() -> System.out.println("task-4"));
        customThreadPoolExecutor.execute(() -> System.out.println("task-5"));
        customThreadPoolExecutor.execute(() -> System.out.println("task-6"));

        customThreadPoolExecutor.shutdown();
        customThreadPoolExecutor.awaitTermination();
        System.out.println("After awaitTermination");
    }
}
