package application.entities;

public class LocatorEntity {
    Long inventoryLocationId, subinventoryId, organizationId;
    String locator;
    public LocatorEntity() {
        super();
    }
    
    public LocatorEntity(Long inventoryLocationId, Long subinventoryId, Long organizationId, String locator){
        this.inventoryLocationId = inventoryLocationId;
        this.subinventoryId = subinventoryId;
        this.organizationId = organizationId;
        this.locator = locator;
    }

    public void setInventoryLocationId(Long inventoryLocationId) {
        this.inventoryLocationId = inventoryLocationId;
    }

    public Long getInventoryLocationId() {
        return inventoryLocationId;
    }

    public void setLocator(String locator) {
        this.locator = locator;
    }

    public String getLocator() {
        return locator;
    }

    public void setSubinventoryId(Long subinventoryId) {
        this.subinventoryId = subinventoryId;
    }

    public Long getSubinventoryId() {
        return subinventoryId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }
}
