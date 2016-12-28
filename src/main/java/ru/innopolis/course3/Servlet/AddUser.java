package ru.innopolis.course3.Servlet;

import ru.innopolis.course3.BL.UserBL;
import ru.innopolis.course3.Pojo.Role;
import ru.innopolis.course3.Pojo.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by korot on 28.12.2016.
 */
@WebServlet("/adduser")
public class AddUser  extends HttpServlet {

    private UserBL userBL;
    public AddUser() {
        this.userBL = new UserBL();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        if (req.getParameter("save") != null){
            String name = req.getParameter("user");
            String password = req.getParameter("password");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            User user = new User(name,password,email,phone, Role.ROLE_USER);
            user = userBL.create(user);
            ctx.setAttribute("user", user);

            if (user != null){
               // req.setAttribute("user", user.getId());
                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("id", user.getId());
                //httpSession.setAttribute("role", user.getRole());
            }

            req.getServletContext()
                    .getRequestDispatcher("/subject")
                    .forward(req, resp);
        } else if (req.getParameter("cancel") != null){
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
        super.doPost(req, resp);
    }
}

