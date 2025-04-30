package t1.task4.service;

import org.springframework.stereotype.Service;
import t1.task4.entity.User;
import t1.task4.repository.UserDao;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public long createUser(User user) {
        try {
            return userDao.create(user);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid user data provided: " + e.getMessage(), e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error occurred while creating user: " + e.getMessage(), e);
        }
    }

    public User getUser(long id) {
        try {
            return userDao.get(id);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("User not found with ID: " + id, e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error occurred while retrieving user: " + e.getMessage(), e);
        }
    }

    public void updateUser(User user) {
        try {
            userDao.update(user);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Cannot update non-existent user: " + e.getMessage(), e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error occurred while updating user: " + e.getMessage(), e);
        }
    }

    public void deleteUser(long id) {
        try {
            userDao.delete(id);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Cannot delete non-existent user with ID: " + id, e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error occurred while deleting user: " + e.getMessage(), e);
        }
    }

    public List<User> getAllUsers() {
        try {
            return userDao.getAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error occurred while retrieving all users: " + e.getMessage(), e);
        }
    }
}
