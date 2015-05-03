package com.zyx.core;

import org.springframework.data.annotation.Id;


public class ParkingLot {

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

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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

	@Id
    private String id;

    private String name;
    private String zipcode;
    private int capacity;
    private int occupied;

    public ParkingLot() {}

    public ParkingLot(String name, String zipcode, int capacity) {
        this.name = name;
        this.zipcode = zipcode;
	this.capacity = capacity;
    }

    @Override
	public String toString() {
		return "ParkingLot [id=" + id + ", name=" + name + ", zipcode="
				+ zipcode + ", capacity=" + capacity + ", occupied=" + occupied
				+ "]";
	}

}
