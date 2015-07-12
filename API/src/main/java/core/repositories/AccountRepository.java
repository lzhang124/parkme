package core.repositories;

import java.util.List;

import core.models.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AccountRepository extends MongoRepository<Account, String> {

    Account findById(String id);
    Account findByEmail(String email);

    @Query("{lots.?0 : {$exists : true}}")
    List<Account> findByLots(String lotId);
}
