package by.ivanshilyaev.rooms.bean;

import java.util.Objects;

public class Room {
    private int id;
    private String name;
    private String country;
    private Lamp lamp;

    public Room() {
    }

    public Room(int id) {
        this.id = id;
    }

    public Room(String name, String country, Lamp lamp) {
        this.name = name;
        this.country = country;
        this.lamp = lamp;
    }

    public Room(String name, String country) {
        this.name = name;
        this.country = country;
        lamp = new Lamp();
    }

    public Room(int id, String name, String country, Lamp lamp) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.lamp = lamp;
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

    public Lamp getLamp() {
        return lamp;
    }

    public void setLamp(Lamp lamp) {
        this.lamp = lamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id &&
                name.equals(room.name) &&
                country.equals(room.country) &&
                lamp.equals(room.lamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, lamp);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", lamp='" + lamp.toString() + '\'' +
                '}';
    }
}
