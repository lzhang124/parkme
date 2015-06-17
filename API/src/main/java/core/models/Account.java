package core.models;

import java.util.*;

import org.springframework.data.annotation.Id;

public class Account {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Object photo;

    private boolean parked;
    private boolean payed;
    private String creditCard;

    private Map<String, String> lots;
    private List<String> roles;

    public Account(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

        this.lots = new HashMap<>();
        this.roles = new ArrayList<>();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) {
        this.photo = photo;
    }

    public boolean isParked() {
        return parked;
    }

    public void setParked(boolean parked) {
        this.parked = parked;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public Map<String, String> getLots() {
        return lots;
    }

    public void addLot(String lotId, String role) {
        this.lots.put(lotId, role);
    }

    public void removeLot(String lotId) {
        this.lots.remove(lotId);
    }

    public List<String> getRoles() {
        return roles;
    }

    public void addRole(String role) {
        this.roles.add(role);
    }

    public void removeRole(String role) {
        this.roles.remove(role);
    }

    public boolean hasRole(String role) {
        return this.roles.contains(role);
    }

    @Override
    public String toString() {
        return "Account [id=" + id +
                ", name=" + firstName + lastName + "]";
    }
}
