package core.models;

import org.springframework.data.annotation.Id;

public class Systems {

    @Id
    private String id;

    private Account account;

    public Systems(Account account) {
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

    @Override
    public String toString() {
        return "Systems [" + account.getFirstName() + account.getLastName();
    }
}
