package t1.task5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*

Скорректируйте ваш сервис:

- вместо имеющейся сущности user сделайте Entity

- Подключите стартер Spring data jpa

- вынесите все настройки в файл application.yml/properties

- опишите repository для работы с сущностью, можете использовать Query если посчитаете нужным

- исправьте методы UserService на работу с repository

- опишите класс CommandLineRunner и выполните возможные операции

- добавьте в проект миграцию для создания таблиц базы, инициализируйте тестовый набор данных в бд
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
