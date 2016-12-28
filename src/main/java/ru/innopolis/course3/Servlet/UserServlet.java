package ru.innopolis.course3.Servlet;

import ru.innopolis.course3.BL.UserBL;
import ru.innopolis.course3.Pojo.User;

import javax.servlet.RequestDispatcher;
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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user.jsp");
        List<User> subject= new UserBL().getAll();
        req.setAttribute("Users", subject);
        dispatcher.forward(req, resp);
        super.doGet(req, resp);
    }
}
