package dao;


import db.DBConnectionProvider;
import model.Task;
import model.TaskStatus;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;


public class TaskDao {

    private static volatile TaskDao instance = new TaskDao();
    private Connection connection;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private UserDao userDao = new UserDao();

    private TaskDao() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public synchronized static TaskDao getInstance() {
        return instance;
    }

    public void addTask(Task task) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO task (name, description, deadline,status,user_id)" +
                    " VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            statement.setString(3, sdf.format(task.getDeadline()));
            statement.setString(4, task.getStatus().name());
            statement.setInt(5, task.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                task.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new LinkedList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM task");
            tasks = getTasksFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public List<Task> getAllTasksByUserId(int userId) {
        List<Task> tasks = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM task WHERE user_id=?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            tasks = getTasksFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    private List<Task> getTasksFromResultSet(ResultSet resultSet) throws SQLException {
        List<Task> tasks = new LinkedList<>();
        while (resultSet.next()) {
            try {
                tasks.add(Task.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .deadline(sdf.parse(resultSet.getString("deadline")))
                        .status(TaskStatus.valueOf(resultSet.getString("status")))
                        .userId(resultSet.getInt("user_id"))
                        .user(userDao.getUserById(resultSet.getInt("user_id")))
                        .build());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return tasks;
    }

    public void updateTaskStatus(int taskId, String newStatus){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE task set status=? WHERE id=?");
                    preparedStatement.setString(1,newStatus);
                    preparedStatement.setInt(2,taskId);
                    preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}






