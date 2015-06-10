package core.controllers;

import java.util.List;

import core.models.ParkingLot;
import core.Repositories.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class ParkingLotController {

    @Autowired
    private ParkingLotRepository repository;

    @RequestMapping("/list")
    public List<ParkingLot> list() {
        return repository.findAll();
    }

    @RequestMapping("/lotById")
    public ParkingLot lotById(String id) {
        ParkingLot lot = repository.findById(id);
        if (lot == null) {
            System.out.println("Lot with id " + id + " was not found.");
            return null;
        } else {
            return lot;
        }
    }

    @RequestMapping("/lotByName")
    public ParkingLot lotByName(String name) {
        ParkingLot lot = repository.findByName(name);
        if (lot == null) {
            System.out.println("Lot with name " + name + " was not found.");
            return null;
        } else {
            return lot;
        }
    }

    @RequestMapping("/lotsByZipcode")
    public List<ParkingLot> lotsByZipcode(String zipcode) {
        List<ParkingLot> allLots = repository.findByZipcode(zipcode);
        if (allLots == null) {
            System.out.println("No lots in " + zipcode + ".");
            return null;
        } else {
            return allLots;
        }
    }

    @RequestMapping("/lotByAddress")
    public ParkingLot lotByAddress(String address) {
        ParkingLot lot = repository.findByAddress(address);
        if (lot == null) {
            System.out.println("Lot with address " + address + " was not found.");
            return null;
        } else {
            return lot;
        }
    }

    @RequestMapping("/entered")
    public ParkingLot entered(String name) {
        ParkingLot lot = repository.findByName(name);
        if (lot == null) {
            System.out.println("Lot with name " + name + " was not found.");
            return null;
        } else {
            lot.setOccupied(lot.getOccupied() + 1);
            repository.save(lot);
            return lot;
        }
    }

    @RequestMapping("/exited")
    public ParkingLot exited(String id) {
        ParkingLot lot = repository.findById(id);
        if (lot == null) {
            System.out.println("Lot with id " + id + " was not found.");
            return null;
        } else if (lot.getOccupied() == 0) {
            System.out.println("Lot with id " + id + " is empty.");
            return null;
        } else {
            lot.setOccupied(lot.getOccupied() - 1);
            repository.save(lot);
            return lot;
        }
    }

    @RequestMapping("/new")
    public ParkingLot newLot(String name, String zipcode, String address, String price, int capacity) {
        ParkingLot lot = new ParkingLot(name, zipcode, address, price, capacity);
        repository.save(lot);
        return lot;
    }
}
