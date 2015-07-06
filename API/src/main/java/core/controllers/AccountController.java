package core.controllers;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @RequestMapping(value = "/newAccount", method = RequestMethod.POST)
    public Account newAccount(String firstName, String lastName, String email, String password) {
        Account account = new Account(firstName, lastName, email, password);
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

    @RequestMapping(value = "/deleteAccount", method = RequestMethod.GET)
    public void deleteAccount(String accountId) {
        Account account = accountRepo.findById(accountId);
        if (account == null) {
            System.out.println("Account with id " + accountId + " was not found.");
        } else {
            for (Map.Entry<String, String> entry : account.getLots().entrySet()) {
                String lotId = entry.getKey();
                String role = entry.getValue();

                Lot lot = lotRepo.findById(lotId);
                if (Objects.equals(role, "owner")) {
                    for (String memberId : lot.getMembers()) {
                        Account member = accountRepo.findById(memberId);
                        member.removeLot(lotId);
                        accountRepo.save(member);
                    }
                    lotRepo.delete(lot);
                    System.out.println("Lot with id " + lotId + " deleted");
                } else {
                    lot.removeMember(accountId);
                    lotRepo.save(lot);
                }
            }
            accountRepo.delete(account);
            System.out.println("Account with id " + accountId + " deleted.");
        }
    }
}
