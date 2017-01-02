package ru.innopolis.course3.Servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.BL.UserBL;
import ru.innopolis.course3.Pojo.User;
import ru.innopolis.course3.dao.DataException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by korot on 23.12.2016.
 * Сервлет для пользователей
 */

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    public static Logger logger = LoggerFactory.getLogger(UserServlet.class);

    private void ErrorProcessing(String errorStr, Exception e){
        logger.error(errorStr, e);
        ctx.setAttribute("error", errorStr);
    }

    private ServletContext ctx;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ctx = getServletContext();
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user.jsp");
        List<User> subject = null;
        try {
            subject= new UserBL().getAll();
        }
        catch (DataException e){
            ErrorProcessing("Ошибка получении списка пользователей", e);
            ctx.getRequestDispatcher("/error.jsp").forward(req, resp);
        }

        req.setAttribute("Users", subject);
        dispatcher.forward(req, resp);
        super.doGet(req, resp);
    }
}
