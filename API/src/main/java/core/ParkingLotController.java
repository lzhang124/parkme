package core;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Array;

@RestController
public class ParkingLotController {

    @Autowired
    private ParkingLotRepository repository;

    @RequestMapping("/list")
    public List<ParkingLot> list() {
        List<ParkingLot> allLots = new ArrayList<>();
        for (ParkingLot parkingLot : repository.findAll()) {
            allLots.add(parkingLot);
        }
        return allLots;
    }

    @RequestMapping("/lotByName")
    public ParkingLot lotByName(String name) {
        ParkingLot lot = repository.findByName(name);
        if (lot == null) {
            System.out.println("Lot by name " + name + " was not found.");
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

    @RequestMapping("/entered")
    public ParkingLot entered(String name) {
        ParkingLot lot = repository.findByName(name);
        if (lot == null) {
            System.out.println("Lot by name " + name + " was not found.");
            return null;
        } else {
            lot.setOccupied(lot.getOccupied() + 1);
            repository.save(lot);
            return lot;
        }
    }

    @RequestMapping("/exited")
    public ParkingLot exited(String name) {
        ParkingLot lot = repository.findByName(name);
        if (lot == null) {
            System.out.println("Lot by name " + name + " was not found.");
            return null;
        } else if (lot.getOccupied() == 0) {
            System.out.println("Lot by name " + name + " is empty.");
            return null;
        } else {
            lot.setOccupied(lot.getOccupied() - 1);
            repository.save(lot);
            return lot;
        }
    }

//    @RequestMapping("/new")
}
