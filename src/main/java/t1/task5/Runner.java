package t1.task5;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import t1.task5.entity.User;
import t1.task5.service.UserService;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Получить всех пользователей
            List<User> users = userService.getAllUsers();
            System.out.println("Все пользователи: " + Arrays.toString(users.toArray()));
            // Создать пользователя
            long id = userService.createUser(new User(null, "Иван Иванович Иванов"));
            // Получить по id
            System.out.println("createdUser: " + userService.getUserById(id));
            // Обновить пользователя
            userService.updateUser(new User(id, "Василий Васильевич Васильев"));
            System.out.println("updatedUser: " + userService.getUserById(id));
            // Удалить
            userService.deleteUser(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
