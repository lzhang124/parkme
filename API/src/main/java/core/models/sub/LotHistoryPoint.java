package core.models.sub;

public class LotHistoryPoint {
    private long time;
    private int occupied;

    public LotHistoryPoint(long time, int occupied) {
        this.time = time;
        this.occupied = occupied;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getOccupied() {
        return occupied;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }
}
