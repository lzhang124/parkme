package core.models;

import java.util.HashMap;
import java.util.Map;

public class Space {
    private boolean available;
    private boolean reservable;
    private Object photo;

    private Map<Integer, Boolean> calendar;

    public Space() {}

    public Space(boolean reservable) {
        this.available = true;
        if (reservable) {
            this.calendar = new HashMap<>();
        }
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isReservable() {
        return reservable;
    }

    public void setReservable(boolean reservable) {
        this.reservable = reservable;
    }

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) {
        this.photo = photo;
    }

    public Map<Integer, Boolean> getCalendar() {
        return calendar;
    }

    public void setCalendar(Map<Integer, Boolean> calendar) {
        this.calendar = calendar;
    }

}