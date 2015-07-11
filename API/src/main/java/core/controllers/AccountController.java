package core.controllers;

import java.util.*;

import core.models.Account;
import core.models.Lot;
import core.models.sub.LotRole;
import core.repositories.AccountRepository;
import core.repositories.LotRepository;
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

    @RequestMapping(value = "/getLots", method = RequestMethod.GET)
    public List<Lot> getLots(String accountId) {
        Account account = accountRepo.findById(accountId);
        if (account == null) {
            System.out.println("Account with id " + accountId + " was not found.");
            return null;
        } else {
            List<Lot> lots = new ArrayList<>();
            for (LotRole lotRole : account.getLots()) {
                Lot lot = lotRepo.findById(lotRole.getLotId());
                lots.add(lot);
            }
            return lots;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Account login(String email, String password) {
        Account account = accountRepo.findByEmail(email);
        if (BCrypt.checkpw(password, account.getPassword())) {
            System.out.println("Account " + email + " logged in.");
            return account;
        } else {
            System.out.println("Incorrect password.");
            return null;
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public Account signup(String firstName, String lastName, String email, String password) {
        String pw = BCrypt.hashpw(password, BCrypt.gensalt(8));
        Account account = new Account(firstName, lastName, email, pw);
        accountRepo.save(account);
        System.out.println("New Account:" + account);
        return account;
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

    @RequestMapping(value = "/deleteAccount", method = RequestMethod.DELETE)
    public void deleteAccount(String accountId) {
        Account account = accountRepo.findById(accountId);
        if (account == null) {
            System.out.println("Account with id " + accountId + " was not found.");
        } else {
            for (LotRole lotRole : account.getLots()) {
                String lotId = lotRole.getLotId();
                lotRepo.delete(lotId);

                if (Objects.equals(lotRole.getRole(), "owner")) {
                    List<Account> accounts = accountRepo.findByLotsLotId(lotId);
                    for (Account member : accounts) {
                        member.removeLot(lotRole);
                    }
                    accountRepo.save(accounts);
                }
            }
            accountRepo.delete(account);
            System.out.println("Account with id " + accountId + " deleted.");
        }
    }
}
