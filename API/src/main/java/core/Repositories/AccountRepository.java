package core.repositories;

import core.models.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {

    Account findById(String id);
    Account findByUsername(String username);
    Account findByEmail(String email);
}
