package core.repositories.data;

import core.models.data.RawHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RawHistoryRepository extends MongoRepository<RawHistory, String> {

    List<RawHistory> findByAccountId(String accountId);
    List<RawHistory> findByLotId(String lotId);
    List<RawHistory> findByDate(long date);
}
