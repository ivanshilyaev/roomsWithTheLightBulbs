package by.ivanshilyaev.rooms.controller.action.user;

import by.ivanshilyaev.rooms.controller.Controller;
import by.ivanshilyaev.rooms.controller.action.Action;
import by.ivanshilyaev.rooms.service.exception.ServiceException;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.Scanner;

public class RoomAction extends Action {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PARAM_ROOM_ID = "roomId";

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try (Scanner s = new java.util.Scanner(new URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A")) {
            String ip = s.next();
            File database = new File("/Users/ivansilaev/Downloads/gitRepos/roomsWithTheLightBulbs/src/main/resources/GeoLite2-Country.mmdb");
            DatabaseReader reader = new DatabaseReader.Builder(database).build();
            InetAddress inetAddress = InetAddress.getByName(ip);
            CountryResponse countryResponse = reader.country(inetAddress);
            String country = countryResponse.getCountry().getIsoCode();
            int roomId;
            try {
                roomId = Integer.parseInt(request.getParameter(PARAM_ROOM_ID));
            } catch (Exception e) {
                roomId = (int) request.getAttribute(PARAM_ROOM_ID);
            }

            if (!Controller.service.read(roomId).get().getCountry().equals(country)) {
                request.getServletContext().getRequestDispatcher("/error403.jsp").forward(request, response);
            }
            request.setAttribute("roomId", roomId);
        } catch (IOException | GeoIp2Exception | ServletException e) {
            LOGGER.error("Unable to get user IP address");
        }
        return null;
    }
}
