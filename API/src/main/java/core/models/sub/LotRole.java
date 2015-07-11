package core.models.sub;

public class LotRole {
    private String lotId;
    private String role;

    public LotRole(String lotId, String role) {
        this.lotId = lotId;
        this.role = role;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
