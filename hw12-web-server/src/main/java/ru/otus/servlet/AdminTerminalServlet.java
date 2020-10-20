package ru.otus.servlet;

import ru.otus.core.dao.UserDao;
import ru.otus.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


public class AdminTerminalServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "adminTerminal.html";

    private final UserDao userDao;
    private final TemplateProcessor templateProcessor;

    public AdminTerminalServlet(TemplateProcessor templateProcessor, UserDao userDao) {
        this.templateProcessor = templateProcessor;
        this.userDao = userDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, Collections.emptyMap()));
    }

}
