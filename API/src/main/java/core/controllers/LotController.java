package core.controllers;

import java.util.List;

import core.models.Account;
import core.models.Lot;
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

    @RequestMapping(value = "/lotByAddress", method = RequestMethod.GET)
    public Lot lotByAddress(String address) {
        Lot lot = lotRepo.findByAddress(address);
        if (lot == null) {
            System.out.println("Lot with address " + address + " was not found.");
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
        } else if (lot.getOccupied() == lot.getCapacity()) {
            System.out.println("Lot " + lot + " is full.");
            return null;
        } else {
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
            lot.setOccupied(lot.getOccupied() - 1);
            lotRepo.save(lot);
            return lot;
        }
    }

    @RequestMapping(value="/changeAvailable", method = RequestMethod.POST)
    public Lot changeAvailable(String id) {
        Lot lot = lotRepo.findById(id);
        if (lot == null) {
            System.out.println("Lot with id " + id + " was not found.");
            return null;
        } else {
            lot.setAvailable(!lot.isAvailable());
            lotRepo.save(lot);
            return lot;
        }
    }

    @RequestMapping(value="/changeReservable", method = RequestMethod.POST)
    public Lot changeReservable(String id) {
        Lot lot = lotRepo.findById(id);
        if (lot == null) {
            System.out.println("Lot with id " + id + " was not found.");
            return null;
        } else {
            lot.setReservable(!lot.isReservable());
            lotRepo.save(lot);
            return lot;
        }
    }

    @RequestMapping(value="/addMember", method = RequestMethod.POST)
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
}
