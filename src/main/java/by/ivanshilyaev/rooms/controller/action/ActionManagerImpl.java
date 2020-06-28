package by.ivanshilyaev.rooms.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionManagerImpl implements ActionManager {

    @Override
    public Action.Forward execute(Action action, HttpServletRequest request, HttpServletResponse response) {
        return action.exec(request, response);
    }
}
