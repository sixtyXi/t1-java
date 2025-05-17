package t1.task6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*

Доработайте сервис, пусть теперь он хранит продукты клиентов.

Хранение продуктов необходимо организовать аналогично ранее добавленным пользователям.

У каждого пользователя может быть несколько продуктов, но у каждого продукта один пользователь.

Продукт клиента: id, номер счета, баланс, тип продукта (счет, карта), userId.

Сервис должен хранить продукты.

Сервис должен давать возможность: запросить все продукты по userId, запросить продукт по productId (использовать spring-boot-starter-web)

Все изменения в БД выполняются через инструмент миграции, добавленный ранее
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
