package core.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import core.models.Account;
import core.models.Lot;
import core.models.Pair;
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

    @RequestMapping(value = "/listLots", method = RequestMethod.GET)
    public List<Lot> listLots() {
        return lotRepo.findAll();
    }

    @RequestMapping(value = "/lotById", method = RequestMethod.GET)
    public Lot lotById(String id) {
        Lot lot = lotRepo.findById(id);
        if (lot == null) {
            System.out.println("Lot with id " + id + " was not found.");
            return null;
        } else {
            return lot;
        }
    }

    @RequestMapping(value="/searchNear", method = RequestMethod.GET)
    public List<Lot> searchNear(double longitude, double latitude) {
        return lotRepo.findByLocationNear(new Point(longitude, latitude), new Distance(1, Metrics.MILES));
    }

    @RequestMapping(value = "/entered", method = RequestMethod.POST)
    public Lot entered(String id) {
        Lot lot = lotRepo.findById(id);
        if (lot == null) {
            System.out.println("Lot with id " + id + " was not found.");
            return null;
        } else if (lot.getOccupied() == lot.getCapacity() - lot.getReserveMax()) {
            System.out.println("Lot " + lot + " is full.");
            return null;
        } else {
            lot.setOccupied(lot.getOccupied() + 1);
            lotRepo.save(lot);
            addLotHistory(lot);
            return lot;
        }
    }

    @RequestMapping(value = "/exited", method = RequestMethod.POST)
    public Lot exited(String id) {
        Lot lot = lotRepo.findById(id);
        if (lot == null) {
            System.out.println("Lot with id " + id + " was not found.");
            return null;
        } else if (lot.getOccupied() == 0) {
            System.out.println("Lot " + lot + " is empty.");
            return null;
        } else {
            lot.setOccupied(lot.getOccupied() - 1);
            lotRepo.save(lot);
            addLotHistory(lot);
            return lot;
        }
    }

    @RequestMapping(value="/changeReserveMax", method = RequestMethod.POST)
    public Lot changeReserveMax(String id, int reserveMax) {
        Lot lot = lotRepo.findById(id);
        if (lot == null) {
            System.out.println("Lot with id " + id + " was not found.");
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

    @RequestMapping(value="/addMember", method = RequestMethod.POST)
    public Lot addMember(String id, String email, String role) {
        Lot lot = lotRepo.findById(id);
        Account account = accountRepo.findByEmail(email);
        if (lot == null) {
            System.out.println("Lot with id " + id + " was not found.");
            return null;
        } else if (account == null) {
            System.out.println("Account with email " + email + " was not found.");
            return null;
        } else {
            account.addLot(id, role);
            accountRepo.save(account);
            lot.addMember(account.getId());
            lotRepo.save(lot);
            return lot;
        }
    }

    @RequestMapping(value = "/setCalendar", method = RequestMethod.POST)
    public Lot setCalendar(String id, List<Long> startTimes, List<Integer> durations) {
        Lot lot = lotRepo.findById(id);
        if (lot == null) {
            System.out.println("Lot with id " + id + " was not found.");
            return null;
        } else if (!lot.isReservable()) {
            System.out.println("Lot with id " + id + " is not reservable.");
            return null;
        } else {
            List<Pair<Long, Integer>> intervals = new ArrayList<>();
            for (int i = 0; i < startTimes.size(); i++) {
                intervals.add(new Pair<>(startTimes.get(i), durations.get(i)));
            }

            List<Space> spaces = lot.getSpaces();
            for (int i = 0; i < lot.getReserveMax(); i++) {
                Space space = spaces.get(i);
                if (space.isReservable()) {
                    space.setCalendar(intervals);
                }
            }
            lotRepo.save(lot);
            return lot;
        }
    }

    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    public Lot reserve(String id, String accountId, List<Long> startTimes, List<Integer> durations, String search) {
        Lot lot = lotRepo.findById(id);
        if (lot == null) {
            System.out.println("Lot with id " + id + " was not found.");
            return null;
        } else if (!lot.isReservable()) {
            System.out.println("Lot with id " + id + " is not reservable.");
            return null;
        } else {
            List<Pair<Long, Integer>> intervals = new ArrayList<>();
            for (int i = 0; i < startTimes.size(); i++) {
                intervals.add(new Pair<>(startTimes.get(i), durations.get(i)));
            }

            List<Space> spaces = lot.getSpaces();
            for (Pair<Long, Integer> interval : intervals) {
                long start = interval.getL();
                int duration = interval.getR();
                for (int i = 0; i < lot.getReserveMax(); i++) {
                    if (spaces.get(i).isAvailable(start, duration)) {
                        spaces.get(i).addReservation(start, duration, accountId);
                        addRawHistory(accountId, id, i, start, duration, search);
                        break;
                    }
                }
            }
            lotRepo.save(lot);
            return lot;
        }
    }

    @RequestMapping(value = "/clearReservations", method = RequestMethod.POST)
    public Lot clearReservations(String id) {
        Lot lot = lotRepo.findById(id);
        if (lot == null) {
            System.out.println("Lot with id " + id + " was not found.");
            return null;
        } else if (!lot.isReservable()) {
            System.out.println("Lot with id " + id + " is not reservable.");
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

    public LotHistory addLotHistory(Lot lot) {
        String id = lot.getLotHistory();
        LotHistory history = lotHistoryRepo.findById(id);
        if (history == null) {
            System.out.println("History with id " + id + " was not found.");
            return null;
        } else {
            Long date = new Date().getTime();
            history.addHistory(date, lot.getOccupied());
            lotHistoryRepo.save(history);
            return history;
        }
    }

    public RawHistory addRawHistory(String accountId, String lotId, int space, long start, int duration, String search) {
        RawHistory history = new RawHistory(accountId, lotId, space, start, duration, search);
        rawHistoryRepo.save(history);
        return history;
    }
}
