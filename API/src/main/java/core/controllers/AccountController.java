package core.controllers;

import java.util.List;

import core.models.Account;
import core.models.Lot;
import core.repositories.AccountRepository;
import core.repositories.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private LotRepository lotRepo;

    @RequestMapping(value = "/listAccounts", method = RequestMethod.GET)
    public List<Account> listAccounts() {
        return accountRepo.findAll();
    }

    @RequestMapping(value = "/accountById", method = RequestMethod.GET)
    public Account accountById(String id) {
        Account account = accountRepo.findById(id);
        if (account == null) {
            System.out.println("Account with id " + id + " was not found.");
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

    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public Account addRole(String id, String role) {
        Account account = accountRepo.findById(id);
        if (account == null) {
            System.out.println("Account with id " + id + " was not found.");
            return null;
        } else {
            account.addRole(role);
            accountRepo.save(account);
            return account;
        }
    }

    @RequestMapping(value = "/newAccount", method = RequestMethod.POST)
    public Account newAccount(String firstName, String lastName, String email, String password) {
        Account account = new Account(firstName, lastName, email, password);
        accountRepo.save(account);
        System.out.println("New Account:" + account);
        return account;
    }

    @RequestMapping(value = "/newLot", method = RequestMethod.POST)
    public Account newLot(String id, String name, String address, Double latitude, Double longitude, String price, int capacity) {
        Account account = accountRepo.findById(id);
        if (account == null) {
            System.out.println("Account with id " + id + " was not found.");
            return null;
        } else {
            Lot lot = new Lot(name, address, latitude, longitude, price, capacity);
            lotRepo.save(lot);
            System.out.println("New Lot:" + lot);
            account.addLot(lot.getId(), "Owner");
            accountRepo.save(account);
            lot.addMember(id);
            lotRepo.save(lot);
            return account;
        }
    }
}
