package service.impl;

import dao.TaskDao;
import model.Task;
import service.TaskService;

import java.util.List;

public class TaskServiceImpl implements TaskService {


    private static volatile TaskService instance = new TaskServiceImpl();
    TaskDao taskDao;

    private TaskServiceImpl(){
        taskDao=TaskDao.getInstance();
    }

    public static synchronized TaskService getInstance(){
        return instance;
    }


    @Override
    public List<Task> getTasksByUserId(int userId) {
        return taskDao.getAllTasksByUserId(userId);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskDao.getAllTasks();
    }

    @Override
    public void addTask(Task task) {
        taskDao.addTask(task);
    }

    @Override
    public void updateTaskStatus(int taskId, String newStatus) {
        taskDao.updateTaskStatus(taskId,newStatus);
    }
}
