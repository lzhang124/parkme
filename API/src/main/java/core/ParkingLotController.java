package core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class ParkingLotController {

    @Autowired
    private ParkingLotRepository repository;

    @RequestMapping("/list")
    public String list() {
        String allLots = "";
        for (ParkingLot parkingLot : repository.findAll()) {
            allLots += "<pre> " + parkingLot.toString() + "</pre>";
        }
        return "Here are all the parking lots in the system: " + allLots;
    }

    @RequestMapping("/lotByName")
    public String lotByName(String name) {
        ParkingLot lot = repository.findByName(name);
        if (lot == null) {
            return "Lot by name " + name + " was not found.";
        } else {
            return "Found:" + "<pre>" + lot + "</pre>";
        }
    }

    @RequestMapping("/entered")
    public String entered(String name) {
        ParkingLot lot = repository.findByName(name);
        if (lot == null) {
            return "Lot by name " + name + " was not found.";
        } else {
            lot.setOccupied(lot.getOccupied() + 1);
            repository.save(lot);
            return "Car entered lot by name " + name + "<pre>" + lot + "</pre>";
        }
    }

    @RequestMapping("/exited")
    public String exited(String name) {
        ParkingLot lot = repository.findByName(name);
        if (lot == null) {
            return "Lot by name " + name + " was not found.";
        } else if (lot.getOccupied() == 0) {
            return "Lot by name " + name + " is empty.";
        } else {
            lot.setOccupied(lot.getOccupied() - 1);
            repository.save(lot);
            return "Car exited lot by name " + name + "<pre>" + lot + "</pre>";
        }
    }

//    @RequestMapping("/new")
}
