package ru.otus.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.User;
import ru.otus.core.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import ru.otus.hibernate.sessionmanager.DatabaseSessionHibernate;


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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getPathInfo() == null){
            resp.setContentType("application/json;charset=UTF-8");
            ServletOutputStream out = resp.getOutputStream();
            out.print("{\"api\": \"try again\"}");
            return;
        }

        User user = null;
        try{
            user = gson.fromJson(req.getReader(), User.class);
        }
        catch (JsonParseException e){
            resp.setContentType("application/json;charset=UTF-8");
            resp.setStatus(200);
            ServletOutputStream out = resp.getOutputStream();
            out.print("{\"api\": \"cant parse user.\"}");
            return;
        }

        userDao.getSessionManager().beginSession();
        final long newId = userDao.insertUser(user);
        userDao.getSessionManager().close();

        resp.setContentType("application/json;charset=UTF-8");
        resp.setStatus(200);
        ServletOutputStream out = resp.getOutputStream();
        out.print(String.format("{\"new id\": \"%d\"}", newId));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getPathInfo() == null){
            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream out = response.getOutputStream();
            out.print("{\"api\": \"try again\"}");
            return;
        }


        if(doesItAboutGetUserById(request)){
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
            return;
        }
        else if(doesItAboutGetAllUsers(request)){
            userDao.getSessionManager().beginSession();
            DatabaseSessionHibernate session = (DatabaseSessionHibernate)
                    userDao.getSessionManager().getCurrentSession();
            List<User> users = session.getHibernateSession().createQuery(
                    "SELECT User FROM User").getResultList();
            System.out.println(users);
            userDao.getSessionManager().close();
            return;
        }

    }

    /**
     * extracts user id of request's path
     * @param request
     * @return
     */
    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1)? path[ID_PATH_PARAM_POSITION]: String.valueOf(- 1);
        try{
            return Long.parseLong(id);
        }
        catch (NumberFormatException e){
            return -1;
        }
    }

    /**
     * determines whether or not one user record was requested by user id
     * @param request
     * @return
     */
    private boolean doesItAboutGetUserById(final HttpServletRequest request){
        String[] path = request.getPathInfo().split("/");
        if(path.length > 1){
            try{
                long id = Long.parseLong(path[ID_PATH_PARAM_POSITION]);
            }
            catch (NumberFormatException e){
                return false;
            }
            return true;
        }

        return false;
    }

    /**
     * determines whether or not all users records was requested
     * @param request
     * @return
     */
    private boolean doesItAboutGetAllUsers(final HttpServletRequest request){
        if(request.getPathInfo().equals("/")){
            return true;
        }
        return false;
    }

}
