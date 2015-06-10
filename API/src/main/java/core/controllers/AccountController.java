package core.controllers;

import java.util.List;

import core.models.Account;
import core.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository repository;

    @RequestMapping("/list")
    public List<Account> list() {
        return repository.findAll();
    }

    @RequestMapping("/accountById")
    public Account accountById(String id) {
        Account account = repository.findById(id);
        if (account == null) {
            System.out.println("Account with id " + id + " was not found.");
            return null;
        } else {
            return account;
        }
    }

    @RequestMapping("/accountByUsername")
    public Account accountByUsername(String username) {
        Account account = repository.findByUsername(username);
        if (account == null) {
            System.out.println("Account with username " + username + " was not found.");
            return null;
        } else {
            return account;
        }
    }

    @RequestMapping("/accountByEmail")
    public Account accountByEmail(String email) {
        Account account = repository.findByEmail(email);
        if (account == null) {
            System.out.println("Account with email " + email + " was not found.");
            return null;
        } else {
            return account;
        }
    }

    @RequestMapping("/addType")
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

    @RequestMapping("/new")
    public Account newAccount(String firstName, String lastName, String username, String password, String phone, String email) {
        Account account = new Account(firstName, lastName, username, password, phone, email);
        repository.save(account);
        return account;
    }
}
