package ru.innopolis.course3.Servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.BL.PracticalAssignmentsBL;
import ru.innopolis.course3.Pojo.PracticalAssignments;
import ru.innopolis.course3.Pojo.Subject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import ru.innopolis.course3.BL.SubjectBL;
import ru.innopolis.course3.dao.DataException;

/**
 * Created by korot on 24.12.2016.
 * Сервлет для тем
 */

@WebServlet("/subject/*")
public class SubjectServlet extends HttpServlet {
    public static Logger logger = LoggerFactory.getLogger(SubjectServlet.class);
    private SubjectBL subjectBL;
    public SubjectServlet() {
        try {
            this.subjectBL = new SubjectBL();
        }
        catch (DataException e){
            ErrorProcessing("Ошибка при инициализации темы", e);
            ctx.getRequestDispatcher("/error.jsp");
        }

    }

    private Subject SubjectFromPK(String[] param)throws DataException{
        Subject subject = null;
        if(param.length > 2){
            int id = Integer.parseInt(param[2]);
            subject = subjectBL.getByPK(id);
        }
        return subject;
    }

    private void ErrorProcessing(String errorStr, Exception e){
        logger.error(errorStr, e);
        ctx.setAttribute("error", errorStr);
    }

    private ServletContext ctx;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ctx = getServletContext();
        req.setCharacterEncoding("UTF-8");
        StringBuffer requestURL = req.getRequestURL();
        int startIndex = requestURL.indexOf("subject");
        String[] param = new String(requestURL).substring(startIndex).split("/");
        Subject subject = null;
        try {
            subject = SubjectFromPK(param);
        }
        catch (DataException e){
            ErrorProcessing("Ошибка при чтении по ключу", e);
            ctx.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
        catch (NumberFormatException e) {
            ErrorProcessing("Не корректный формат ключа", e);
            ctx.getRequestDispatcher("/error.jsp").forward(req, resp);
        }

        String jsp = "/subject.jsp";

        if (param.length > 1) {
           switch (param[1]){
               case "create":
                   jsp = "/editsubject.jsp";

                   break;
               case "edit":
                   jsp = "/editsubject.jsp";
                   req.setAttribute("subject", subject);
                   try {
                       if(subject != null){
                           List<PracticalAssignments> practicals= new PracticalAssignmentsBL().getAllBySubject(subject.getId().toString());
                           req.setAttribute("Practicals", practicals);
                       }
                   }
                   catch (DataException e){
                       ErrorProcessing("Ошибка при получении списка практичексих заданий", e);
                       ctx.getRequestDispatcher("/error.jsp").forward(req, resp);
                   }
                   break;
               case "delete":
                   try {
                       subjectBL.delete(subject);
                   }
                   catch (DataException e){
                       ErrorProcessing("Ошибка при удалении темы", e);
                       ctx.getRequestDispatcher("/error.jsp").forward(req, resp);
                   }
                   break;
           }
        }
        try {
            List<Subject> subjects= subjectBL.getAll();
            req.setAttribute("Subjects", subjects);
        }
        catch (DataException e){
            ErrorProcessing("Ошибка при получении списка тем", e);
            ctx.getRequestDispatcher("/error.jsp").forward(req, resp);
        }


        RequestDispatcher dispatcher = req.getRequestDispatcher(jsp);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ctx = getServletContext();
        req.setCharacterEncoding("UTF-8");
        StringBuffer requestURL = req.getRequestURL();
        int startIndex = requestURL.indexOf("subject");
        String[] param = new String(requestURL).substring(startIndex).split("/");
        if(param.length > 1 && param[1].equals("save")){
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            String id = req.getParameter("id");
            Subject subject;

            if (id == null || id.isEmpty()) {
                subject = new Subject(name, description);
                try {
                    subjectBL.create(subject);
                }
                catch (DataException e){
                    ErrorProcessing("Ошибка при создании темы", e);
                    ctx.getRequestDispatcher("/error.jsp").forward(req, resp);
                }

            } else {
                subject = new Subject(name, description, Integer.parseInt(id));
                try {
                    subjectBL.update(subject);
                }
                catch (DataException e){
                    ErrorProcessing("Ошибка при изменении темы", e);
                    ctx.getRequestDispatcher("/error.jsp").forward(req, resp);
                }
            }

        }
        resp.sendRedirect(req.getContextPath() + "/subject/");
        String jsp = "/editsubject.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(jsp);
        dispatcher.forward(req, resp);
    }
}
