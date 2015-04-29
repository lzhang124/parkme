package hello;
 
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParkingLotRepository extends MongoRepository<ParkingLot, String> {

    public ParkingLot findByName(String name);
    public List<ParkingLot> findByZipcode(String zipcode);

}