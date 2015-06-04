package core;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParkingLotRepository extends MongoRepository<ParkingLot, String> {

    ParkingLot findByName(String name);
    List<ParkingLot> findByZipcode(String zipcode);
}
