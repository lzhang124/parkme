package core.repositories;

import java.util.List;

import core.models.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {

    Account findById(String id);
    Account findByEmail(String email);
    List<Account> findByLots(String lotId);
}
