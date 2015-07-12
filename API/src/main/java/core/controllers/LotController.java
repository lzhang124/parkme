package core.controllers;

import java.util.Date;
import java.util.List;

import core.models.Account;
import core.models.Lot;
import core.models.Reservation;
import core.models.data.LotHistory;
import core.repositories.AccountRepository;
import core.repositories.LotRepository;
import core.repositories.ReservationRepository;
import core.repositories.data.LotHistoryRepository;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class LotController {

    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private LotRepository lotRepo;
    @Autowired
    private ReservationRepository reservationRepo;
    @Autowired
    private LotHistoryRepository lotHistoryRepo;
    @Autowired
    MongoTemplate template;

    @RequestMapping(value = "/listLots", method = RequestMethod.GET)
    public List<Lot> listLots() {
        return lotRepo.findAll();
    }

    @RequestMapping(value = "/lotById", method = RequestMethod.GET)
    public Lot lotById(String lotId) {
        Lot lot = lotRepo.findById(lotId);
        if (lot == null) {
            System.out.println("Lot with id " + lotId + " was not found.");
            return null;
        } else {
            return lot;
        }
    }

    @RequestMapping(value="/searchNear", method = RequestMethod.GET)
    public List<Lot> searchNear(double latitude, double longitude) {
        return lotRepo.findByLocationNear(new Point(longitude, latitude), new Distance(1, Metrics.MILES));
    }

    @RequestMapping(value = "/newLot", method = RequestMethod.POST)
    public Lot newLot(String accountId, String name, String type, String address, double latitude, double longitude, int capacity, int reserveMax, long[] calendar) {
        Account account = accountRepo.findById(accountId);
        if (account == null) {
            System.out.println("Account with id " + accountId + " was not found.");
            return null;
        } else {
            template.indexOps(Lot.class).ensureIndex(new GeospatialIndex("location"));

            Lot lot = new Lot(name, type, address, latitude, longitude, capacity, reserveMax, calendar);
            Lot duplicate = lotRepo.findByAddress(lot.getAddress());
            if (duplicate != null) {
                System.out.println("This lot already exists: " + duplicate.getId());
                return null;
            } else {
                System.out.println("New Lot:" + lot);
                lotRepo.save(lot);

                account.addLot(lot.getId(), "owner");
                accountRepo.save(account);

                LotHistory history = new LotHistory(lot.getId());
                lotHistoryRepo.save(history);

                return lot;
            }
        }
    }

    @RequestMapping(value = "/entered", method = RequestMethod.POST)
    public Lot entered(String lotId) {
        Lot lot = lotRepo.findById(lotId);
        if (lot == null) {
            System.out.println("Lot with id " + lotId + " was not found.");
            return null;
        } else if (!lot.isAvailable()) {
            System.out.println("Lot " + lot + " is full.");
            return null;
        } else {
            lot.setOccupied(lot.getOccupied() + 1);
            if (lot.getOccupied() == lot.getCapacity()) {
                lot.setAvailable(false);
            }
            lotRepo.save(lot);
            addLotHistory(lotId, lot.getOccupied());
            return lot;
        }
    }

    @RequestMapping(value = "/exited", method = RequestMethod.POST)
    public Lot exited(String lotId) {
        Lot lot = lotRepo.findById(lotId);
        if (lot == null) {
            System.out.println("Lot with id " + lotId + " was not found.");
            return null;
        } else if (lot.getOccupied() == 0) {
            System.out.println("Lot " + lot + " is empty.");
            return null;
        } else {
            if (lot.getOccupied() == lot.getCapacity()) {
                lot.setAvailable(true);
            }
            lot.setOccupied(lot.getOccupied() - 1);
            lotRepo.save(lot);
            addLotHistory(lotId, lot.getOccupied());
            return lot;
        }
    }

    @RequestMapping(value = "/changeReserveMax", method = RequestMethod.POST)
    public Lot changeReserveMax(String lotId, int reserveMax) {
        Lot lot = lotRepo.findById(lotId);
        if (lot == null) {
            System.out.println("Lot with id " + lotId + " was not found.");
            return null;
        } else {
            lot.setReserveMax(reserveMax);
            if (reserveMax == 0) {
                lot.setReservable(false);
                lot.setCalendar(null);
            } else {
                lot.setReservable(true);
            }
            lotRepo.save(lot);
            return lot;
        }
    }

    @RequestMapping(value = "/setCalendar", method = RequestMethod.POST)
    public Lot setCalendar(String lotId, long[] calendar) {
        Lot lot = lotRepo.findById(lotId);
        if (lot == null) {
            System.out.println("Lot with id " + lotId + " was not found.");
            return null;
        } else if (!lot.isReservable()) {
            System.out.println("Lot with id " + lotId + " is not reservable.");
            return null;
        } else {
            lot.setCalendar(calendar);
            lotRepo.save(lot);
            return lot;
        }
    }

    @RequestMapping(value = "/deleteLot", method = RequestMethod.DELETE)
    public void deleteLot(String lotId) {
        Lot lot = lotRepo.findById(lotId);
        lotRepo.delete(lot);
        List<Account> accounts = accountRepo.findByLots(lotId);
        for (Account account : accounts) {
            account.removeLot(lotId);
        }
        accountRepo.save(accounts);

        for (int i = 0; i < lot.getReserveMax(); i++) {
            List<Reservation> reservations = reservationRepo.findByLotIdAndSpaceAndStatus(lotId, i, "active");
            for (Reservation reservation : reservations) {
                reservation.setStatus("deleted");
            }
            reservationRepo.save(reservations);
        }

        System.out.println("Lot with id " + lotId + " deleted.");
    }

    private void addLotHistory(String lotId, int occupied) {
        LotHistory history = lotHistoryRepo.findByLotId(lotId);
        if (history == null) {
            System.out.println("History with lot " + lotId + " was not found.");
        } else {
            long date = new Date().getTime();
            history.addHistory(date, occupied);
            lotHistoryRepo.save(history);
        }
    }
}
