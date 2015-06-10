package core.models;

import org.springframework.data.annotation.Id;

public class LotGroup {

    @Id
    private String id;

    private String accountId;
    private String lotId;

    public LotGroup(String accountId) {
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

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    @Override
    public String toString() {
        return "LotGroup [id=" + id;
    }
}
