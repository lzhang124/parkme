package core.models.data;

import core.models.sub.LotHistoryPoint;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;

public class LotHistory {

    @Id
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

    public void addHistory(long date, int occupied) {
        this.history.add(new LotHistoryPoint(date, occupied));
    }
}
