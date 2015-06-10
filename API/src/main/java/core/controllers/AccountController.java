package core.controllers;

import java.util.List;

import core.models.Account;
import core.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository repository;

    @RequestMapping(value = "/listAccounts", method = RequestMethod.GET)
    public List<Account> listAccounts() {
        return repository.findAll();
    }

    @RequestMapping(value = "/accountById", method = RequestMethod.GET)
    public Account accountById(String id) {
        Account account = repository.findById(id);
        if (account == null) {
            System.out.println("Account with id " + id + " was not found.");
            return null;
        } else {
            return account;
        }
    }

    @RequestMapping(value = "/accountByUsername", method = RequestMethod.GET)
    public Account accountByUsername(String username) {
        Account account = repository.findByUsername(username);
        if (account == null) {
            System.out.println("Account with username " + username + " was not found.");
            return null;
        } else {
            return account;
        }
    }

    @RequestMapping(value = "/accountByEmail", method = RequestMethod.GET)
    public Account accountByEmail(String email) {
        Account account = repository.findByEmail(email);
        if (account == null) {
            System.out.println("Account with email " + email + " was not found.");
            return null;
        } else {
            return account;
        }
    }

    @RequestMapping(value = "/addType", method = RequestMethod.POST)
    public Account addType(String id, String type, Object object) {
        Account account = repository.findById(id);
        if (account == null) {
            System.out.println("Account with id " + id + " was not found.");
            return null;
        } else {
            account.addType(type, object);
            repository.save(account);
            return account;
        }
    }

    @RequestMapping(value = "/newAccount", method = RequestMethod.POST)
    public Account newAccount(String firstName, String lastName, String username, String password, String phone, String email) {
        Account account = new Account(firstName, lastName, username, password, phone, email);
        repository.save(account);
        return account;
    }
}
