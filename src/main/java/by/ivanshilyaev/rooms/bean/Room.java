package by.ivanshilyaev.rooms.bean;

import lombok.Data;

@Data
public class Room {
    private int id;
    private String name;
    private String country;
    private Lamp lamp;
}
