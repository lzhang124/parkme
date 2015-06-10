package core.models;

import org.springframework.data.annotation.Id;

public class User {

    @Id
    private String id;

    private Account account;
    private boolean parked;
    private boolean payed;

    public User(Account account) {
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isParked() {
        return parked;
    }

    public void setParked(boolean parked) {
        this.parked = parked;
    }

    public void changeParked() {
        this.parked = !this.parked;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public void changePayed() {
        this.payed = !this.payed;
    }

    @Override
    public String toString() {
        return "User [" + account.getFirstName() + account.getLastName();
    }
}
