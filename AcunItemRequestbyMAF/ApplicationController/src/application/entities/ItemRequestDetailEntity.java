package application.entities;

import java.util.ArrayList;

public class ItemRequestDetailEntity {

    private Long ItemRequestId, ItemRequestLine, RequestQuantity, Subinvid, RequestLocatorId, orgid;
    private String ItemNumber, RequestUom, RequestSubinventory, RequestLocator, ParentProject, RequestLineStatus, DistributionsLink, RequestDetailNote;
    private ArrayList<ItemRequestDistributionEntity> distributions;

    public ItemRequestDetailEntity() {
        super();
    }

    public ItemRequestDetailEntity(Long ItemRequestId, Long ItemRequestLine, Long RequestQuantity,
                                   Long Subinvid, Long RequestLocatorId, String ItemNumber, String RequestUom,
                                   String RequestSubinventory, String RequestLocator, String ParentProject, String RequestDetailNote,
                                   String RequestLineStatus, String DistributionsLink, ArrayList<ItemRequestDistributionEntity> distributions) {
        this.ItemRequestId = ItemRequestId;
        this.ItemRequestLine = ItemRequestLine;
        this.RequestQuantity = RequestQuantity;
        this.Subinvid = Subinvid;
        this.RequestLocatorId = RequestLocatorId;
        this.ItemNumber = ItemNumber;
        this.RequestUom = RequestUom;
        this.RequestSubinventory = RequestSubinventory;
        this.RequestLocator = RequestLocator;
        this.ParentProject = ParentProject;
        this.RequestDetailNote = RequestDetailNote;
        this.RequestLineStatus = RequestLineStatus;
        this.DistributionsLink = DistributionsLink;
        this.distributions = distributions;
    }

    public void setItemRequestId(Long ItemRequestId) {
        this.ItemRequestId = ItemRequestId;
    }

    public Long getItemRequestId() {
        return ItemRequestId;
    }

    public void setItemRequestLine(Long ItemRequestLine) {
        this.ItemRequestLine = ItemRequestLine;
    }

    public Long getItemRequestLine() {
        return ItemRequestLine;
    }

    public void setRequestQuantity(Long RequestQuantity) {
        this.RequestQuantity = RequestQuantity;
    }

    public Long getRequestQuantity() {
        return RequestQuantity;
    }

    public void setSubinvid(Long Subinvid) {
        this.Subinvid = Subinvid;
    }

    public Long getSubinvid() {
        return Subinvid;
    }

    public void setRequestLocatorId(Long RequestLocatorId) {
        this.RequestLocatorId = RequestLocatorId;
    }

    public Long getRequestLocatorId() {
        return RequestLocatorId;
    }

    public void setItemNumber(String ItemNumber) {
        this.ItemNumber = ItemNumber;
    }

    public String getItemNumber() {
        return ItemNumber;
    }

    public void setRequestUom(String RequestUom) {
        this.RequestUom = RequestUom;
    }

    public String getRequestUom() {
        return RequestUom;
    }

    public void setRequestSubinventory(String RequestSubinventory) {
        this.RequestSubinventory = RequestSubinventory;
    }

    public String getRequestSubinventory() {
        return RequestSubinventory;
    }

    public void setRequestLocator(String RequestLocator) {
        this.RequestLocator = RequestLocator;
    }

    public String getRequestLocator() {
        return RequestLocator;
    }

    public void setParentProject(String ParentProject) {
        this.ParentProject = ParentProject;
    }

    public String getParentProject() {
        return ParentProject;
    }

    public void setDistributionsLink(String DistributionsLink) {
        this.DistributionsLink = DistributionsLink;
    }

    public String getDistributionsLink() {
        return DistributionsLink;
    }

    public void setRequestLineStatus(String RequestLineStatus) {
        this.RequestLineStatus = RequestLineStatus;
    }

    public String getRequestLineStatus() {
        return RequestLineStatus;
    }

    public void setDistributions(ArrayList<ItemRequestDistributionEntity> distributions) {
        this.distributions = distributions;
    }

    public ArrayList<ItemRequestDistributionEntity> getDistributions() {
        return distributions;
    }

    public void setRequestDetailNote(String RequestDetailNote) {
        this.RequestDetailNote = RequestDetailNote;
    }

    public String getRequestDetailNote() {
        return RequestDetailNote;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }
}
