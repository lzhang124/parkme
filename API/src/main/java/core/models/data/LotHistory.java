package core.models.data;

import core.models.sub.LotHistoryPoint;

import java.util.ArrayList;
import java.util.List;

public class LotHistory {
    private String lotId;
    private List<LotHistoryPoint> history;

    public LotHistory() {}

    public LotHistory(String lotId) {
        this.lotId = lotId;
        this.history = new ArrayList<>();
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public List<LotHistoryPoint> getHistory() {
        return history;
    }

    public void addHistory(long date, int occupancy) {
        this.history.add(new LotHistoryPoint(date, occupancy));
    }
}