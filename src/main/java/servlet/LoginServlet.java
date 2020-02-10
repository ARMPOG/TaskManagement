package servlet;

import model.User;
import model.UserType;
import service.UserService;
import service.impl.UserServiceImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService = UserServiceImp.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = userService.login(email, password);
        if (user == null) {
            resp.sendRedirect("index.jsp");
        }else {
            HttpSession session = req.getSession();
            session.setAttribute("currentUser", user);
            if (user.getUserType()== UserType.MANAGER ){
                resp.sendRedirect("/managerHome");
            }else {
                resp.sendRedirect("/userHome");
            }
        }
    }
}
