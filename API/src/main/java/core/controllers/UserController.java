package core.controllers;

import java.util.List;

import core.models.User;
import core.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class UserController {
    @Autowired
    private UserRepository repository;

    @RequestMapping(value = "/listUsers", method = RequestMethod.GET)
    public List<User> listUsers() {
        return repository.findAll();
    }

    @RequestMapping(value = "/userById", method = RequestMethod.GET)
    public User userById(String id) {
        User user = repository.findById(id);
        if (user == null) {
            System.out.println("User with id " + id + " was not found.");
            return null;
        } else {
            return user;
        }
    }
}
