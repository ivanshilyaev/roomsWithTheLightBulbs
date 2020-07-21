package by.ivanshilyaev.rooms.dao.interfaces;

import by.ivanshilyaev.rooms.dao.exception.DAOException;

public interface Transaction {
    RoomDao createDao(Class<RoomDao> key) throws DAOException;

    void commit() throws DAOException;

    void rollback() throws DAOException;
}
