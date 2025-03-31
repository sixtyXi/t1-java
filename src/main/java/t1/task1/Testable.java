package t1.task1;

import t1.task1.annotations.*;

public class Testable {
    public static void staticMethod3() {
        System.out.println("staticMethod3");
    }

    @BeforeSuite
    private static void staticMethod1() {
        System.out.println("staticMethod1");
    }
    @Test(priority = 3)
    public void method1() {
        System.out.println("method1");
    }

    @Test(priority = 2)
    public void method2() {
        System.out.println("method2");
    }

    @Test(priority = 10)
    public void method3() {
        System.out.println("method3");
    }

    @AfterSuite
    public static void staticMethod2() {
        System.out.println("staticMethod2");
    }

    @BeforeTest
    public void beforeTest1() {
        System.out.println("----------------before----------------");
    }

    @AfterTest
    public void afterTest1() {
        System.out.println("----------------after----------------");
    }


    @Test(priority = 1)
    @CsvSource("20, Java, 10, true ")
    public void testMethod(int a, String b, int c, boolean d) {
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
        System.out.println("d = " + d);
    }
}
