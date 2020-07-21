package by.ivanshilyaev.rooms.dao.mysql;

import by.ivanshilyaev.rooms.dao.exception.DAOException;
import by.ivanshilyaev.rooms.dao.interfaces.RoomDao;
import by.ivanshilyaev.rooms.dao.interfaces.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionImpl implements Transaction {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final Map<Class<RoomDao>, RoomDaoImpl>
            repository = new ConcurrentHashMap<>();

    static {
        repository.put(RoomDao.class, new RoomDaoImpl());
    }

    private Connection connection;

    public TransactionImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public RoomDao createDao(Class<RoomDao> key) throws DAOException {
        RoomDao dao = repository.get(key);
        if (dao != null) {
            dao.setConnection(connection);
            return dao;
        }
        return null;
    }

    @Override
    public void commit() throws DAOException {
        try {
            connection.commit();
        } catch (SQLException throwables) {
            LOGGER.error("Impossible to commit transaction", throwables);
            throw new DAOException(throwables);
        }
    }

    @Override
    public void rollback() throws DAOException {
        try {
            connection.rollback();
        } catch (SQLException throwables) {
            LOGGER.error("Impossible to rollback transaction", throwables);
            throw new DAOException(throwables);
        }
    }
}
