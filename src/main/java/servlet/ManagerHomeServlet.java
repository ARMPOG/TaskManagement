package servlet;

import model.Task;
import model.User;
import model.UserType;
import service.TaskService;
import service.UserService;
import service.impl.TaskServiceImpl;
import service.impl.UserServiceImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/managerHome")
public class ManagerHomeServlet extends HttpServlet {

    private TaskService taskService = TaskServiceImpl.getInstance();
    private UserService userService = UserServiceImp.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            List<Task> allTasks = taskService.getAllTasks();
            List<User> allUsers = userService.getAllUsers();
            req.setAttribute("tasks", allTasks);
            req.setAttribute("users", allUsers);
            req.getRequestDispatcher("/WEB-INF/manager.jsp").forward(req,resp);
        }
    }

