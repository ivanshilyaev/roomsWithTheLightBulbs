package by.ivanshilyaev.rooms.controller.action.user;

import by.ivanshilyaev.rooms.bean.Lamp;
import by.ivanshilyaev.rooms.bean.Room;
import by.ivanshilyaev.rooms.controller.action.Action;
import by.ivanshilyaev.rooms.service.exception.ServiceException;
import by.ivanshilyaev.rooms.service.interfaces.RoomService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
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
            mapCountries.put(countryName, code);
        }
        try (Scanner s = new java.util.Scanner(new URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A")) {
            String ip = s.next();
            File database = new File("/Users/ivansilaev/Downloads/gitRepos/roomsWithTheLightBulbs/src/main/resources/GeoLite2-Country.mmdb");
            DatabaseReader reader = new DatabaseReader.Builder(database).build();
            InetAddress inetAddress = InetAddress.getByName(ip);
            CountryResponse countryResponse = reader.country(inetAddress);
            String userCountry = countryResponse.getCountry().getName();
            request.setAttribute("userCountry", userCountry);
        } catch (IOException | GeoIp2Exception e) {
            e.printStackTrace();
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
        Room room = new Room();
        room.setName(name);
        room.setCountry(country);
        Lamp lamp = new Lamp();
        lamp.setState("On");
        room.setLamp(lamp);
        RoomService service = factory.createService(RoomService.class);
        if (service.create(room) != -1) {
            Forward forward = new Forward("/listOfRooms.html");
            LOGGER.info(String.format("New room with id %d has been successfully created", room.getId()));
            return forward;
        }
        request.setAttribute("createRoomMessage", "Unable to create room! Try again");
        return null;
    }
}
