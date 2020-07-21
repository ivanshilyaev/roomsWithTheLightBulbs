package by.ivanshilyaev.rooms.service.interfaces;

import by.ivanshilyaev.rooms.bean.Room;
import by.ivanshilyaev.rooms.dao.interfaces.Transaction;
import by.ivanshilyaev.rooms.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    Integer create(Room entity) throws ServiceException;

    List<Room> read() throws ServiceException;

    Optional<Room> read(Integer id) throws ServiceException;

    void update(Room entity) throws ServiceException;

    boolean delete(Integer id) throws ServiceException;

    void setTransaction(Transaction transaction);
}
