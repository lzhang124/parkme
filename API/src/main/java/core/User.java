package core;

import org.springframework.data.annotation.Id;

public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String type;

    public User(String firstName, String lastName, String username, String password, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.type = "General";
    };

    public String getId() {
        return id;
    };

    public String getFirstName() {
        return firstName;
    };

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    };

    public String getLastName() {
        return lastName;
    };

    public void setLastName(String lastName) {
        this.lastName = lastName;
    };

    public String getUsername() {
        return username;
    };

    public void setUsername(String username) {
        this.username = username;
    };

    public String getPassword() {
        return password;
    };

    public void setPassword(String password) {
        this.password = password;
    };

    public String getPhone() {
        return phone;
    };

    public void setPhone(String phone) {
        this.phone = phone;
    };

    public String getEmail() {
        return email;
    };

    public void setEmail(String email) {
        this.email = email;
    };

    public String getType() {
        return type;
    };

    public void setType(String type) {
        this.type = type;
    };

    @Override
    public String toString() {
        return "User [id=" + id +
                ", name=" + firstName + lastName + "]";
    };
}
