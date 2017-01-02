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
 * Сервлет для практичексих заданий
 */
@WebServlet("/practical/*")
public class PracticalServlet extends HttpServlet {
    public static Logger logger = LoggerFactory.getLogger(PracticalServlet.class);
    private PracticalAssignmentsBL practicalBL;
    private SubjectBL subjectBL;
    public PracticalServlet() {
        try {
            this.practicalBL = new PracticalAssignmentsBL();
            this.subjectBL = new SubjectBL();
        }
        catch (DataException e){
            ErrorProcessing("Ошибка при инициализации темы и списка практических заданий", e);
            ctx.getRequestDispatcher("/error.jsp");
        }

    }

    private PracticalAssignments PracticalFromPK(String[] param) throws DataException {
        PracticalAssignments practicalAssignments = null;
        if(param.length > 2 && param[2] != null && !param[2].equals("")){
            int id = Integer.parseInt(param[2]);
            practicalAssignments = practicalBL.getByPK(id);
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

    private ServletContext ctx;
    private void ErrorProcessing(String errorStr, Exception e){
        logger.error(errorStr, e);
        ctx.setAttribute("error", errorStr);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ctx = getServletContext();
        req.setCharacterEncoding("UTF-8");
        String[] param = getParam(req.getRequestURL());
        PracticalAssignments practical = null;
        try {
            practical = PracticalFromPK(param);
        }
        catch (DataException e){
            ErrorProcessing("Ошибка при чтении практического задания по ключу", e);
            ctx.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
        catch (NumberFormatException e) {
            ErrorProcessing("Некорректный формат ключа", e);
            ctx.getRequestDispatcher("/error.jsp").forward(req, resp);
        }

        String jsp = "/editsubject.jsp";
        Integer idSubject = getIdSubject(param);
        if(idSubject != null)
            req.setAttribute("subjectid", idSubject);

        if (param.length > 1) {
            switch (param[1]){
                case "create":
                    jsp = "/editpractical.jsp";
                    break;
                case "edit":
                    jsp = "/editpractical.jsp";
                    req.setAttribute("practical", practical);
                    break;
                case "delete":
                    try {
                        practicalBL.delete(practical);
                    }
                    catch (DataException e){
                        ErrorProcessing("Ошибка при удалении практического задания", e);
                        ctx.getRequestDispatcher("/error.jsp").forward(req, resp);
                    }
                    resp.sendRedirect(req.getContextPath() + "/subject/edit/" + idSubject);
                    break;
            }
        }
        try {
            List<PracticalAssignments> practicals= practicalBL.getAll();
            req.setAttribute("Practicals", practicals);
        }
        catch (DataException e){
            ErrorProcessing("Ошибка при получении списка практических заданий", e);
            ctx.getRequestDispatcher("/error.jsp").forward(req, resp);
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher(jsp);
        dispatcher.forward(req, resp);
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ctx = getServletContext();
        req.setCharacterEncoding("UTF-8");
        String[] param = getParam(req.getRequestURL());
        Integer idSubject = getIdSubject(param);
        if(idSubject != null)
            req.setAttribute("subjectid", idSubject);

        if(param.length > 1 && param[1].equals("save")) {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            String id = req.getParameter("id");
            Subject subject = null;
            try {
                subject = subjectBL.getByPK(idSubject);
            }
            catch (DataException e){
                ErrorProcessing("Не найдена тема с данным ключом", e);
                ctx.getRequestDispatcher("/error.jsp").forward(req, resp);
            }


            PracticalAssignments practical;


            if (id == null || id.isEmpty()) {
                practical = new PracticalAssignments(name, description, subject);
                try {
                    practicalBL.create(practical);
                }
                catch (DataException e){
                    ErrorProcessing("Ошибка при создании практического задания", e);
                    ctx.getRequestDispatcher("/error.jsp").forward(req, resp);
                }

            } else {
                practical = new PracticalAssignments(name, description, subject, Integer.parseInt(id));
                try {
                    practicalBL.update(practical);
                }
                catch (DataException e){
                    ErrorProcessing("Ошибка при обновлении практического задания", e);
                    ctx.getRequestDispatcher("/error.jsp").forward(req, resp);
                }

            }
        }
        resp.sendRedirect(req.getContextPath() + "/subject/");
        String jsp = "/editsubject.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(jsp);
        dispatcher.forward(req, resp);
        super.doPost(req, resp);
    }
}
