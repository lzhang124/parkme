package core.repositories;

import java.util.List;

import core.models.Address;
import core.models.Lot;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LotRepository extends MongoRepository<Lot, String> {

    Lot findById(String id);
    Lot findByAddress(Address address);
    List<Lot> findByLocationNear(Point point, Distance distance);
}
