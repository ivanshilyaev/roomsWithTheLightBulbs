package by.ivanshilyaev.rooms.dao.mysql;

import by.ivanshilyaev.rooms.bean.Lamp;
import by.ivanshilyaev.rooms.bean.Room;
import by.ivanshilyaev.rooms.dao.exception.DAOException;
import by.ivanshilyaev.rooms.dao.interfaces.RoomDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomDaoImpl implements RoomDao {
    protected Connection connection;

    private static final Integer BAD_CREATION_CODE = -1;

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_SELECT_ALL_ROOMS =
            "SELECT id, name, country, lamp FROM room;";

    private static final String SQL_SELECT_ROOM_BY_ID =
            "SELECT id, name, country, lamp FROM room WHERE id = ?;";

    private static final String SQL_INSERT =
            "INSERT INTO room (name, country, lamp) values (?, ?, ?);";

    private static final String SQL_UPDATE =
            "UPDATE room SET name = ?, country = ?, lamp = ? WHERE id = ?;";

    private static final String SQL_DELETE_ROOM_BY_ID =
            "DELETE FROM room WHERE id = ?;";

    @Override
    public Integer create(Room entity) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getCountry());
            if (entity.getLamp().getState().equals("On")) {
                statement.setInt(3, 1);
            } else {
                statement.setInt(3, 0);
            }
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                entity.setId(id);
                return id;
            } else {
                LOGGER.error("No autoincremented index after trying to add record into table room");
                return BAD_CREATION_CODE;
            }
        } catch (SQLException throwables) {
            LOGGER.error("DB connection error", throwables);
        }
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
            resultSet.close();
        } catch (SQLException throwables) {
            LOGGER.error("DB connection error", throwables);
        }
        return rooms;
    }

    @Override
    public Optional<Room> read(Integer id) throws DAOException {
        Room room = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROOM_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                room = new Room();
                fillRoom(resultSet, room);
            }
            resultSet.close();
        } catch (SQLException throwables) {
            LOGGER.error("DB connection error", throwables);
        }
        return Optional.ofNullable(room);
    }

    @Override
    public void update(Room entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getCountry());
            if (entity.getLamp().getState().equals("On")) {
                statement.setInt(3, 1);
            } else {
                statement.setInt(3, 0);
            }
            statement.setInt(4, entity.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            LOGGER.error("DB connection error", throwables);
        }
    }

    @Override
    public boolean delete(Integer id) throws DAOException {
        boolean deleted = false;
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ROOM_BY_ID)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            deleted = true;
        } catch (SQLException throwables) {
            LOGGER.error("DB connection error", throwables);
        }
        return deleted;
    }

    private void fillRoom(ResultSet resultSet, Room room) throws SQLException {
        room.setId(resultSet.getInt(1));
        room.setName(resultSet.getString(2));
        room.setCountry(resultSet.getString(3));
        int lampState = resultSet.getInt(4);
        Lamp lamp = new Lamp();
        if (lampState == 0) {
            lamp.setState("Off");
        } else {
            lamp.setState("On");
        }
        room.setLamp(lamp);
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
