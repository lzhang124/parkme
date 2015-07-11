package core.models;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import core.models.sub.Address;
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

    private List<Long> calendar;

    public Lot() {}

    public Lot(String name, String type, String address, double latitude, double longitude, int capacity, int reserveMax, long[] times) {
        this.name = name;
        this.type = type;

        String[] splitAddress = address.split(", ");
        String street = splitAddress[0];
        String city = splitAddress[1];
        String state = splitAddress[2].split(" ")[0];
        String zipcode = splitAddress[2].split(" ")[1];
        this.address = new Address(street, city, state, zipcode);

        this.location = new double[] {longitude, latitude};
        this.rate = new HashMap<>();
        this.rate.put("hour", 2.25);
        this.rateRatio = 1.0;
        this.capacity = capacity;
        this.available = true;
        if (reserveMax > 0) {
            this.reservable = true;
        }
        this.reserveMax = reserveMax;

        this.calendar = new ArrayList<>();
        for (long time : times) {
            this.calendar.add(time);
        }
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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

    public List<Long> getCalendar() {
        return calendar;
    }

    public void setCalendar(List<Long> calendar) {
        this.calendar = calendar;
    }

    @Override
    public String toString() {
        return "Lot [id=" + id +
                ", name=" + name +
                ", address=" + address + "]";
    }
}
