package hello;

import org.springframework.data.annotation.Id;


public class ParkingLot {

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
