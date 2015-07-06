package core.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Space {
    private boolean reservable;
    private Object photo;

    private Map<Long, String> calendar;

    public Space() {}

    public Space(boolean reservable, List<Long> startTimes, List<Integer> durations) {
        if (reservable) {
            this.reservable = true;
            this.calendar = new HashMap<>();
            for (int i = 0; i < startTimes.size(); i++) {
                for (int j = 0; j < durations.get(i); j++) {
                    long time = startTimes.get(i) + j*3600000;
                    this.calendar.put(time, null);
                }
            }
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

    public void setCalendar(long start, int duration) {
        for (int i = 0; i < duration; i++) {
            long time = start + i*3600000;
            this.calendar.put(time, null);
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
