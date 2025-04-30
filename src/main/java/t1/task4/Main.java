package t1.task4;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import t1.task4.entity.User;
import t1.task4.service.UserService;

import java.util.Arrays;
import java.util.List;

/*

- Разверните локально postgresql БД, создайте таблицу users (id bigserial primary key, username varchar(255) unique);

- Создайте Maven проект и подключите к нему: драйвер postgresql, hickaricp, spring context.

- Создайте пул соединений в виде Spring бина

- Создайте класс User (Long id, String username)

- Реализуйте в виде бина класс UserDao который позволяет выполнять CRUD операции над пользователями

- Реализуйте в виде бина UserService, который позволяет: создавать, удалять, получать одного, получать всех пользователей из базы данных

- Создайте Spring Context, получите из него бин UserService и выполните все возможные операции
 */
@ComponentScan
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        UserService userService = context.getBean(UserService.class);

        try {
            // Получить всех пользователей
            List<User> users = userService.getAllUsers();
            System.out.println("Все пользователи: " + Arrays.toString(users.toArray()));
            // Создать пользователя
            long id = userService.createUser(new User(null, "Иван Иванович Иванов"));
            // Получить по id
            System.out.println("createdUser: " + userService.getUser(id));
            // Обновить пользователя
            userService.updateUser(new User(id, "Василий Васильевич Васильев"));
            System.out.println("updatedUser: " + userService.getUser(id));
            // Удалить
            userService.deleteUser(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
