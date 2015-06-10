package core.controllers;

import java.util.List;
import java.util.Objects;

import core.models.Account;
import core.models.User;
import core.models.LotGroup;
import core.models.Systems;
import core.repositories.AccountRepository;
import core.repositories.UserRepository;
import core.repositories.LotGroupRepository;
import core.repositories.SystemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class AccountController {
    
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LotGroupRepository lotGroupRepository;
    @Autowired
    private SystemsRepository systemsRepository;

    // ACCOUNTS //
    @RequestMapping(value = "/listAccounts", method = RequestMethod.GET)
    public List<Account> listAccounts() {
        return accountRepository.findAll();
    }

    @RequestMapping(value = "/accountById", method = RequestMethod.GET)
    public Account accountById(String id) {
        Account account = accountRepository.findById(id);
        if (account == null) {
            System.out.println("Account with id " + id + " was not found.");
            return null;
        } else {
            return account;
        }
    }

    @RequestMapping(value = "/accountByUsername", method = RequestMethod.GET)
    public Account accountByUsername(String username) {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            System.out.println("Account with username " + username + " was not found.");
            return null;
        } else {
            return account;
        }
    }

    @RequestMapping(value = "/accountByEmail", method = RequestMethod.GET)
    public Account accountByEmail(String email) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            System.out.println("Account with email " + email + " was not found.");
            return null;
        } else {
            return account;
        }
    }

    @RequestMapping(value = "/addType", method = RequestMethod.POST)
    public Account addType(String id, String type) {
        Account account = accountRepository.findById(id);
        if (account == null) {
            System.out.println("Account with id " + id + " was not found.");
            return null;
        } else if (Objects.equals(type, "lotGroup")) {
            LotGroup group = new LotGroup(account.getId());
            lotGroupRepository.save(group);
            account.addType(type, group.getId());
            accountRepository.save(account);
            return account;
        } else if (Objects.equals(type, "systems")) {
            Systems systems = new Systems(account.getId());
            systemsRepository.save(systems);
            account.addType(type, systems.getId());
            accountRepository.save(account);
            return account;
        } else {
            System.out.println("Type " + type + " not recognized.");
            return null;
        }
    }

    @RequestMapping(value = "/newAccount", method = RequestMethod.POST)
    public Account newAccount(String firstName, String lastName, String username, String password, String phone, String email) {
        Account account = new Account(firstName, lastName, username, password, phone, email);
        accountRepository.save(account);
        User user = new User(account.getId());
        userRepository.save(user);
        account.addType("user", user.getId());
        accountRepository.save(account);
        return account;
    }

    // USERS //
    @RequestMapping(value = "/listUsers", method = RequestMethod.GET)
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/userById", method = RequestMethod.GET)
    public User userById(String id) {
        User user = userRepository.findById(id);
        if (user == null) {
            System.out.println("User with id " + id + " was not found.");
            return null;
        } else {
            return user;
        }
    }

    // LOTGROUPS //
    @RequestMapping(value = "/listLotGroups", method = RequestMethod.GET)
    public List<LotGroup> listLotGroups() {
        return lotGroupRepository.findAll();
    }

    @RequestMapping(value = "/lotGroupById", method = RequestMethod.GET)
    public LotGroup lotGroupById(String id) {
        LotGroup group = lotGroupRepository.findById(id);
        if (group == null) {
            System.out.println("Group with id " + id + " was not found.");
            return null;
        } else {
            return group;
        }
    }

    @RequestMapping(value = "/lotGroupByLotId", method = RequestMethod.GET)
    public LotGroup lotGroupByLotId(String lotId) {
        LotGroup group = lotGroupRepository.findByLotId(lotId);
        if (group == null) {
            System.out.println("Group with lot id " + lotId + " was not found.");
            return null;
        } else {
            return group;
        }
    }

    // SYSTEMS //
    @RequestMapping(value = "/listSystems", method = RequestMethod.GET)
    public List<Systems> listSystems() {
        return systemsRepository.findAll();
    }

    @RequestMapping(value = "/systemsById", method = RequestMethod.GET)
    public Systems systemsById(String id) {
        Systems systems = systemsRepository.findById(id);
        if (systems == null) {
            System.out.println("Systems with id " + id + " was not found.");
            return null;
        } else {
            return systems;
        }
    }
}
