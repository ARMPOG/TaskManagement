package service;

import model.Task;

import java.util.List;

public interface TaskService {

    List<Task> getTasksByUserId(int userId);

    List<Task> getAllTasks();

    void addTask(Task task);

    void updateTaskStatus(int taskId, String newStatus);
}
