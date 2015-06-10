package core.models;

import org.springframework.data.annotation.Id;

public class Systems {

    @Id
    private String id;

    private String accountId;

    public Systems(String accountId) {
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

        @Override
    public String toString() {
        return "Systems [id=" + id;
    }
}
