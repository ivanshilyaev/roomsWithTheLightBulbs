package by.ivanshilyaev.rooms.controller;

import by.ivanshilyaev.rooms.bean.Lamp;
import com.google.gson.Gson;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/room", encoders = LampEncoder.class, decoders = LampDecoder.class)
public class RoomEndpoint {
    private Session session;
    private static final Set<RoomEndpoint> roomEndpoints = new CopyOnWriteArraySet<>();
    private static Map<String, String> users = new HashMap<>();
    private static final Gson GSON = new Gson();
    private static String filePath = "/Users/ivansilaev/Downloads/gitRepos/roomsWithTheLightBulbs/src/main/resources/lamp.json";

    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException {
        this.session = session;
        roomEndpoints.add(this);
        users.put(session.getId(), "username");
        Lamp current = GSON.fromJson(new FileReader(filePath), Lamp.class);
        session.getBasicRemote().sendObject(current);
    }

    @OnMessage
    public void onMessage(Session session, Lamp lamp) throws IOException, EncodeException {
        Lamp response = new Lamp();
        if (lamp.getState().equals("On")) {
            response.setState("Off");
        } else {
            response.setState("On");
        }
        Writer writer = new FileWriter(filePath);
        GSON.toJson(response, writer);
        writer.close();
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
