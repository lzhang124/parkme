package core;

import org.springframework.data.annotation.Id;

public class ParkingLot {

    @Id
    private String id;

    private String name;
    private String zipcode;
    private String address;
    private int capacity;
    private int occupied;

    public ParkingLot(String name, String zipcode, String address, int capacity) {
        this.name = name;
        this.zipcode = zipcode;
        this.address = address;
        this.capacity = capacity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public String toString() {
        return "ParkingLot [id=" + id +
                ", name=" + name +
                ", zipcode=" + zipcode +
                ", capacity=" + capacity +
                ", occupied=" + occupied + "]";
    }
}
