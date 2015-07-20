package core.controllers;

import java.util.ArrayList;
import java.util.List;

import core.models.Account;
import core.models.Lot;
import core.models.Reservation;
import core.repositories.AccountRepository;
import core.repositories.LotRepository;
import core.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepo;
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private LotRepository lotRepo;

    @RequestMapping(value = "/listReservations", method = RequestMethod.GET)
    public List<Reservation> listReservations() {
        return reservationRepo.findAll();
    }

    @RequestMapping(value = "/reservationById", method = RequestMethod.GET)
    public Reservation reservationById(String reservationId) {
        Reservation reservation = reservationRepo.findById(reservationId);
        if (reservation == null) {
            System.out.println("Reservation with id " + reservationId + " was not found.");
            return null;
        } else {
            return reservation;
        }
    }

    @RequestMapping(value = "/reservationsByAccountId", method = RequestMethod.GET)
    public List<Reservation> reservationByAccountId(String accountId) {
        List<Reservation> reservations = reservationRepo.findByAccountId(accountId);
        if (reservations == null) {
            System.out.println("No reservations with account id " + accountId + " .");
            return null;
        } else {
            return reservations;
        }
    }

    @RequestMapping(value = "/reservationsByLotId", method = RequestMethod.GET)
    public List<Reservation> reservationsByLotId(String lotId) {
        Lot lot = lotRepo.findById(lotId);
        if (lot == null) {
            System.out.println("Lot with lot id " + lotId + " was not found.");
            return null;
        } else {
            int spaces = lot.getReserveMax();
            List<Reservation> reservations = new ArrayList<>();
            for (int i = 0; i < spaces; i++) {
                reservations.addAll(reservationRepo.findByLotIdAndSpace(lotId, i));
            }
            return reservations;
        }
    }

    @RequestMapping(value = "/activeReservationsByAccountId", method = RequestMethod.GET)
    public List<Reservation> activeReservationsByAccountId(String accountId) {
        List<Reservation> reservations = reservationRepo.findByAccountIdAndStatus(accountId, "active");
        if (reservations == null) {
            System.out.println("No active reservations with account id " + accountId + " .");
            return null;
        } else {
            return reservations;
        }
    }

    @RequestMapping(value = "/activeReservationsByLotId", method = RequestMethod.GET)
    public List<Reservation> activeReservationsByLotId(String lotId) {
        Lot lot = lotRepo.findById(lotId);
        if (lot == null) {
            System.out.println("Lot with lot id " + lotId + " was not found.");
            return null;
        } else {
            int spaces = lot.getReserveMax();
            List<Reservation> reservations = new ArrayList<>();
            for (int i = 0; i < spaces; i++) {
                reservations.addAll(reservationRepo.findByLotIdAndSpaceAndStatus(lotId, i, "active"));
            }
            return reservations;
        }
    }

    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    public List<Reservation> reserve(String accountId, String lotId, long[] startTimes, int[] durations) {
        List<Reservation> reservations = new ArrayList<>();
        Lot lot = lotRepo.findById(lotId);
        if (lot == null) {
            System.out.println("Lot with lot id " + lotId + " was not found.");
            return null;
        } else {
            for (int i = 0; i < startTimes.length; i++) {
                long start = startTimes[i];
                int duration = durations[i];

                for (int j = 0; j < duration; j++) {
                    if (!lot.getCalendar().contains(start + j * 3600000)) {
                        System.out.println("Lot is not available at this time.");
                        return null;
                    }
                }
                for (int j = 0; j < lot.getReserveMax(); j++) {
                    Reservation conflict = reservationRepo.checkAvailable(lotId, j, start, start + duration * 3600000);
                    if (conflict == null) {
                        Account account = accountRepo.findById(accountId);
                        Reservation reservation = new Reservation(accountId, account.getFirstName(), account.getLastName(), lotId, j, start, duration);
                        reservationRepo.save(reservation);
                        reservations.add(reservation);
                    } else {
                        System.out.println("Cannot reserve in space " + j + ".");
                    }
                }
                System.out.println("Lot is busy, cannot make reservation at time " + start + ", " + duration + ".");
            }
            return reservations;
        }
    }

    @RequestMapping(value = "/deleteReservationsByAccountId", method = RequestMethod.DELETE)
    public void deleteReservationsByAccountId(String accountId) {
        List<Reservation> reservations = reservationRepo.findByAccountIdAndStatus(accountId, "active");
        reservationRepo.delete(reservations);
        System.out.println("Reservations for account " + accountId + " deleted.");
    }
}
