package by.ivanshilyaev.rooms.dao.pool;

import by.ivanshilyaev.rooms.dao.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();

    private String url;
    private String username;
    private String password;
    private int maxActive;
    private int maxWait;
    private Lock locker = new ReentrantLock();

    private BlockingDeque<PooledConnection> freeConnections = new LinkedBlockingDeque<>();
    private Set<PooledConnection> usedConnections = new ConcurrentSkipListSet<>();

    private static final ConnectionPool INSTANCE = new ConnectionPool();

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    private ConnectionPool() {
    }

    public Connection getConnection() throws DAOException {
        locker.lock();
        PooledConnection connection = null;
        while (connection == null) {
            try {
                if (!freeConnections.isEmpty()) {
                    connection = freeConnections.take();
                    if (!connection.isValid(maxWait)) {
                        try {
                            connection.getConnection().close();
                        } catch (SQLException throwables) {
                        }
                        connection = null;
                    }
                } else if (usedConnections.size() < maxActive) {
                    connection = createConnection();
                } else {
                    LOGGER.error("Number of database connections is exceeded");
                    locker.unlock();
                    throw new DAOException();
                }
            } catch (InterruptedException | SQLException e) {
                LOGGER.error("Impossible to connect to a database", e);
                locker.unlock();
                throw new DAOException(e);
            }
        }
        usedConnections.add(connection);
        locker.unlock();
        return connection;
    }

    private PooledConnection createConnection() throws SQLException {
        return new PooledConnection(DriverManager.getConnection(url, username, password));
    }

    void freeConnection(PooledConnection connection) {
        locker.lock();
        try {
            if (connection.isValid(maxWait)) {
                connection.clearWarnings();
                connection.setAutoCommit(true);
                usedConnections.remove(connection);
                freeConnections.add(connection);
                LOGGER.debug(String.format("Connection was returned into pool. Current pool size: %d used connections; %d free connection", usedConnections.size(), freeConnections.size()));
            }
        } catch (SQLException e) {
            LOGGER.warn("Impossible to return connection into pool", e);
            try {
                connection.getConnection().close();
            } catch (SQLException ex) {
            }
        }
        locker.unlock();
    }

    public void init(String url, String username, String password, int startSize, int maxActive, int maxWait) throws DAOException {
        locker.lock();
        try {
            destroy();
            this.url = url;
            this.username = username;
            this.password = password;
            this.maxActive = maxActive;
            this.maxWait = maxWait;
            for (int counter = 0; counter < startSize; counter++) {
                freeConnections.put(createConnection());
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.fatal("Impossible to initialize connection pool", e);
            locker.unlock();
            throw new DAOException(e);
        }
        locker.unlock();
    }

    public void destroy() {
        locker.lock();
        usedConnections.addAll(freeConnections);
        freeConnections.clear();
        for (PooledConnection connection : usedConnections) {
            try {
                connection.getConnection().close();
            } catch (SQLException e) {
            }
        }
        usedConnections.clear();
        locker.unlock();
    }
}
