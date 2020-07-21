package by.ivanshilyaev.rooms.service.impl;

import by.ivanshilyaev.rooms.bean.Room;
import by.ivanshilyaev.rooms.dao.exception.DAOException;
import by.ivanshilyaev.rooms.dao.interfaces.RoomDao;
import by.ivanshilyaev.rooms.dao.interfaces.Transaction;
import by.ivanshilyaev.rooms.service.exception.ServiceException;
import by.ivanshilyaev.rooms.service.interfaces.RoomService;

import java.util.List;
import java.util.Optional;

public class RoomServiceImpl implements RoomService {
    protected Transaction transaction;

    @Override
    public Integer create(Room entity) throws ServiceException {
        try {
            RoomDao dao = transaction.createDao(RoomDao.class);
            return dao.create(entity);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Room> read() throws ServiceException {
        try {
            RoomDao dao = transaction.createDao(RoomDao.class);
            return dao.read();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Room> read(Integer id) throws ServiceException {
        try {
            RoomDao dao = transaction.createDao(RoomDao.class);
            return dao.read(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Room entity) throws ServiceException {
        try {
            RoomDao dao = transaction.createDao(RoomDao.class);
            dao.update(entity);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(Integer id) throws ServiceException {
        try {
            RoomDao dao = transaction.createDao(RoomDao.class);
            return dao.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
