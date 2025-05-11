package t1.task5.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import t1.task5.entity.User;
import t1.task5.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public long createUser(User user) {
        try {
            return userRepository.save(user).getId();
        } catch (IllegalArgumentException e) {
            log.error("Некорректный аргумент user для создания пользователя: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Ошибка создания пользователя: {}", e.getMessage(), e);
            throw new RuntimeException("Ошибка создания пользователя.", e);
        }
    }

    public User getUserById(long id) {
        try {
            return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException e) {
            log.error("Пользователь с ID {} не найден: {}", id, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Ошибка получения пользователя с ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Ошибка получения пользователя по ID.", e);
        }
    }

    public User getUserByUsername(String username) {
        try {
            return userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException e) {
            log.error("Пользователь с username '{}' не найден: {}", username, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Ошибка получения пользователя с username '{}': {}", username, e.getMessage(), e);
            throw new RuntimeException("Ошибка получения пользователя по username.", e);
        }
    }

    public void updateUser(User user) {
        try {
            userRepository.save(user);
        } catch (IllegalArgumentException e) {
            log.error("Некорректный аргумент user для обновления пользователя: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Ошибка обновления пользователя: {}", e.getMessage(), e);
            throw new RuntimeException("Ошибка обновления пользователя.", e);
        }
    }

    public void deleteUser(long id) {
        try {
            userRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            log.error("Некорректный аргумент ID для удаления пользователя: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Ошибка удаления пользователя по ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Ошибка удаления пользователя.", e);
        }
    }

    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            log.error("Ошибка получения списка пользователей: {}", e.getMessage(), e);
            throw new RuntimeException("Ошибка получения списка пользователей.", e);
        }
    }
}
