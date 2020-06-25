package by.ivanshilyaev.rooms.bean;

import java.util.Objects;

public class Room {
    private String name;
    private String country;
    private boolean lamp;

    public Room() {
    }

    public Room(boolean lamp) {
        this.lamp = lamp;
    }

    public Room(String name, String country, boolean lamp) {
        this.name = name;
        this.country = country;
        this.lamp = lamp;
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

    public boolean isLamp() {
        return lamp;
    }

    public void setLamp(boolean lamp) {
        this.lamp = lamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return lamp == room.lamp &&
                name.equals(room.name) &&
                country.equals(room.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country, lamp);
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", lamp=" + lamp +
                '}';
    }
}
