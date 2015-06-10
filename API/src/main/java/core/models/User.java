package core.models;

import org.springframework.data.annotation.Id;

public class User {

    @Id
    private String id;

    private Account account;
    private boolean isParked;
    private boolean hasPayed;

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

    public void setAccount() {
        this.account = account;
    }

    public boolean isParked() {
        return isParked;
    }

    public void setIsParked(boolean isParked) {
        this.isParked = isParked;
    }

    public boolean isHasPayed() {
        return hasPayed;
    }

    public void setHasPayed(boolean hasPayed) {
        this.hasPayed = hasPayed;
    }

    @Override
    public String toString() {
        return "User [" + account.getFirstName() + account.getLastName();
    }
}
