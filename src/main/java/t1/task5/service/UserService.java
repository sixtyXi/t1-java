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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserById(long id) {
        try {
            return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByUsername(String username) {
        try {
            return userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
