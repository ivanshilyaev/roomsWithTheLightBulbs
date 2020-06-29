package by.ivanshilyaev.rooms.controller;

import by.ivanshilyaev.rooms.controller.action.Action;
import by.ivanshilyaev.rooms.controller.action.user.CreateRoomAction;
import by.ivanshilyaev.rooms.controller.action.user.ListOfRoomsAction;
import by.ivanshilyaev.rooms.controller.action.user.RoomAction;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebFilter(filterName = "ActionFromUriFilter", urlPatterns = {"*.html"})
public class ActionFromUriFilter implements Filter {
    private static Map<String, Action> actions = new ConcurrentHashMap<>();

    static {
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
                if (actionName.equals("/")) {
                    httpRequest.getServletContext().getRequestDispatcher("/index.jsp").forward(servletRequest, servletResponse);
                }
//                else {
//                    httpRequest.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error404.jsp").forward(servletRequest, servletResponse);
//                }
            }
        } else {
            //servletRequest.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
