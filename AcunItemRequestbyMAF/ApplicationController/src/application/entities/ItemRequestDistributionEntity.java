package application.entities;

public class ItemRequestDistributionEntity {
    Long itemRequestId, itemRequestLine, itemDistributionLine, sourceSubinventoryId, sourceLocatorId, approvedQuantity, 
         organizationId, transactionQuantity;
    String requestLineStatus, requestUom, sourceSubinventory, sourceLocator, requestSubinventory, requestLocator, 
           subinventoriesLink, locatorsLink;

    public ItemRequestDistributionEntity() {
        super();
    }

    public ItemRequestDistributionEntity(Long itemRequestId, Long itemRequestLine, Long itemDistributionLine,
                                         Long sourceSubinventoryId, Long sourceLocatorId, Long approvedQuantity,
                                         Long organizationId,
                                         String requestLineStatus, String requestUom, String sourceSubinventory,
                                         String sourceLocator, String requestSubinventory, String requestLocator,
                                         String subinventoriesLink, String locatorsLink) {
        this.itemRequestId = itemRequestId;
        this.itemRequestLine = itemRequestLine;
        this.itemDistributionLine = itemDistributionLine;
        this.sourceSubinventoryId = sourceSubinventoryId;
        this.sourceLocatorId = sourceLocatorId;
        this.approvedQuantity = approvedQuantity;
        this.organizationId = organizationId;
        this.requestLineStatus = requestLineStatus;
        this.requestUom = requestUom;
        this.sourceSubinventory = sourceSubinventory;
        this.sourceLocator = sourceLocator;
        this.requestSubinventory = requestSubinventory;
        this.requestLocator = requestLocator;
        this.subinventoriesLink = subinventoriesLink;
        this.locatorsLink = locatorsLink;
    }

    public void setItemRequestId(Long itemRequestId) {
        this.itemRequestId = itemRequestId;
    }

    public Long getItemRequestId() {
        return itemRequestId;
    }

    public void setItemRequestLine(Long itemRequestLine) {
        this.itemRequestLine = itemRequestLine;
    }

    public Long getItemRequestLine() {
        return itemRequestLine;
    }

    public void setItemDistributionLine(Long itemDistributionLine) {
        this.itemDistributionLine = itemDistributionLine;
    }

    public Long getItemDistributionLine() {
        return itemDistributionLine;
    }

    public void setSourceSubinventoryId(Long sourceSubinventoryId) {
        this.sourceSubinventoryId = sourceSubinventoryId;
    }

    public Long getSourceSubinventoryId() {
        return sourceSubinventoryId;
    }

    public void setSourceLocatorId(Long sourceLocatorId) {
        this.sourceLocatorId = sourceLocatorId;
    }

    public Long getSourceLocatorId() {
        return sourceLocatorId;
    }

    public void setApprovedQuantity(Long approvedQuantity) {
        this.approvedQuantity = approvedQuantity;
    }

    public Long getApprovedQuantity() {
        return approvedQuantity;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setRequestLineStatus(String requestLineStatus) {
        this.requestLineStatus = requestLineStatus;
    }

    public String getRequestLineStatus() {
        return requestLineStatus;
    }

    public void setRequestUom(String requestUom) {
        this.requestUom = requestUom;
    }

    public String getRequestUom() {
        return requestUom;
    }

    public void setSourceSubinventory(String sourceSubinventory) {
        this.sourceSubinventory = sourceSubinventory;
    }

    public String getSourceSubinventory() {
        return sourceSubinventory;
    }

    public void setSourceLocator(String sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    public String getSourceLocator() {
        return sourceLocator;
    }

    public void setRequestSubinventory(String requestSubinventory) {
        this.requestSubinventory = requestSubinventory;
    }

    public String getRequestSubinventory() {
        return requestSubinventory;
    }

    public void setRequestLocator(String requestLocator) {
        this.requestLocator = requestLocator;
    }

    public String getRequestLocator() {
        return requestLocator;
    }

    public void setTransactionQuantity(Long transactionQuantity) {
        this.transactionQuantity = transactionQuantity;
    }

    public Long getTransactionQuantity() {
        return transactionQuantity;
    }

    public void setSubinventoriesLink(String subinventoriesLink) {
        this.subinventoriesLink = subinventoriesLink;
    }

    public String getSubinventoriesLink() {
        return subinventoriesLink;
    }

    public void setLocatorsLink(String locatorsLink) {
        this.locatorsLink = locatorsLink;
    }

    public String getLocatorsLink() {
        return locatorsLink;
    }
}
