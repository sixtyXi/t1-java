package t1.task4;

import org.springframework.stereotype.Service;

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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(long id) {
        try {
            return userDao.get(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(User user) {
        try {
            userDao.update(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(long id) {
        try {
            userDao.delete(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try {
            return userDao.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
