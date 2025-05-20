package t1.task1;

import t1.task1.annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class TestRunner {
    public static <T> void runTests(Class<T> c) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
      ArrayList<Method> beforeSuiteMethods = new ArrayList<Method>();
      ArrayList<Method> afterSuiteMethods = new ArrayList<Method>();
      ArrayList<Method> testMethods = new ArrayList<>();
      ArrayList<Method> beforeTestMethods = new ArrayList<>();
      ArrayList<Method> afterTestMethods = new ArrayList<>();

      for (Method method : c.getDeclaredMethods()) {
          if (Modifier.isAbstract(method.getModifiers())) {
              throw new IllegalArgumentException("Method can not be abstract");
          }

          if (method.isAnnotationPresent(BeforeSuite.class)) {
              if (!Modifier.isStatic(method.getModifiers())) {
                  throw new IllegalArgumentException("BeforeSuite must be applied to static methods only");
              }

              beforeSuiteMethods.add(method);

              if (beforeSuiteMethods.size() > 1) {
                  throw new IllegalArgumentException("BeforeSuite annotation must be only one");
              }
          }

          if (method.isAnnotationPresent(AfterSuite.class)) {
              if (!Modifier.isStatic(method.getModifiers())) {
                  throw new IllegalArgumentException("AfterSuite annotation must be applied to static methods only");
              }

              afterSuiteMethods.add(method);

              if (afterSuiteMethods.size() > 1) {
                  throw new IllegalArgumentException("AfterSuite annotation must be only one");
              }
          }

          if (method.isAnnotationPresent(BeforeTest.class)) {
              beforeTestMethods.add(method);
          }

          if (method.isAnnotationPresent(AfterTest.class)) {
              afterTestMethods.add(method);
          }


          Test testAnnotation = method.getAnnotation(Test.class);

          if (testAnnotation != null) {
              if (Modifier.isStatic(method.getModifiers())) {
                  throw new IllegalArgumentException("Test annotation must be applied to non static methods only");
              }

              if (testAnnotation.priority() < 1 || testAnnotation.priority() > 10) {
                  throw new IllegalArgumentException("Priority must be from 1 to 10");
              }


              testMethods.add(method);
          }
      }

      for (Method test : beforeSuiteMethods) {
          invokeMethod(c, test);
      }

      testMethods.sort((a, b) -> {
        return b.getAnnotation(Test.class).priority() - a.getAnnotation(Test.class).priority();
      });

      T instance = c.getConstructor().newInstance();

      for (Method test : testMethods) {
          for (Method beforeTestMethod : beforeTestMethods) {
             invokeMethod(instance, beforeTestMethod);
          }

          if (test.isAnnotationPresent(CsvSource.class)) {

              runCsvMethod(instance, test);
          } else {
              invokeMethod(instance, test);
          }

          for (Method afterTestMethod : afterTestMethods) {
              invokeMethod(instance, afterTestMethod);
          }
      }

      for (Method test : afterSuiteMethods) {
          invokeMethod(c, test);
      }
    }

    private static <T> void runCsvMethod(T instance, Method method) throws InvocationTargetException, IllegalAccessException {
        String value = method.getAnnotation(CsvSource.class).value();

        if (Modifier.isStatic(method.getModifiers())) {
            throw new IllegalArgumentException("CsvSource annotation must be applied to non static methods only");
        }

        String[] values = value.split(",");

        if (values.length != 4) {
            throw new IllegalArgumentException("Arguments must be int, String, int, boolean");
        }

        int arg1 = Integer.parseInt(values[0].trim());
        String arg2 = values[1].trim();
        int arg3 = Integer.parseInt(values[2].trim());
        String elem4 = values[3].trim();
        boolean arg4;
        if (elem4.equals("true") || elem4.equals("false")) {
            arg4 = elem4.equals("true");
        } else {
            throw new IllegalArgumentException("arg4 must be boolean");
        }
        checkScvMethodParamTypes(method);
        invokeMethod(instance, method, arg1, arg2, arg3, arg4);
    }

    private static void checkScvMethodParamTypes(Method method) {
        Class<?>[] paramTypes = method.getParameterTypes();

        if (paramTypes.length != 4) {
            throw new IllegalArgumentException("Method must have 4 parameters");
        }
        if (!paramTypes[0].equals(int.class)) {
            throw new IllegalArgumentException("First method argument must be int");
        }
        if (!paramTypes[1].equals(String.class)) {
            throw new IllegalArgumentException("Second method argument must be String");
        }
        if (!paramTypes[2].equals(int.class)) {
            throw new IllegalArgumentException("Third method argument must be int");
        }
        if (!paramTypes[3].equals(boolean.class)) {
            throw new IllegalArgumentException("Fourth method argument must be boolean");
        }
    }


    private static <T> void invokeMethod(T c, Method test, Object ...args) throws InvocationTargetException, IllegalAccessException {
        boolean isPrivate = Modifier.isPrivate(test.getModifiers());
        if (isPrivate) {
            test.setAccessible(true);
        }

        test.invoke(c, args);

        if (isPrivate) {
            test.setAccessible(false);
        }
    }
}
