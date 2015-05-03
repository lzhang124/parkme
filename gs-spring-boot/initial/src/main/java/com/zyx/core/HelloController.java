package com.zyx.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {
	@Autowired
	private ParkingLotRepository repository;
	
    @RequestMapping("/")
    public String index() {
        return "Greetings from ZYX Parking, blah blah blah... ";
    }
 
    @RequestMapping("/list")
    public String list() {
    	String allLots="";
		for (ParkingLot parkinglot : repository.findAll()) {
			allLots = allLots + "<pre> " + parkinglot.toString() + "</pre>";
		}
        return " Here is all the parking lots in the system ;" + allLots;
    }
    
    @RequestMapping("/getByName")
    public String getByName(String name) {
    	
    	ParkingLot lot = repository.findByName(name);
    	if ( lot == null){
    		return "Lot by Name:" + name + " not Found";
    	} else {
    		return "found:" + "<pre>" + lot + "</pre>";
    	}
        
    }  
    
    @RequestMapping(value = "/entered")
    public String entered(String name ) {
    	ParkingLot lot = repository.findByName(name);
    	if ( lot == null){
    		return "Lot by Name:" + name + " not Found";
    	} else {
    		lot.setOccupied(lot.getOccupied() + 1);
    		repository.save(lot);
    		
    		return "Parking Lot added:" + "<pre>" + lot + "</pre>";
    	}   
    }  
    
    @RequestMapping(value = "/left")
    public String left(String name ) {
    	ParkingLot lot = repository.findByName(name);
    	if ( lot == null){
    		return "Lot by Name:" + name + " not Found";
    	} else {
    		lot.setOccupied(lot.getOccupied() - 1);
    		repository.save(lot);
    		
    		return "Parking Lot added:" + "<pre>" + lot + "</pre>";
    	}   
    }  
    
}
