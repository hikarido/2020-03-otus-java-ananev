package ru.otus.server;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.core.dao.UserDao;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.services.TemplateProcessor;
import ru.otus.servlet.AdminTerminalServlet;
import ru.otus.servlet.UsersApiServlet;

public class AdminServer implements UsersWebServer{

    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";
    private static final String USER_API_CONTEXT_PATH = "/api/user/*";
    private static final String  ADMIN_TERMINAL_CONTEXT_PATH = "/adminTerminal";

    private final UserDao userDao;
    private final Gson gson;
    protected final TemplateProcessor templateProcessor;
    private final Server server;

    public AdminServer(int port, UserDao userDao, Gson gson, TemplateProcessor templateProcessor) {
        this.userDao = userDao;
        this.gson = gson;
        this.templateProcessor = templateProcessor;
        ResourceHandler resourceHandler = createResourceHandler();

        ServletContextHandler context = new ServletContextHandler();
        context.addServlet(new ServletHolder(
                new UsersApiServlet(userDao, gson)),
                USER_API_CONTEXT_PATH);
        context.addServlet(new ServletHolder(
                new AdminTerminalServlet(templateProcessor, userDao)),
                ADMIN_TERMINAL_CONTEXT_PATH);

        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(context);
        server = new Server(port);
        server.setHandler(handlers);

    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    @Override
    public void start() throws Exception {
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }
}
