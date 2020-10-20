package hw12.web.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.SessionFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.server.AdminServer;
import ru.otus.server.UsersWebServer;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.core.model.*;

public class AdminServerDemo{
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HIBERNATE_CONFIG = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(
                HIBERNATE_CONFIG,
                User.class,
                PhoneDataSet.class,
                AddressDataSet.class
        );
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

        UserDao userDao = new UserDaoHibernate(sessionManager);
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        UsersWebServer adminServer = new AdminServer(WEB_SERVER_PORT, userDao, gson, templateProcessor);

        adminServer.start();
        adminServer.join();
    }
}
