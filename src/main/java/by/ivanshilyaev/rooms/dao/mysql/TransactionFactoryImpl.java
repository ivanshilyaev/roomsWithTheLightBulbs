package by.ivanshilyaev.rooms.dao.mysql;

import by.ivanshilyaev.rooms.dao.exception.DAOException;
import by.ivanshilyaev.rooms.dao.interfaces.Transaction;
import by.ivanshilyaev.rooms.dao.interfaces.TransactionFactory;
import by.ivanshilyaev.rooms.dao.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionFactoryImpl implements TransactionFactory {
    private static final Logger LOGGER = LogManager.getLogger();

    private Connection connection;

    public TransactionFactoryImpl() throws DAOException {
        connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException throwables) {
            LOGGER.error("Impossible to turn off autocommiting for database connection", throwables);
            throw new DAOException(throwables);
        }
    }

    @Override
    public Transaction createTransaction() {
        return new TransactionImpl(connection);
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
        }
    }
}
