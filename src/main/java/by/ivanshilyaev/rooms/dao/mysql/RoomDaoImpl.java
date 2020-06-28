package by.ivanshilyaev.rooms.dao.mysql;

import by.ivanshilyaev.rooms.bean.Room;
import by.ivanshilyaev.rooms.dao.exception.DAOException;
import by.ivanshilyaev.rooms.dao.interfaces.RoomDao;
import by.ivanshilyaev.rooms.dao.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDaoImpl implements RoomDao {
    private Connection connection;

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_SELECT_ALL_ROOMS =
            "SELECT id, name, country, lampState FROM room";

    public RoomDaoImpl() {
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer create(Room entity) throws DAOException {
        return null;
    }

    @Override
    public List<Room> read() throws DAOException {
        List<Room> rooms = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ROOMS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Room room = new Room();
                fillRoom(resultSet, room);
                rooms.add(room);
            }
        } catch (SQLException throwables) {
            LOGGER.error("DB connection error", throwables);
        }
        return rooms;
    }

    @Override
    public void update(Room entity) {

    }

    @Override
    public boolean delete(Integer id) throws DAOException {
        return false;
    }

    private void fillRoom(ResultSet resultSet, Room room) throws SQLException {
        room.setId(resultSet.getInt(1));
        room.setName(resultSet.getString(2));
        room.setCountry(resultSet.getString(3));
        room.setLampState(resultSet.getString(4));
    }
}
