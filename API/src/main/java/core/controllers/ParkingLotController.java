package core.controllers;

import java.util.List;

import core.models.ParkingLot;
import core.repositories.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class ParkingLotController {

    @Autowired
    private ParkingLotRepository repository;

    @RequestMapping(value = "/listParkingLots", method = RequestMethod.GET)
    public List<ParkingLot> listParkingLots() {
        return repository.findAll();
    }

    @RequestMapping(value = "/lotById", method = RequestMethod.GET)
    public ParkingLot lotById(String id) {
        ParkingLot lot = repository.findById(id);
        if (lot == null) {
            System.out.println("Lot with id " + id + " was not found.");
            return null;
        } else {
            return lot;
        }
    }

    @RequestMapping(value = "/lotByName", method = RequestMethod.GET)
    public ParkingLot lotByName(String name) {
        ParkingLot lot = repository.findByName(name);
        if (lot == null) {
            System.out.println("Lot with name " + name + " was not found.");
            return null;
        } else {
            return lot;
        }
    }

    @RequestMapping(value = "/lotsByZipcode", method = RequestMethod.GET)
    public List<ParkingLot> lotsByZipcode(String zipcode) {
        List<ParkingLot> allLots = repository.findByZipcode(zipcode);
        if (allLots == null) {
            System.out.println("No lots in " + zipcode + ".");
            return null;
        } else {
            return allLots;
        }
    }

    @RequestMapping(value = "/lotByAddress", method = RequestMethod.GET)
    public ParkingLot lotByAddress(String address) {
        ParkingLot lot = repository.findByAddress(address);
        if (lot == null) {
            System.out.println("Lot with address " + address + " was not found.");
            return null;
        } else {
            return lot;
        }
    }

    @RequestMapping(value = "/entered", method = RequestMethod.POST)
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

    @RequestMapping(value = "/exited", method = RequestMethod.POST)
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

    @RequestMapping(value = "/newParkingLot", method = RequestMethod.POST)
    public ParkingLot newLot(String name, String zipcode, String address, String price, int capacity) {
        ParkingLot lot = new ParkingLot(name, zipcode, address, price, capacity);
        repository.save(lot);
        return lot;
    }
}
