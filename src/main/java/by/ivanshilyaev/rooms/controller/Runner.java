package by.ivanshilyaev.rooms.controller;

import by.ivanshilyaev.rooms.bean.Lamp;
import by.ivanshilyaev.rooms.bean.Room;
import by.ivanshilyaev.rooms.dao.exception.DAOException;
import by.ivanshilyaev.rooms.dao.interfaces.RoomDao;
import by.ivanshilyaev.rooms.dao.mysql.RoomDaoImpl;
import by.ivanshilyaev.rooms.dao.pool.ConnectionPool;
import by.ivanshilyaev.rooms.service.exception.ServiceException;
import by.ivanshilyaev.rooms.service.impl.RoomServiceImpl;
import by.ivanshilyaev.rooms.service.interfaces.RoomService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Runner {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String DB_URL = "jdbc:mysql://localhost:3306/room_db";
    public static final String DB_LOGIN = "application";
    public static final String DB_PASSWORD = "application_password";
    public static final int DB_POOL_START_ACTIVE = 10;
    public static final int DB_POOL_MAX_ACTIVE = 1000;
    public static final int DB_POOL_MAX_WAIT = 0;

    public static void initConnectionPool() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            ConnectionPool.getInstance().init(DB_URL,
                    DB_LOGIN, DB_PASSWORD, DB_POOL_START_ACTIVE,
                    DB_POOL_MAX_ACTIVE, DB_POOL_MAX_WAIT);
        } catch (DAOException | SQLException e) {
            LOGGER.error(e);
        }
    }

    public static void main(String[] args) {
        initConnectionPool();
        try {
            RoomService service = new RoomServiceImpl();
            System.out.println(service.read());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
