package by.ivanshilyaev.rooms.dao.interfaces;

import by.ivanshilyaev.rooms.bean.Room;
import by.ivanshilyaev.rooms.dao.exception.DAOException;

import java.util.List;

public interface RoomDao {
    Integer create(Room entity) throws DAOException;

    List<Room> read() throws DAOException;

    void update(Room entity);

    boolean delete(Integer id) throws DAOException;
}
