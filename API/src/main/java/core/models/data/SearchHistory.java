package core.models.data;

import java.util.ArrayList;
import java.util.List;

public class SearchHistory {
    private String accountId;
    private List<double[]> location;

    public SearchHistory() {}

    public SearchHistory(String accountId) {
        this.accountId = accountId;
        this.location = new ArrayList<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public List<double[]> getLocation() {
        return location;
    }

    public void addLocation(double[] location) {
        this.location.add(location);
    }
}
