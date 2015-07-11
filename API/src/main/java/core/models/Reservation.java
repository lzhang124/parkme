package core.models;

import org.springframework.data.annotation.Id;

public class Reservation {

    @Id
    private String id;

    private String accountId;
    private String firstName;
    private String lastName;
    private String lotId;
    private int space;
    private long start;
    private long end;
    private int duration;
    private String status;

    public Reservation() {}

    public Reservation(String accountId, String firstName, String lastName, String lotId, int space, long start, int duration) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.lotId = lotId;
        this.space = space;
        this.start = start;
        this.end = start + duration*3600000;
        this.duration = duration;
        this.status = "active";
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
