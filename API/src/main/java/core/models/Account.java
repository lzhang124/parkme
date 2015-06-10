package core.models;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

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
    private Map<String, Object> type;

    public Account(String firstName, String lastName, String username, String password, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.type = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map getType() {
        return type;
    }

    public boolean hasType(String type) {
        return Objects.equals(type, "lotGroup") && this.type.containsKey("lotGroup") ||
               Objects.equals(type, "systems") && this.type.containsKey("systems");
    }

    public void addType(String type, Object object) {
        this.type.put(type, object);
    }

    @Override
    public String toString() {
        return "Account [id=" + id +
                ", name=" + firstName + lastName + "]";
    }
}
