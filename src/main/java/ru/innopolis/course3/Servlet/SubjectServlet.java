package ru.innopolis.course3.Servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.BL.PracticalAssignmentsBL;
import ru.innopolis.course3.Pojo.PracticalAssignments;
import ru.innopolis.course3.Pojo.Subject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import ru.innopolis.course3.BL.SubjectBL;

/**
 * Created by korot on 24.12.2016.
 */

@WebServlet("/subject/*")
public class SubjectServlet extends HttpServlet {
    public static Logger logger = LoggerFactory.getLogger(SubjectServlet.class);
    private SubjectBL subjectBL;
    public SubjectServlet() {
        this.subjectBL = new SubjectBL();
    }

    private Subject SubjectFromPK(String[] param){
        Subject subject = null;
        if(param.length > 2){
            try {
                int id = Integer.parseInt(param[2]);
                subject = subjectBL.getByPK(id);
            }catch (NumberFormatException e) {
                logger.error("NumberFormatException", e);
            }
        }
        return subject;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StringBuffer requestURL = req.getRequestURL();
        int startIndex = requestURL.indexOf("subject");
        String[] param = new String(requestURL).substring(startIndex).split("/");
        Subject subject = SubjectFromPK(param);
        String jsp = "/subject.jsp";

        if (param.length > 1) {
           switch (param[1]){
               case "create":
                   jsp = "/editsubject.jsp";
                   subjectBL.create(subject);
                   break;
               case "edit":
                   jsp = "/editsubject.jsp";
                   req.setAttribute("subject", subject);
                   List<PracticalAssignments> practicals= new PracticalAssignmentsBL().getAll();
                   req.setAttribute("Practicals", practicals);
                   break;
               case "delete":
                   subjectBL.delete(subject);
                   break;
           }
        }

        List<Subject> subjects= subjectBL.getAll();
        req.setAttribute("Subjects", subjects);

        RequestDispatcher dispatcher = req.getRequestDispatcher(jsp);
        dispatcher.forward(req, resp);
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                subjectBL.create(subject);
            } else {
                subject = new Subject(name, description, Integer.parseInt(id));
                subjectBL.update(subject);
            }

           /* resp.sendRedirect(req.getContextPath() + "/subject/");
            String jsp = "/editsubject.jsp";
            RequestDispatcher dispatcher = req.getRequestDispatcher(jsp);
            dispatcher.forward(req, resp);*/
        }
        resp.sendRedirect(req.getContextPath() + "/subject/");
        String jsp = "/editsubject.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(jsp);
        dispatcher.forward(req, resp);
        super.doPost(req, resp);
    }
}
