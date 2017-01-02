package ru.innopolis.course3.Servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.BL.SubjectBL;
import ru.innopolis.course3.BL.UserBL;
import ru.innopolis.course3.Pojo.Role;
import ru.innopolis.course3.Pojo.User;
import ru.innopolis.course3.dao.DataException;

import javax.servlet.RequestDispatcher;
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
 * Сервлет для обработки авторизации
 */

@WebServlet("/authorization")
public class AuthorizationServlet extends HttpServlet {
    public static Logger logger = LoggerFactory.getLogger(AuthorizationServlet.class);

    private UserBL userBL;
    public AuthorizationServlet() {
        try {
            this.userBL = new UserBL();
        }
        catch (DataException e){
            ErrorProcessing("Ошибка при чтении пользователя", e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        super.doGet(req, resp);
    }
    private ServletContext ctx;

    private void ErrorProcessing(String errorStr, Exception e){
        logger.error(errorStr, e);
        ctx.setAttribute("error", errorStr);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ctx = getServletContext();
        if (req.getParameter("save") != null){
            String name = req.getParameter("user");
            String password = req.getParameter("password");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            User user = new User(name,password,email,phone, Role.ROLE_USER);
            try {
                user = userBL.create(user);
            }
            catch (DataException e){
                ErrorProcessing("Ошибка при создании пользователя", e);
                ctx.getRequestDispatcher("/error.jsp").forward(req, resp);
            }
            ctx.setAttribute("user", user);

            if (user != null){
                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("id", user.getId());
            }

            ctx.getRequestDispatcher("/subject").forward(req, resp);
        } else if (req.getParameter("cancel") != null){
            ctx.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
        else if (req.getParameter("login") != null){
            String name = req.getParameter("user");
            String password = req.getParameter("password");
            Integer id = null;
            try {
                id = userBL.getIdUser(name,password);
            }
            catch (DataException e){
                ErrorProcessing("Ошибка при получении польвателя по логину и паролю", e);
                ctx.getRequestDispatcher("/error.jsp").forward(req, resp);
            }

            if (id != null){
                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("id", id);
            }
            else {
                ctx.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
            ctx.getRequestDispatcher("/subject")
                    .forward(req, resp);
        } else if (req.getParameter("registration") != null) {
            ctx.getRequestDispatcher("/registration")
                    .forward(req, resp);;
        }
        super.doPost(req, resp);
    }

}
