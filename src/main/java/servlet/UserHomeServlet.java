package servlet;

import model.Task;
import model.User;
import model.UserType;
import service.TaskService;
import service.impl.TaskServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/userHome")
public class UserHomeServlet extends HttpServlet {

    private TaskService taskService = TaskServiceImpl.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
            List<Task> allTasksByUserId = taskService.getTasksByUserId(currentUser.getId());
            req.setAttribute("tasks", allTasksByUserId);
            req.getRequestDispatcher("/WEB-INF/user.jsp").forward(req,resp);
        }
    }

