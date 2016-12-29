package ru.innopolis.course3.Servlet;

/**
 * Created by korot on 24.12.2016.
 *
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.course3.BL.PracticalAssignmentsBL;
import ru.innopolis.course3.BL.SubjectBL;
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

/**
 * Сервлет для практичексих заданий
 */
@WebServlet("/practical/*")
public class PracticalServlet extends HttpServlet {
    public static Logger logger = LoggerFactory.getLogger(PracticalServlet.class);
    private PracticalAssignmentsBL practicalBL;
    private SubjectBL subjectBL;
    public PracticalServlet() {
        this.practicalBL = new PracticalAssignmentsBL();
        this.subjectBL = new SubjectBL();
    }

    private PracticalAssignments PracticalFromPK(String[] param){
        PracticalAssignments practicalAssignments = null;
        if(param.length > 2 && param[2] != null ){
            try {
                int id = Integer.parseInt(param[2]);
                practicalAssignments = practicalBL.getByPK(id);
            }catch (NumberFormatException e) {
                logger.error("NumberFormatException", e);
            }
        }
        return practicalAssignments;
    }

    private Integer getIdSubject(String[] param){
        Integer idSubject = null;
        if (param.length > 2 && param[3] != null) {
            idSubject = Integer.parseInt(param[3]);
        }
        return idSubject;
    }

    private String[] getParam(StringBuffer requestURL){
        int startIndex = requestURL.indexOf("practical");
        String[] param = new String(requestURL).substring(startIndex).split("/");
        return param;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] param = getParam(req.getRequestURL());
        PracticalAssignments practical = PracticalFromPK(param);
        String jsp = "/editsubject.jsp";
        Integer idSubject = getIdSubject(param);
        if(idSubject != null)
            req.setAttribute("subjectid", idSubject);

        if (param.length > 1) {
            switch (param[1]){
                case "create":
                    jsp = "/editpractical.jsp";
                    practicalBL.create(practical);
                    break;
                case "edit":
                    jsp = "/editpractical.jsp";
                    req.setAttribute("practical", practical);
                    break;
                case "delete":
                    practicalBL.delete(practical);
                    resp.sendRedirect(req.getContextPath() + "/subject/edit/" + idSubject);
                    break;
            }
        }

        List<PracticalAssignments> practicals= practicalBL.getAll();
        req.setAttribute("Practicals", practicals);

        RequestDispatcher dispatcher = req.getRequestDispatcher(jsp);
        dispatcher.forward(req, resp);
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] param = getParam(req.getRequestURL());
        Integer idSubject = getIdSubject(param);
        if(idSubject != null)
            req.setAttribute("subjectid", idSubject);

        if(param.length > 1 && param[1].equals("save")) {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            String id = req.getParameter("id");
            Subject subject = subjectBL.getByPK(idSubject);

            PracticalAssignments practical;

            if (id == null || id.isEmpty()) {
                practical = new PracticalAssignments(name, description, subject);
                practicalBL.create(practical);
            } else {
                practical = new PracticalAssignments(name, description, subject, Integer.parseInt(id));
                practicalBL.update(practical);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/subject/");
        String jsp = "/editsubject.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(jsp);
        dispatcher.forward(req, resp);
        super.doPost(req, resp);
    }
}
