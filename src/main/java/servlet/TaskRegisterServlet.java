package servlet;

import model.Task;
import model.TaskStatus;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/taskRegister")
public class TaskRegisterServlet extends HttpServlet {

    private TaskService taskService = TaskServiceImpl.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            String name = req.getParameter("name");
            String description = req.getParameter("description");
            String deadline = req.getParameter("deadline");
            String status = req.getParameter("status");
            int user_id = Integer.parseInt(req.getParameter("user_id"));

            try {
                taskService.addTask(Task.builder()
                        .name(name)
                        .description(description)
                        .deadline(sdf.parse(deadline))
                        .status(TaskStatus.valueOf(status))
                        .userId(user_id)
                        .build());

                resp.sendRedirect("/managerHome");

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

