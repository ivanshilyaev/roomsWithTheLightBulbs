package by.ivanshilyaev.rooms.controller;

import by.ivanshilyaev.rooms.bean.Lamp;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/room", encoders = LampEncoder.class, decoders = LampDecoder.class)
public class RoomEndpoint {
    private Session session;
    private static final Set<RoomEndpoint> roomEndpoints = new CopyOnWriteArraySet<>();
    private static Map<String, String> users = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException {
        this.session = session;
        roomEndpoints.add(this);
        users.put(session.getId(), "username");
//        Lamp lamp = new Lamp();
//        lamp.setState("On");
//        broadcast(lamp);
    }

    @OnMessage
    public void onMessage(Session session, Lamp lamp) throws IOException, EncodeException {
        Lamp response = new Lamp();
        if (lamp.getState().equals("On")) {
            response.setState("Off");
        } else {
            response.setState("On");
        }
        broadcast(response);
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        roomEndpoints.remove(this);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private static void broadcast(Lamp lamp) throws IOException, EncodeException {
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
