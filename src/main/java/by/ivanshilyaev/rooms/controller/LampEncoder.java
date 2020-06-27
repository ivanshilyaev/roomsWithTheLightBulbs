package by.ivanshilyaev.rooms.controller;

import by.ivanshilyaev.rooms.bean.Lamp;
import com.google.gson.Gson;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class LampEncoder implements Encoder.Text<Lamp> {
    private static Gson gson = new Gson();

    @Override
    public String encode(Lamp lamp) throws EncodeException {
        return gson.toJson(lamp);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
}
