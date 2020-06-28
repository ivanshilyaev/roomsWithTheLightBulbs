package by.ivanshilyaev.rooms.controller;

import by.ivanshilyaev.rooms.controller.action.Action;
import by.ivanshilyaev.rooms.controller.action.ActionManager;
import by.ivanshilyaev.rooms.controller.action.ActionManagerImpl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Controller", urlPatterns = "*.html")
public class Controller extends HttpServlet {
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
        Action action = (Action) req.getAttribute("action");
        ActionManager actionManager = new ActionManagerImpl();
        Action.Forward forward = actionManager.execute(action, req, resp);
        if (forward != null && forward.isRedirect()) {
            String redirectedUri = req.getContextPath() + forward.getForward();
            resp.sendRedirect(redirectedUri);
        } else {
            String page;
            if (forward != null) {
                page = forward.getForward();
            } else {
                page = action.getName() + ".jsp";
            }
            getServletContext().getRequestDispatcher(page).forward(req, resp);
        }
    }
}
