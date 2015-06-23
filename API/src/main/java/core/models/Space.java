package core.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Space {
    private boolean reservable;
    private Object photo;

    private Map<Long, String> calendar;

    public Space() {}

    public Space(boolean reservable) {
        if (reservable) {
            this.reservable = true;
            this.calendar = new HashMap<>();
        }
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

    public Map<Long, String> getCalendar() {
        return calendar;
    }

    public void setCalendar(List<Pair<Long, Integer>> intervals) {
        for (Pair interval : intervals) {
            long start = (long) interval.getL();
            int duration = (int) interval.getR();
            for (int i = 0; i < duration; i++) {
                long time = start + i*3600000;
                this.calendar.put(time, null);
            }
        }
    }

    public void addReservation(long start, int duration, String id) {
        for (int i = 0; i < duration; i++) {
            long time = start + i * 3600000;
            if (!this.calendar.containsKey(time)) {
                break;
            } else if (this.calendar.get(time) != null) {
                break;
            }
            this.calendar.put(time, id);
        }
    }

    public void clearReservations() {
        for (long time : calendar.keySet()) {
            calendar.put(time, null);
        }
    }

    public boolean isAvailable(long start, int duration) {
        if (!reservable) {
            System.out.println("Space is not reservable");
            return false;
        } else {
            for (int i = 0; i < duration; i++) {
                long time = start + i*3600000;
                if (!this.calendar.containsKey(time)) {
                    System.out.println("This time is not available.");
                    return false;
                } else if (this.calendar.get(time) != null) {
                    System.out.println("This time is already taken.");
                    return false;
                }
            }
            return true;
        }
    }
}
