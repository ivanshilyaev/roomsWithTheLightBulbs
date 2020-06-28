package by.ivanshilyaev.rooms.bean;

import java.util.Objects;

public class Room {
    private int id;
    private String name;
    private String country;
    private String lampState;

    public Room() {
    }

    public Room(int id) {
        this.id = id;
    }

    public Room(String name, String country, String lampState) {
        this.name = name;
        this.country = country;
        this.lampState = lampState;
    }

    public Room(int id, String name, String country, String lampState) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.lampState = lampState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLampState() {
        return lampState;
    }

    public void setLampState(String lampState) {
        this.lampState = lampState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id &&
                name.equals(room.name) &&
                country.equals(room.country) &&
                lampState.equals(room.lampState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, lampState);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", lampState='" + lampState + '\'' +
                '}';
    }
}
