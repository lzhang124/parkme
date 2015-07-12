package core.models.data;

import java.util.ArrayList;
import java.util.List;

public class SearchHistory {
    private String accountId;
    private List<double[]> locations;

    public SearchHistory() {}

    public SearchHistory(String accountId) {
        this.accountId = accountId;
        this.locations = new ArrayList<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public List<double[]> getLocations() {
        return locations;
    }

    public void addLocation(double latitude, double longitude) {
        this.locations.add(new double[] {longitude, latitude});
    }
}
