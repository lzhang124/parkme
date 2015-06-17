package core.models;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;

class Point<X, Y> {
    public final X x;
    public final Y y;
    public Point(X x, Y y) {
        this.x = x;
        this.y = y;
    }
}

public class ParkingLot {

    @Id
    private String id;

    private String name;
    private String address;
    private Float latitude;
    private Float longitude;
    private String price;

    private int capacity;
    private int occupied;
    private boolean reservable;
    private boolean available;

    private List<String> members;
    private List<Point> history;

    public ParkingLot(String name, String address, Float latitude, Float longitude, String price, int capacity) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.capacity = capacity;

        this.members = new ArrayList<>();
        this.history = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getOccupied() {
        return occupied;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }

    public boolean isReservable() {
        return reservable;
    }

    public void setReservable(boolean reservable) {
        this.reservable = reservable;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<String> getMembers() {
        return members;
    }

    public void addMember(String member) {
        this.members.add(member);
    }

    public void removeMember(String member) {
        this.members.remove(member);
    }

    public List<Point> getHistory() {
        return history;
    }

    public void addPoint(Point point) {
        this.history.add(point);
    }

    public void removePoint(Point point) {
        this.history.remove(point);
    }

    @Override
    public String toString() {
        return "ParkingLot [id=" + id +
                ", name=" + name +
                ", address=" + address + "]";
    }
}
