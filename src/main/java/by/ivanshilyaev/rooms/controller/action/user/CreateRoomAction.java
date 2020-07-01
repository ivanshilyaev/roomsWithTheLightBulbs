package by.ivanshilyaev.rooms.controller.action.user;

import by.ivanshilyaev.rooms.bean.Room;
import by.ivanshilyaev.rooms.controller.Controller;
import by.ivanshilyaev.rooms.controller.action.Action;
import by.ivanshilyaev.rooms.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class CreateRoomAction extends Action {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PARAM_NAME = "name";
    private static final String PARAM_COUNTRY = "country";

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String name = request.getParameter(PARAM_NAME);
        String country = request.getParameter(PARAM_COUNTRY);
        String[] countryCodes = Locale.getISOCountries();
        Map<String, String> mapCountries = new TreeMap<>();
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            String code = locale.getCountry();
            String countryName = locale.getDisplayCountry();
            mapCountries.put(code, countryName);
        }
        if (name == null || country == null) {
            request.setAttribute("mapCountries", mapCountries);
            return null;
        }
        if (name.isEmpty() || country.isEmpty()) {
            request.setAttribute("mapCountries", mapCountries);
            request.setAttribute("createRoomMessage", "Invalid data! Try again");
            return null;
        }
        Room room = new Room(name, country);
        if (Controller.service.create(room) != -1) {
            Forward forward = new Forward("/room.html");
            forward.getAttributes().put("roomId", room.getId());
            LOGGER.info(String.format("New room with id %d has been successfully created", room.getId()));
            return forward;
        }
        request.setAttribute("createRoomMessage", "Unable to create room! Try again");
        return null;
    }
}
