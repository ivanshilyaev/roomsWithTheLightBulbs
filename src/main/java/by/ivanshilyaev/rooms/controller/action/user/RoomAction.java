package by.ivanshilyaev.rooms.controller.action.user;

import by.ivanshilyaev.rooms.controller.action.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RoomAction extends Action {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PARAM_ROOM_ID = "roomId";

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) {
        int roomId;
        try {
            roomId = Integer.parseInt(request.getParameter(PARAM_ROOM_ID));
        } catch (Exception e) {
            roomId = (int) request.getAttribute(PARAM_ROOM_ID);
        }
        request.setAttribute("roomId", roomId);
        return null;
    }
}
