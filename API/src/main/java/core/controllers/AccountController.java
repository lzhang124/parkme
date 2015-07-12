package core.controllers;

import java.util.*;

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
import org.springframework.security.crypto.bcrypt.BCrypt;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private LotRepository lotRepo;
    @Autowired
    private ReservationRepository reservationRepo;

    @RequestMapping(value = "/listAccounts", method = RequestMethod.GET)
    public List<Account> listAccounts() {
        return accountRepo.findAll();
    }

    @RequestMapping(value = "/accountById", method = RequestMethod.GET)
    public Account accountById(String accountId) {
        Account account = accountRepo.findById(accountId);
        if (account == null) {
            System.out.println("Account with id " + accountId + " was not found.");
            return null;
        } else {
            return account;
        }
    }

    @RequestMapping(value = "/accountByEmail", method = RequestMethod.GET)
    public Account accountByEmail(String email) {
        Account account = accountRepo.findByEmail(email);
        if (account == null) {
            System.out.println("Account with email " + email + " was not found.");
            return null;
        } else {
            return account;
        }
    }

    @RequestMapping(value = "/checkAccount", method = RequestMethod.GET)
    public boolean checkAccount(String email) {
        Account account = accountRepo.findByEmail(email);
        if (account == null) {
            return false;
        } else {
            return true;
        }
    }

    @RequestMapping(value = "/getLots", method = RequestMethod.GET)
    public List<Lot> getLots(String accountId) {
        Account account = accountRepo.findById(accountId);
        if (account == null) {
            System.out.println("Account with id " + accountId + " was not found.");
            return null;
        } else {
            List<Lot> lots = new ArrayList<>();
            for (String lotId : account.getLots().keySet()) {
                Lot lot = lotRepo.findById(lotId);
                lots.add(lot);
            }
//            for (LotRole lotRole : account.getLots()) {
//                Lot lot = lotRepo.findById(lotRole.getLotId());
//                lots.add(lot);
//            }
            return lots;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Account login(String email, String password) {
        Account account = accountRepo.findByEmail(email);
        if (account == null) {
            System.out.println("Account with email " + email + " was not found.");
            return null;
        } else if (BCrypt.checkpw(password, account.getPassword())) {
            System.out.println("Account " + email + " logged in.");
            return account;
        } else {
            System.out.println("Incorrect password.");
            return null;
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public Account signup(String firstName, String lastName, String email, String password) {
        Account account = accountRepo.findByEmail(email);
        if (account == null) {
            String pw = BCrypt.hashpw(password, BCrypt.gensalt(8));
            Account newAccount = new Account(firstName, lastName, email, pw);
            accountRepo.save(newAccount);
            System.out.println("New Account:" + newAccount);
            return newAccount;
        } else {
            System.out.println("Account with email " + email + " already exists.");
            return null;
        }
    }

    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public Account addRole(String accountId, String role) {
        Account account = accountRepo.findById(accountId);
        if (account == null) {
            System.out.println("Account with id " + accountId + " was not found.");
            return null;
        } else {
            account.addRole(role);
            accountRepo.save(account);
            return account;
        }
    }

    @RequestMapping(value = "/addLot", method = RequestMethod.POST)
    public Account addMember(String accountId, String lotId, String role) {
        Account account = accountRepo.findById(accountId);
        if (account == null) {
            System.out.println("Account with id " + accountId + " was not found.");
            return null;
        } else {
            account.addLot(lotId, role);
            accountRepo.save(account);
            return account;
        }
    }

    @RequestMapping(value = "/deleteAccount", method = RequestMethod.DELETE)
    public void deleteAccount(String accountId) {
        Account account = accountRepo.findById(accountId);
        if (account == null) {
            System.out.println("Account with id " + accountId + " was not found.");
        } else {
            for (String lotId : account.getLots().keySet()) {
                Lot lot = lotRepo.findById(lotId);
                lotRepo.delete(lot);
                if (Objects.equals(account.getLots().get(lotId), "owner")) {
                    List<Account> members = accountRepo.findByLots(lotId);
                    for (Account member: members) {
                        member.removeLot(lotId);
                    }
                    accountRepo.save(members);

                    for (int i = 0; i < lot.getReserveMax(); i++) {
                        List<Reservation> reservations = reservationRepo.findByLotIdAndSpaceAndStatus(lotId, i, "active");
                        for (Reservation reservation : reservations) {
                            reservation.setStatus("deleted");
                        }
                        reservationRepo.save(reservations);
                    }
                }
                System.out.println("Lot with id " + lotId + " deleted.");

                List<Reservation> reservations = reservationRepo.findByAccountIdAndStatus(accountId, "active");
                reservationRepo.delete(reservations);
                System.out.println("Reservations for account " + accountId + " deleted.");
            }

//            for (LotRole lotRole : account.getLots()) {
//                String lotId = lotRole.getLotId();
//                lotRepo.delete(lotId);
//
//                if (Objects.equals(lotRole.getRole(), "owner")) {
//                    List<Account> accounts = accountRepo.findByLotsLotId(lotId);
//                    for (Account member : accounts) {
//                        member.removeLot(lotRole);
//                    }
//                    accountRepo.save(accounts);
//                }
//
//                System.out.println("Lot with id " + lotId + " deleted.");
//            }
            accountRepo.delete(account);
            System.out.println("Account with id " + accountId + " deleted.");
        }
    }
}
