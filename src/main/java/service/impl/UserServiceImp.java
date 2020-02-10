package service.impl;

import dao.UserDao;
import model.User;
import service.UserService;

import java.util.List;

public class UserServiceImp implements UserService {

    private static volatile UserService instance = new UserServiceImp();
    UserDao userDao;

    private UserServiceImp(){
        userDao=UserDao.getInstance();
    }

    public static synchronized UserService getInstance(){
        return instance;
    }

    @Override
    public User login(String email, String password) {
        return userDao.getUserByEmailAndPassword(email,password);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }
}
