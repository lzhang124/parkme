package core.Repositories;

import core.models.LotGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LotGroupRepository extends MongoRepository<LotGroup, String> {

    LotGroup findById(String id);
    LotGroup findByLotId(String lotId);
}
