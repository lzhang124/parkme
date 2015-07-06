package core.controllers;

import java.util.Date;
import java.util.List;

import core.models.Account;
import core.models.Address;
import core.models.Lot;
import core.models.Space;
import core.models.data.LotHistory;
import core.models.data.RawHistory;
import core.repositories.AccountRepository;
import core.repositories.LotRepository;
import core.repositories.data.LotHistoryRepository;
import core.repositories.data.RawHistoryRepository;
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
    private LotRepository lotRepo;
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private LotHistoryRepository lotHistoryRepo;
    @Autowired
    private RawHistoryRepository rawHistoryRepo;
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
    public Lot newLot(String accountId, String name, String type, String address, double latitude, double longitude, int capacity, int reserveMax, long[] startTimes, int[] durations) {
        Account account = accountRepo.findById(accountId);
        if (account == null) {
            System.out.println("Account with id " + accountId + " was not found.");
            return null;
        } else {
            template.indexOps(Lot.class).ensureIndex(new GeospatialIndex("location"));

            Lot lot = new Lot(name, type, address, latitude, longitude, capacity, reserveMax, startTimes, durations);
            Lot duplicate = lotRepo.findByAddress(lot.getAddress());
            if (duplicate != null) {
                System.out.println("This lot already exists: " + duplicate.getId());
                return null;
            } else {
                lotRepo.save(lot);
                System.out.println("New Lot:" + lot);

                account.addLot(lot.getId(), "owner");
                accountRepo.save(account);
                lot.addMember(accountId);

                LotHistory history = new LotHistory();
                lotHistoryRepo.save(history);
                lot.setLotHistory(history.getId());

                lotRepo.save(lot);
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
        } else if (lot.getOccupied() == lot.getCapacity() - lot.getReserveMax()) {
            System.out.println("Lot " + lot + " is full.");
            lot.setAvailable(false);
            lotRepo.save(lot);
            return null;
        } else {
            lot.setOccupied(lot.getOccupied() + 1);
            lotRepo.save(lot);
            addLotHistory(lot);
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
            lot.setOccupied(lot.getOccupied() - 1);
            lot.setAvailable(true);
            lotRepo.save(lot);
            addLotHistory(lot);
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
            }
            lotRepo.save(lot);
            return lot;
        }
    }

    @RequestMapping(value = "/addMember", method = RequestMethod.POST)
    public Lot addMember(String lotId, String email, String role) {
        Lot lot = lotRepo.findById(lotId);
        Account account = accountRepo.findByEmail(email);
        if (lot == null) {
            System.out.println("Lot with id " + lotId + " was not found.");
            return null;
        } else if (account == null) {
            System.out.println("Account with email " + email + " was not found.");
            return null;
        } else {
            account.addLot(lotId, role);
            accountRepo.save(account);
            lot.addMember(account.getId());
            lotRepo.save(lot);
            return lot;
        }
    }

    @RequestMapping(value = "/setCalendar", method = RequestMethod.POST)
    public Lot setCalendar(String lotId, long[] startTimes, int[] durations) {
        Lot lot = lotRepo.findById(lotId);
        if (lot == null) {
            System.out.println("Lot with id " + lotId + " was not found.");
            return null;
        } else if (!lot.isReservable()) {
            System.out.println("Lot with id " + lotId + " is not reservable.");
            return null;
        } else {
            List<Space> spaces = lot.getSpaces();

            for (int i = 0; i < startTimes.length; i++) {
                long start = startTimes[i];
                int duration = durations[i];
                for (int j = 0; j < lot.getReserveMax(); j++) {
                    Space space = spaces.get(j);
                    if (space.isReservable()) {
                        space.setCalendar(start, duration);
                    }
                }
            }
            lotRepo.save(lot);
            return lot;
        }
    }

    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    public Lot reserve(String lotId, String accountId, long[] startTimes, int[] durations, String search) {
        Lot lot = lotRepo.findById(lotId);
        Account account = accountRepo.findById(accountId);
        if (lot == null) {
            System.out.println("Lot with lotId " + lotId + " was not found.");
            return null;
        } else if (account == null){
            System.out.println("Account with lotId " + accountId + " was not found.");
            return null;
        } else if (!lot.isReservable()) {
            System.out.println("Lot with lotId " + lotId + " is not reservable.");
            return null;
        } else {
            List<Space> spaces = lot.getSpaces();

            for (int i = 0; i < startTimes.length; i++) {
                long start = startTimes[i];
                int duration = durations[i];
                for (int j = 0; j < lot.getReserveMax(); j++) {
                    if (spaces.get(j).isAvailable(start, duration)) {
                        spaces.get(j).addReservation(start, duration, accountId);
                        account.addReservation(lotId, j, start, duration);
                        addRawHistory(accountId, lotId, j, start, duration, search);
                        break;
                    }
                }
            }
            lotRepo.save(lot);
            accountRepo.save(account);
            return lot;
        }
    }

    @RequestMapping(value = "/clearReservations", method = RequestMethod.POST)
    public Lot clearReservations(String lotId) {
        Lot lot = lotRepo.findById(lotId);
        if (lot == null) {
            System.out.println("Lot with id " + lotId + " was not found.");
            return null;
        } else if (!lot.isReservable()) {
            System.out.println("Lot with id " + lotId + " is not reservable.");
            return null;
        } else {
            List<Space> spaces = lot.getSpaces();
            for (int i = 0; i < lot.getReserveMax(); i++) {
                Space space = spaces.get(i);
                if (space.isReservable()) {
                    space.clearReservations();
                }
            }
            lotRepo.save(lot);
            return lot;
        }
    }

    @RequestMapping(value = "/deleteLot", method = RequestMethod.GET)
    public void deleteLot(String lotId) {
        Lot lot = lotRepo.findById(lotId);
        if (lot == null) {
            System.out.println("Lot with id " + lotId + " was not found.");
        } else {
            for (String memberId : lot.getMembers()) {
                Account member = accountRepo.findById(memberId);
                member.removeLot(lotId);
                accountRepo.save(member);
            }
            lotRepo.delete(lot);
            System.out.println("Lot with id " + lotId + " deleted");
        }
    }

    private LotHistory addLotHistory(Lot lot) {
        String lotHistoryId = lot.getLotHistory();
        LotHistory history = lotHistoryRepo.findById(lotHistoryId);
        if (history == null) {
            System.out.println("History with id " + lotHistoryId + " was not found.");
            return null;
        } else {
            Long date = new Date().getTime();
            history.addHistory(date, lot.getOccupied());
            lotHistoryRepo.save(history);
            return history;
        }
    }

    private RawHistory addRawHistory(String accountId, String lotId, int space, long start, int duration, String search) {
        RawHistory history = new RawHistory(accountId, lotId, space, start, duration, search);
        rawHistoryRepo.save(history);
        return history;
    }
}
