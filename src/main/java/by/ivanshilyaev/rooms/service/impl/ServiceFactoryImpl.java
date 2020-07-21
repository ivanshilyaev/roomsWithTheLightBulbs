package by.ivanshilyaev.rooms.service.impl;

import by.ivanshilyaev.rooms.dao.exception.DAOException;
import by.ivanshilyaev.rooms.dao.interfaces.Transaction;
import by.ivanshilyaev.rooms.dao.interfaces.TransactionFactory;
import by.ivanshilyaev.rooms.dao.mysql.TransactionFactoryImpl;
import by.ivanshilyaev.rooms.service.exception.ServiceException;
import by.ivanshilyaev.rooms.service.interfaces.RoomService;
import by.ivanshilyaev.rooms.service.interfaces.ServiceFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceFactoryImpl implements ServiceFactory {
    private static final Map<Class<RoomService>, RoomServiceImpl>
            repository = new ConcurrentHashMap<>();

    static {
        repository.put(RoomService.class, new RoomServiceImpl());
    }

    private TransactionFactory factory;

    public ServiceFactoryImpl() throws ServiceException {
        try {
            factory = new TransactionFactoryImpl();
        } catch (DAOException e) {
            throw new ServiceException("Couldn't create service factory", e);
        }
    }

    @Override
    public RoomService createService(Class<RoomService> key) throws ServiceException {
        RoomServiceImpl service = repository.get(key);
        if (service != null) {
            Transaction transaction = factory.createTransaction();
            service.setTransaction(transaction);
            InvocationHandler handler = new ServiceInvocationHandler(service);
            return (RoomService) Proxy.newProxyInstance(service.getClass().getClassLoader(),
                    new Class[]{key}, handler);
        }
        return null;
    }

    @Override
    public void close() {
        factory.close();
    }
}
