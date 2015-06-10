package core.models;

import org.springframework.data.annotation.Id;

public class User {

    @Id
    private String id;

    private String accountId;
    private boolean parked;
    private boolean payed;

    public User(String accountId) {
        this.accountId = accountId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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
        return "User [id=" + id;
    }
}
