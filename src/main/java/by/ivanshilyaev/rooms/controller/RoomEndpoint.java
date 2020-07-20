package by.ivanshilyaev.rooms.controller;

import by.ivanshilyaev.rooms.bean.Lamp;
import by.ivanshilyaev.rooms.bean.Room;
import by.ivanshilyaev.rooms.service.exception.ServiceException;
import by.ivanshilyaev.rooms.service.impl.RoomServiceImpl;
import by.ivanshilyaev.rooms.service.interfaces.RoomService;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/room.html/{roomId}", encoders = LampEncoder.class, decoders = LampDecoder.class)
public class RoomEndpoint {
    private Session session;
    private int roomId;
    private RoomService service;
    private static final Map<Integer, Set<RoomEndpoint>> repository = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("roomId") int roomId) throws IOException, EncodeException, ServiceException {
        this.session = session;
        this.roomId = roomId;
        service = new RoomServiceImpl();
        if (repository.containsKey(roomId)) {
            repository.get(roomId).add(this);
        } else {
            Set<RoomEndpoint> roomEndpoints = new CopyOnWriteArraySet<>();
            roomEndpoints.add(this);
            repository.put(roomId, roomEndpoints);
        }
        Lamp current = service.read(roomId).get().getLamp();
        session.getBasicRemote().sendObject(current);
    }

    @OnMessage
    public void onMessage(Session session, Lamp lamp) throws IOException, EncodeException, ServiceException {
        Lamp response = new Lamp();
        if (lamp.getState().equals("On")) {
            response.setState("Off");
        } else {
            response.setState("On");
        }
        Room room = service.read(roomId).get();
        room.setLamp(response);
        service.update(room);
        broadcast(response, roomId);
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        repository.get(roomId).remove(this);
        service.close();
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private static void broadcast(Lamp lamp, int roomId) throws IOException, EncodeException {
        Set<RoomEndpoint> roomEndpoints = repository.get(roomId);
        roomEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote()
                            .sendObject(lamp);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
