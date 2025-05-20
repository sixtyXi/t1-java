package t1.task1;

public class Main {
    public static void main(String[] args) {
        try {
            TestRunner.runTests(Testable.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}