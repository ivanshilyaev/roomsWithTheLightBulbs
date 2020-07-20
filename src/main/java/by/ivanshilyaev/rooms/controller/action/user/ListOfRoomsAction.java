package by.ivanshilyaev.rooms.controller.action.user;

import by.ivanshilyaev.rooms.bean.Room;
import by.ivanshilyaev.rooms.controller.action.Action;
import by.ivanshilyaev.rooms.service.exception.ServiceException;
import by.ivanshilyaev.rooms.service.interfaces.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListOfRoomsAction extends Action {
    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response, RoomService service) throws ServiceException {
        List<Room> rooms = service.read();
        request.getSession().setAttribute("rooms", rooms);
        return null;
    }
}
