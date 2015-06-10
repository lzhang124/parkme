package core.models;

import org.springframework.data.annotation.Id;

public class LotGroup {

    @Id
    private String id;

    private Account account;
    private ParkingLot lot;

    public LotGroup(Account account, ParkingLot lot) {
        this.account = account;
        this.lot = lot;

        account.addType("lotGroup", this);
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

    public ParkingLot getLot() {
        return lot;
    }

    public void setLot(ParkingLot lot) {
        this.lot = lot;
    }

    @Override
    public String toString() {
        return "LotGroup [lot=" + lot.getName();
    }
}
