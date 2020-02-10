package servlet;

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


@WebServlet("/changeTaskStatus")
public class ChangeTaskStatusServlet extends HttpServlet {

    private TaskService taskService = TaskServiceImpl.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser.getUserType() == UserType.MANAGER) {
            resp.sendRedirect("/managerHome");
        } else {
                resp.sendRedirect("/userHome");
        }

        int taskId = Integer.parseInt(req.getParameter("taskId"));
        String status = req.getParameter("status");
        taskService.updateTaskStatus(taskId, status);
    }
}






