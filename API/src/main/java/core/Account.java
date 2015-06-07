package core;

import org.springframework.data.annotation.Id;

public class Account {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phone;
    private String email;
    private boolean isOwner;
    private boolean isAdmin;

}
