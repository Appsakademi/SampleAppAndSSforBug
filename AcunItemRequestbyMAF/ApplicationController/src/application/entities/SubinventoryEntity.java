package application.entities;

import java.util.ArrayList;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class SubinventoryEntity {
    Long subinventoryId, organizationId;
    String secondaryInventoryName;
    ArrayList<LocatorEntity> locators = new ArrayList<LocatorEntity>();
    private PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);

    public SubinventoryEntity() {
        super();
    }

    public SubinventoryEntity(Long subinventoryId, Long organizationId, String secondaryInventoryName) {
        this.subinventoryId = subinventoryId;
        this.organizationId = organizationId;
        this.secondaryInventoryName = secondaryInventoryName;
    }

    public void setSubinventoryId(Long subinventoryId) {
        this.subinventoryId = subinventoryId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public Long getSubinventoryId() {
        return subinventoryId;
    }

    public void setSecondaryInventoryName(String secondaryInventoryName) {
        this.secondaryInventoryName = secondaryInventoryName;
    }

    public String getSecondaryInventoryName() {
        return secondaryInventoryName;
    }

    public void setLocators(ArrayList<LocatorEntity> locators) {
        ArrayList<LocatorEntity> oldLocators = this.locators;
        this.locators = locators;
        _propertyChangeSupport.firePropertyChange("locators", oldLocators, locators);
    }

    public ArrayList<LocatorEntity> getLocators() {
        return locators;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        _propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        _propertyChangeSupport.removePropertyChangeListener(l);
    }
}
