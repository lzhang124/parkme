package core.repositories.data;

import core.models.data.SearchHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SearchHistoryRepository extends MongoRepository<SearchHistory, String> {

    SearchHistory findByAccountId(String accountId);
}
