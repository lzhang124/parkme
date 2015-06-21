package core.models;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.data.annotation.Id;

public class Lot {

    @Id
    private String id;

    private String name;
    private String type;
    private Address address;
    private double[] location;
    private Object photo;

    private Map<String, Double> rate;
    private double rateRatio;

    private int capacity;
    private int occupied;
    private boolean available;
    private boolean reservable;
    private int reserveMax;
    private List<Space> spaces;

    private List<String> members;

    public Lot() {}

    public Lot(String name, String type, Address address, double longitude, double latitude, String rateType, Double price, int capacity, int reserveMax) {
        this.name = name;
        this.type = type;
        this.address = address;
        this.location = new double[] {longitude, latitude};
        this.rate = new HashMap<>();
        this.rate.put(rateType, price);
        this.rateRatio = 1.0;
        this.capacity = capacity;
        this.available = true;
        if (reserveMax > 0) {
            this.reservable = true;
        }
        this.reserveMax = reserveMax;

        this.spaces = new ArrayList<>();
        for (int i = 0; i < reserveMax; i ++) {
            this.spaces.add(new Space(true));
        }
        for (int i = 0; i < capacity-reserveMax; i++) {
            this.spaces.add(new Space(false));
        }

        this.members = new ArrayList<>();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) {
        this.photo = photo;
    }

    public Map<String, Double> getRate() {
        return rate;
    }

    public void addRate(String rateType, Double price) {
        this.rate.put(rateType, price);
    }

    public void removeRate(String rateType) {
        this.rate.remove(rateType);
    }

    public double getRateRatio() {
        return rateRatio;
    }

    public void setRateRatio(double rateRatio) {
        this.rateRatio = rateRatio;
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

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean isReservable() {
        return reservable;
    }

    public void setReservable(boolean reservable) {
        this.reservable = reservable;
    }

    public int getReserveMax() {
        return reserveMax;
    }

    public void setReserveMax(int reserveMax) {
        this.reserveMax = reserveMax;
    }

    public List<Space> getSpaces() {
        return spaces;
    }

    public void addSpace(Space space) {
        this.spaces.add(space);
    }

    public void removeSpace(Space space) {
        this.spaces.remove(space);
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

    @Override
    public String toString() {
        return "Lot [id=" + id +
                ", name=" + name +
                ", address=" + address + "]";
    }
}
