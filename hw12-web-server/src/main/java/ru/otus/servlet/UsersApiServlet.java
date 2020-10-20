package ru.otus.servlet;

import com.google.gson.Gson;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.User;
import ru.otus.core.dao.UserDao;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;


public class UsersApiServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(UsersApiServlet.class);
    private static final int ID_PATH_PARAM_POSITION = 1;

    private final UserDao userDao;
    private final Gson gson;

    public UsersApiServlet(UserDao userDao, Gson gson) {
        this.userDao = userDao;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.debug("api was used");
        if(request.getPathInfo() == null){
            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream out = response.getOutputStream();
            out.print("{\"api\": \"try again\"}");
            return;
        }

        final long id = extractIdFromRequest(request);
        userDao.getSessionManager().beginSession();
        final User user = userDao.findById(id).orElse(null);
        userDao.getSessionManager().close();

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        if(user == null){
            out.print("{}");
            logger.debug("There is no such user with id {}", id);
            return;

        }

        final String json = gson.toJson(user);
        logger.debug("User was requested: " + json);
        out.print(json);

    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1)? path[ID_PATH_PARAM_POSITION]: String.valueOf(- 1);
        return Long.parseLong(id);
    }

}
