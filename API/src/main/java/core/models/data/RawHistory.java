package core.models.data;

import java.util.ArrayList;
import java.util.List;

public class RawHistory {

    private String accountId;
    private String lotId;
    private int space;
    private long date;
    private int duration;
    private String search;
    private List<String> events;

    public RawHistory() {}

    public RawHistory(String accountId, String lotId, int space, long date, int duration, String search) {
        this.accountId = accountId;
        this.lotId = lotId;
        this.space = space;
        this.date = date;
        this.duration = duration;
        this.search = search;
        this.events = new ArrayList<>();
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

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<String> getEvents() {
        return events;
    }

    public void addEvent(String event) {
        this.events.add(event);
    }
}
