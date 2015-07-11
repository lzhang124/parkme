package core.repositories;

import java.util.List;

import core.models.Account;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {

    Account findById(String id);
    Account findByEmail(String email);
    List<Account> findByLotsLotId(String lotId);

    @Query("{lots.lotId : ?0 ")
    List<Account> findByLotsLotIdQuery(String lotId);
}
