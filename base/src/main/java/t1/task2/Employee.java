package t1.task2;

public class Employee {
    private String name;
    private int age;
    private String title;

    public Employee(String name, int age, String title) {
        this.name = name;
        this.age = age;
        this.title = title;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", title='" + title + '\'' +
                '}';
    }
}
