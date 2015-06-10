package core.models;

import org.springframework.data.annotation.Id;

public class User {

    @Id
    private String id;

    private Account account;
    private String creditCard;
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

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
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
