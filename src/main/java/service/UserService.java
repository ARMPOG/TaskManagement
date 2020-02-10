package service;

import model.User;

import java.util.List;

public interface UserService {

    User login(String email, String password);
    List<User> getAllUsers();
    void addUser(User user);

}
