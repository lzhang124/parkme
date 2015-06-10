package core.Repositories;

import core.models.Systems;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SystemsRepository extends MongoRepository<Systems, String> {

    Systems findById(String id);
}
