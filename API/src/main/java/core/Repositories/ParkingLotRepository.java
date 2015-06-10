package core.Repositories;

import java.util.List;

import core.models.ParkingLot;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParkingLotRepository extends MongoRepository<ParkingLot, String> {

    ParkingLot findById(String id);
    ParkingLot findByName(String name);
    List<ParkingLot> findByZipcode(String zipcode);
    ParkingLot findByAddress(String address);
}
