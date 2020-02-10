package dao;


import db.DBConnectionProvider;
import model.Task;
import model.User;
import model.UserType;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class UserDao {

    private static volatile UserDao instance = new UserDao();
    private Connection connection ;

    public UserDao() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public synchronized static UserDao getInstance() {
        return instance;
    }

    public void addUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user (name,surname,email,password,type)" +
                    " VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getUserType().name());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                user.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new LinkedList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                users.add(User.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .surname(resultSet.getString("surname"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .userType(UserType.valueOf(resultSet.getString("type")))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUserByEmailAndPassword(String email, String password) {
        User result = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user" +
                    " WHERE email=? AND password=?");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            result=getUsersFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public User getUserById(int id) {
        User result = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE id=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            result=getUsersFromResultSet(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private User getUsersFromResultSet(ResultSet resultSet) throws SQLException {
       User users = new User();
        while (resultSet.next()) {
            users = (User.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .surname(resultSet.getString("surname"))
                    .email(resultSet.getString("email"))
                    .password(resultSet.getString("password"))
                    .userType(UserType.valueOf(resultSet.getString("type")))
                    .build());
        }

        return users;
    }
}




