package by.ivanshilyaev.rooms.controller.action.user;

import by.ivanshilyaev.rooms.controller.action.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RoomAction extends Action {
    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        request.setAttribute("roomId", roomId);
        return null;
    }
}
