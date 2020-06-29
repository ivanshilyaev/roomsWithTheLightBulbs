package by.ivanshilyaev.rooms.controller;

import by.ivanshilyaev.rooms.controller.action.Action;
import by.ivanshilyaev.rooms.controller.action.user.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebFilter(filterName = "ActionFromUriFilter", urlPatterns = {"*.html"})
public class ActionFromUriFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();

    private static Map<String, Action> actions = new ConcurrentHashMap<>();

    static {
        actions.put("/", new ForwardToIndexAction());
        actions.put("/index", new MainAction());
        actions.put("/createRoom", new CreateRoomAction());
        actions.put("/listOfRooms", new ListOfRoomsAction());
        actions.put("/room", new RoomAction());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
            String contextPath = httpRequest.getContextPath();
            String uri = httpRequest.getRequestURI();
            LOGGER.debug(String.format("Starting to process request for URI \"%s\"", uri));
            int beginAction = contextPath.length();
            int endAction = uri.lastIndexOf('.');
            String actionName;
            if (endAction >= 0) {
                actionName = uri.substring(beginAction, endAction);
            } else {
                actionName = uri.substring(beginAction);
            }
            try {
                Action action = actions.get(actionName);
                action.setName(actionName);
                httpRequest.setAttribute("action", action);
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (NullPointerException e) {
                LOGGER.error("Impossible to create action handler object", e);
                httpRequest.getServletContext().getRequestDispatcher("/error404.jsp").forward(servletRequest, servletResponse);
            }
        } else {
            LOGGER.error("Impossible to use HTTP filter");
            servletRequest.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
