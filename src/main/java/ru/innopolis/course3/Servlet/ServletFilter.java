package ru.innopolis.course3.Servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by korot on 29.12.2016.
 * Фильтр сервлетов для безопасноти
 * после авторизации
 */
@WebFilter(value="/*")
public class ServletFilter implements Filter {

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + "/authorization";
        String logoutURI = request.getContextPath() + "/logout";
        request.getRequestURI();
        boolean loggedIn = session != null && session.getAttribute("id") != null;
        boolean loginRequest = request.getRequestURI().equals(loginURI);
        boolean logoutRequest = request.getRequestURI().equals(logoutURI);

        if (loggedIn || loginRequest || logoutRequest) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(loginURI);
        }

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}
