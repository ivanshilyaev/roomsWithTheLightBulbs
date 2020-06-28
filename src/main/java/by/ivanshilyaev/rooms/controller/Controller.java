package by.ivanshilyaev.rooms.controller;

import by.ivanshilyaev.rooms.controller.action.Action;
import by.ivanshilyaev.rooms.controller.action.ActionManager;
import by.ivanshilyaev.rooms.controller.action.ActionManagerImpl;
import by.ivanshilyaev.rooms.dao.exception.DAOException;
import by.ivanshilyaev.rooms.dao.pool.ConnectionPool;
import by.ivanshilyaev.rooms.service.exception.ServiceException;
import by.ivanshilyaev.rooms.service.impl.RoomServiceImpl;
import by.ivanshilyaev.rooms.service.interfaces.RoomService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Controller", urlPatterns = "*.html")
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String DB_URL = "jdbc:mysql://localhost:3306/room_db";
    public static final String DB_LOGIN = "application";
    public static final String DB_PASSWORD = "application_password";
    public static final int DB_POOL_START_ACTIVE = 10;
    public static final int DB_POOL_MAX_ACTIVE = 1000;
    public static final int DB_POOL_MAX_WAIT = 0;

    public static RoomService service;

    @Override
    public void init() throws ServletException {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            ConnectionPool.getInstance().init(DB_URL,
                    DB_LOGIN, DB_PASSWORD, DB_POOL_START_ACTIVE,
                    DB_POOL_MAX_ACTIVE, DB_POOL_MAX_WAIT);
            LOGGER.info("Servlet has been started");
            service = new RoomServiceImpl();
        } catch (DAOException | SQLException e) {
            LOGGER.error("Impossible to init connection pool", e);
            destroy();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Action action = (Action) req.getAttribute("action");
            ActionManager actionManager = new ActionManagerImpl();
            Action.Forward forward = actionManager.execute(action, req, resp);
            String requestedUri = req.getRequestURI();
            if (forward != null && forward.isRedirect()) {
                String redirectedUri = req.getContextPath() + forward.getForward();
                LOGGER.debug(String.format("Request for URI \"%s\" is redirected to URI \"%s\"", requestedUri, redirectedUri));
                resp.sendRedirect(redirectedUri);
            } else {
                String page;
                if (forward != null) {
                    page = forward.getForward();
                } else {
                    page = action.getName() + ".jsp";
                }
                LOGGER.debug(String.format("Request for URI \"%s\" is forwarded to JSP \"%s\"", requestedUri, page));
                getServletContext().getRequestDispatcher(page).forward(req, resp);
            }
        } catch (ServiceException e) {
            // forward to 404
        }
    }
}
