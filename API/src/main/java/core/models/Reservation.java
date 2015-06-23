package core.models;

public class Reservation {

    private String lotId;
    private int space;
    private long start;
    private int duration;

    public Reservation(String lotId, int space, long start, int duration) {
        this.lotId = lotId;
        this.space = space;
        this.start = start;
        this.duration = duration;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
