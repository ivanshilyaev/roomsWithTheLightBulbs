package by.ivanshilyaev.rooms.dao.interfaces;

import by.ivanshilyaev.rooms.bean.Room;
import by.ivanshilyaev.rooms.dao.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface RoomDao {
    Integer create(Room entity) throws DAOException;

    List<Room> read() throws DAOException;

    Optional<Room> read(Integer id) throws DAOException;

    void update(Room entity);

    boolean delete(Integer id) throws DAOException;

    void closeConnection();
}
