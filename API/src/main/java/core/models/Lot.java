package core.models;

import java.util.List;
import java.util.ArrayList;
import java.awt.Point;

import org.springframework.data.annotation.Id;

public class Lot {

    @Id
    private String id;

    private String name;
    private String address;
    private double[] location;
    private String price;

    private int capacity;
    private int occupied;
    private boolean reservable;
    private boolean available;

    private List<String> members;
    private List<Point> history;

    public Lot() {}

    public Lot(String name, String address, double longitude, double latitude, String price, int capacity) {
        this.name = name;
        this.address = address;
        this.location = new double[] {longitude, latitude};
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

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
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

    public void addMember(String memberId) {
        this.members.add(memberId);
    }

    public void removeMember(String member) {
        this.members.remove(member);
    }

    public List<Point> getHistory() {
        return history;
    }

    public void addHistory(Point point) {
        this.history.add(point);
    }

    public void removeHistory(Point point) {
        this.history.remove(point);
    }

    @Override
    public String toString() {
        return "Lot [id=" + id +
                ", name=" + name +
                ", address=" + address + "]";
    }
}