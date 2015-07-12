package core.repositories;

import java.util.List;

import core.models.Reservation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReservationRepository extends MongoRepository<Reservation, String> {

    Reservation findById(String id);
    List<Reservation> findByAccountId(String accountId);
    List<Reservation> findByAccountIdAndStatus(String accountId, String status);
    List<Reservation> findByLotIdAndSpace(String lotId, int space);
    List<Reservation> findByLotIdAndSpaceAndStatus(String lotId, int space, String status);

    @Query("{lotId : ?0, space : ?1, $or : [{start : {$gte : ?2, $lt : ?3}}, {end : {$gt : ?2, $lte : ?3}}]}")
    Reservation checkAvailable(String lotId, int space, long start, long end);
}
