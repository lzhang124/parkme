package core.repositories.data;

import core.models.data.LotHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LotHistoryRepository extends MongoRepository<LotHistory, String> {

    LotHistory findByLotId(String lotId);
}
