package by.ivanshilyaev.rooms.service.impl;

import by.ivanshilyaev.rooms.dao.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServiceInvocationHandler implements InvocationHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    private RoomServiceImpl service;

    public ServiceInvocationHandler(RoomServiceImpl service) {
        this.service = service;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            Object result = method.invoke(service, args);
            service.transaction.commit();
            return result;
        } catch (DAOException e) {
            rollback();
            throw e;
        } catch (InvocationTargetException e) {
            rollback();
            throw e.getCause();
        }
    }

    private void rollback() {
        try {
            service.transaction.rollback();
        } catch (DAOException e) {
            LOGGER.warn("Impossible to rollback transaction", e);
        }
    }
}
