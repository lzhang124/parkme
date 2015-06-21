package core.controllers;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import core.models.Account;
import core.models.Lot;
import core.models.Space;
import core.repositories.AccountRepository;
import core.repositories.LotRepository;
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
        } else if (!lot.isAvailable()) {
            System.out.println("Lot " + lot + " is full.");
            return null;
        } else {
            if (lot.getOccupied() + 1 == lot.getCapacity() - lot.getReserveMax()) {
                lot.setAvailable(false);
            }
            lot.setOccupied(lot.getOccupied() + 1);
            lotRepo.save(lot);
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
            if (lot.getOccupied() == lot.getCapacity() - lot.getReserveMax()) {
                lot.setAvailable(true);
            }
            lot.setOccupied(lot.getOccupied() - 1);
            lotRepo.save(lot);
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
    public Lot setCalendar(String id, Map<Integer, Boolean> calendar) {
        Lot lot = lotRepo.findById(id);
        if (lot == null) {
            System.out.println("Lot with id " + id + " was not found.");
            return null;
        } else if (!lot.isReservable()) {
            System.out.println("Lot with id " + id + " is not reservable.");
            return null;
        } else {
            List<Space> spaces = lot.getSpaces();
            for (Space space : spaces) {
                if (space.isReservable()) {
                    space.setCalendar(calendar);
                }
            }
            lotRepo.save(lot);
            return lot;
        }
    }
}
