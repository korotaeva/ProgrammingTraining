package ru.innopolis.course3.Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by korot on 03.01.2017.
 */
@WebServlet("/getImage.action")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String imageId = request.getParameter("imageId");
       /* byte[] imageData = getImageFromDatabase(imageId);
        response.setContentType("image/jpeg");
        response.getOutputStream().write(imageData);*/


        response.setContentType(imageId);
        response.getOutputStream();

    }
}