package ru.innopolis.course3.Servlet;

import ru.innopolis.course3.BL.SubjectBL;
import ru.innopolis.course3.BL.UserBL;
import ru.innopolis.course3.Pojo.User;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/authorization")
public class AuthorizationServlet extends HttpServlet {
    private UserBL userBL;
    public AuthorizationServlet() {
        this.userBL = new UserBL();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("login") != null){
            String name = req.getParameter("user");
            String password = req.getParameter("password");
            Integer id = userBL.getIdUser(name,password);
            if (id != null){
                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("id", id);
                //User user = userBL.getByPK(id);
                //httpSession.setAttribute("role", user.getRole());
            }
            else {
                getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            }


            req.getServletContext()
                    .getRequestDispatcher("/subject")
                    .forward(req, resp);
        } else if (req.getParameter("registration") != null) {
            req.getServletContext()
                    .getRequestDispatcher("/registration")
                    .forward(req, resp);;
        }
        super.doPost(req, resp);
    }

  /*  @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/registration.jsp");
        dispatcher.forward(req, resp);
        super.doGet(req, resp);
    }*/
}
