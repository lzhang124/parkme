package core.models.data;

import java.util.ArrayList;
import java.util.List;

import core.models.Pair;
import org.springframework.data.annotation.Id;

public class LotHistory {

    @Id
    private String id;

    private List<Pair<Long, Integer>> history;

    public LotHistory() {
        this.history = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Pair<Long, Integer>> getHistory() {
        return history;
    }

    public void addHistory(long date, int occupancy) {
        this.history.add(new Pair<>(date, occupancy));
    }
}