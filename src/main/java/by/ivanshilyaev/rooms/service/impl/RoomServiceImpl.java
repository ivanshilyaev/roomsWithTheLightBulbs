package by.ivanshilyaev.rooms.service.impl;

import by.ivanshilyaev.rooms.bean.Room;
import by.ivanshilyaev.rooms.dao.exception.DAOException;
import by.ivanshilyaev.rooms.dao.interfaces.RoomDao;
import by.ivanshilyaev.rooms.dao.mysql.RoomDaoImpl;
import by.ivanshilyaev.rooms.service.exception.ServiceException;
import by.ivanshilyaev.rooms.service.interfaces.RoomService;

import java.util.List;
import java.util.Optional;

public class RoomServiceImpl implements RoomService {
    private RoomDao dao;

    public RoomServiceImpl() {
        dao = new RoomDaoImpl();
    }

    @Override
    public Integer create(Room entity) throws ServiceException {
        try {
            return dao.create(entity);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Room> read() throws ServiceException {
        try {
            return dao.read();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Room> read(Integer id) throws ServiceException {
        try {
            return dao.read(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Room entity) {
        dao.update(entity);
    }

    @Override
    public boolean delete(Integer id) throws ServiceException {
        try {
            return dao.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void close() {
        dao.closeConnection();
    }
}
