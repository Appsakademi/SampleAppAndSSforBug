package application.entities;

import java.util.ArrayList;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class ItemRequestHeaderEntity {

    private Long ItemRequestId, Orgid, Subinvid, RequestedLocatorId;
    private String ItemRequestDesc, ItemRequiredDate, ItemRequestStatus, ParentProject, PersonId, RequestedInvOrg, 
        RequestedSubinventory, RequestedLocator, DetailsLink;
    private ArrayList<ItemRequestDetailEntity> details;
    private PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);

    public ItemRequestHeaderEntity() {
        super();
    }

    public ItemRequestHeaderEntity(Long ItemRequestId, Long Orgid, Long Subinvid, Long RequestedLocatorId,
                                   String ItemRequestDesc, String ItemRequiredDate, String ItemRequestStatus,
                                   String ParentProject, String PersonId, String RequestedInvOrg,
                                   String RequestedSubinventory, String RequestedLocator, String DetailsLink,
                                   ArrayList<ItemRequestDetailEntity> details) {
        this.ItemRequestId = ItemRequestId;
        this.Orgid = Orgid;
        this.Subinvid = Subinvid;
        this.RequestedLocatorId = RequestedLocatorId;
        this.ItemRequestDesc = ItemRequestDesc;
        this.ItemRequiredDate = ItemRequiredDate;
        this.ItemRequestStatus = ItemRequestStatus;
        this.ParentProject = ParentProject;
        this.PersonId = PersonId;
        this.RequestedInvOrg = RequestedInvOrg;
        this.RequestedSubinventory = RequestedSubinventory;
        this.RequestedLocator = RequestedLocator;
        this.DetailsLink = DetailsLink;
        this.details = details;
    }

    public void setItemRequestId(Long ItemRequestId) {
        this.ItemRequestId = ItemRequestId;
    }

    public Long getItemRequestId() {
        return ItemRequestId;
    }

    public void setOrgid(Long Orgid) {
        this.Orgid = Orgid;
    }

    public Long getOrgid() {
        return Orgid;
    }

    public void setSubinvid(Long Subinvid) {
        this.Subinvid = Subinvid;
    }

    public Long getSubinvid() {
        return Subinvid;
    }

    public void setRequestedLocatorId(Long RequestedLocatorId) {
        this.RequestedLocatorId = RequestedLocatorId;
    }

    public Long getRequestedLocatorId() {
        return RequestedLocatorId;
    }

    public void setItemRequestDesc(String ItemRequestDesc) {
        this.ItemRequestDesc = ItemRequestDesc;
    }

    public String getItemRequestDesc() {
        return ItemRequestDesc;
    }

    public void setItemRequiredDate(String ItemRequiredDate) {
        this.ItemRequiredDate = ItemRequiredDate;
    }

    public String getItemRequiredDate() {
        return ItemRequiredDate;
    }

    public void setItemRequestStatus(String ItemRequestStatus) {
        this.ItemRequestStatus = ItemRequestStatus;
    }

    public String getItemRequestStatus() {
        return ItemRequestStatus;
    }

    public void setParentProject(String ParentProject) {
        this.ParentProject = ParentProject;
    }

    public String getParentProject() {
        return ParentProject;
    }

    public void setPersonId(String PersonId) {
        this.PersonId = PersonId;
    }

    public String getPersonId() {
        return PersonId;
    }

    public void setRequestedInvOrg(String RequestedInvOrg) {
        this.RequestedInvOrg = RequestedInvOrg;
    }

    public String getRequestedInvOrg() {
        return RequestedInvOrg;
    }

    public void setRequestedSubinventory(String RequestedSubinventory) {
        this.RequestedSubinventory = RequestedSubinventory;
    }

    public String getRequestedSubinventory() {
        return RequestedSubinventory;
    }

    public void setRequestedLocator(String RequestedLocator) {
        this.RequestedLocator = RequestedLocator;
    }

    public String getRequestedLocator() {
        return RequestedLocator;
    }

    public void setDetailsLink(String DetailsLink) {
        this.DetailsLink = DetailsLink;
    }

    public String getDetailsLink() {
        return DetailsLink;
    }

    public void setDetails(ArrayList<ItemRequestDetailEntity> details) {
        ArrayList<ItemRequestDetailEntity> oldDetails = this.details;
        this.details = details;
        _propertyChangeSupport.firePropertyChange("details", oldDetails, details);
    }

    public ArrayList<ItemRequestDetailEntity> getDetails() {
        return details;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        _propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        _propertyChangeSupport.removePropertyChangeListener(l);
    }
}
