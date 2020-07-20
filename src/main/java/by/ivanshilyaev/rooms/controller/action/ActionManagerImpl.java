package by.ivanshilyaev.rooms.controller.action;

import by.ivanshilyaev.rooms.service.exception.ServiceException;
import by.ivanshilyaev.rooms.service.interfaces.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionManagerImpl implements ActionManager {

    @Override
    public Action.Forward execute(Action action, HttpServletRequest request, HttpServletResponse response, RoomService service) throws ServiceException {
        return action.exec(request, response, service);
    }
}
