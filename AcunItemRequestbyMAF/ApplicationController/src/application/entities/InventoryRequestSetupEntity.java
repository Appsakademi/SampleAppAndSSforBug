package application.entities;

public class InventoryRequestSetupEntity {
    
    private Long setupId, inTransitSubinventoryId, inTransitLocatorId;
    
    public InventoryRequestSetupEntity() {
        super();
    }

    public void setSetupId(Long setupId) {
        this.setupId = setupId;
    }

    public Long getSetupId() {
        return setupId;
    }

    public void setInTransitSubinventoryId(Long inTransitSubinventoryId) {
        this.inTransitSubinventoryId = inTransitSubinventoryId;
    }

    public Long getInTransitSubinventoryId() {
        return inTransitSubinventoryId;
    }

    public void setInTransitLocatorId(Long inTransitLocatorId) {
        this.inTransitLocatorId = inTransitLocatorId;
    }

    public Long getInTransitLocatorId() {
        return inTransitLocatorId;
    }
}
