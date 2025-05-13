package t1.task5.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import t1.task5.entity.User;
import t1.task5.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public long createUser(User user) {
        if (user == null || user.getUsername() == null) {
            throw new IllegalArgumentException("username не может быть null");
        }

        return userRepository.save(user).getId();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
    }

    public void updateUser(User user) {
        if (user == null || user.getUsername() == null) {
            throw new IllegalArgumentException("username не может быть null");
        }
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
