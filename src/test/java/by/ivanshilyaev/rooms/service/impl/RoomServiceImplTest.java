package by.ivanshilyaev.rooms.service.impl;

import by.ivanshilyaev.rooms.controller.Runner;
import by.ivanshilyaev.rooms.service.exception.ServiceException;
import by.ivanshilyaev.rooms.service.interfaces.RoomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomServiceImplTest {
    private static final Logger LOGGER = LogManager.getLogger();

    private static RoomService roomService;

    @BeforeAll
    public static void init() {
        Runner.initConnectionPool();
        roomService = new RoomServiceImpl();
    }

    @Test
    public void testRoom() {
        try {
            assertEquals(roomService.read(1).get().getName(), "testRoom");
        } catch (ServiceException e) {
            LOGGER.error("Unable to read from database");
        }
    }
}
