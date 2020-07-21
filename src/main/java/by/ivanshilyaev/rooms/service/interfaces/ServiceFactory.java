package by.ivanshilyaev.rooms.service.interfaces;

import by.ivanshilyaev.rooms.service.exception.ServiceException;

public interface ServiceFactory {
    RoomService createService(Class<RoomService> key) throws ServiceException;

    void close();
}
