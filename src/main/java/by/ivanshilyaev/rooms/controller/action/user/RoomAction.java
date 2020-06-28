package by.ivanshilyaev.rooms.controller.action.user;

import by.ivanshilyaev.rooms.controller.RoomEndpoint;
import by.ivanshilyaev.rooms.controller.action.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RoomAction extends Action {
    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        RoomEndpoint.roomId = roomId;
        return null;
    }
}
