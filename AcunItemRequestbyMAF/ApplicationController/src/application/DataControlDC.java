package application;

import application.entities.InventoryRequestSetupEntity;
import application.entities.ItemRequestDetailEntity;
import application.entities.ItemRequestDistributionEntity;
import application.entities.ItemRequestHeaderEntity;

import application.entities.LocatorEntity;

import application.entities.SubinventoryEntity;

import application.helper.InventoryTransactionEntityToJson;
import application.helper.ItemRequestDistributionEntityToJson;
import application.helper.JsonArrayToInventoryRequestSetupArray;
import application.helper.JsonArrayToItemRequestDetailArray;
import application.helper.JsonArrayToItemRequestDistributionArray;
import application.helper.JsonArrayToItemRequestHeaderArray;

import application.helper.JsonArrayToLocatorArray;
import application.helper.JsonArrayToSubinventoryArray;

import application.uris.URIs;

import application.util.RestCallerUtil;

import application.util.RestCallerUtilForSaas;

import java.util.ArrayList;

import java.util.Calendar;

import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.api.JSONBeanSerializationHelper;
import oracle.adfmf.framework.exception.AdfException;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.json.JSONObject;

public class DataControlDC {
    private PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);
    ArrayList<ItemRequestHeaderEntity> itemRequestHeaders = new ArrayList<ItemRequestHeaderEntity>();
    ArrayList<ItemRequestDetailEntity> itemRequestDetails = new ArrayList<ItemRequestDetailEntity>();
    ArrayList<ItemRequestDistributionEntity> itemRequestDistributions = new ArrayList<ItemRequestDistributionEntity>();
    ArrayList<LocatorEntity> locators = new ArrayList<LocatorEntity>();
    ArrayList<SubinventoryEntity> subinventories = new ArrayList<SubinventoryEntity>();
    private Long globalRequestId, globalRequestLine, globalDistributionLine;
    private String globalSubinventoriesLink = "", globalDistributionLink = "";
    String locatorVisible = "false";

    //For header filters
    private Long filterHeaderRequestId = Long.parseLong("0");
    private String headerFilterVisible = "false", filterHeaderRequestDesc = "", filterHeaderParentProject =
        "", filterHeaderRequestStatus = "", headerJsonArray = "";

    //For detail filters
    private Long filterDetailLineId = Long.parseLong("0"), filterDetailReqSubinv = Long.parseLong("0");
    private String filterDetailInvItemNumber = "", filterDetailLineStatus = "", detailFilterVisible =
        "false", detailJsonArray = "";

    //For distribution filters
    private Long filterDistributionLine = Long.parseLong("0");
    private String filterDistributionLocator = "", filterDistributionLineStatus = "", filterDistributionReqSubinv = "", 
            distributionFilterVisible = "false";

    public DataControlDC() {
        super();
    }

    public void RefreshHeaders() {
        String restUri = URIs.GetAllItemRequestHeaderURI();
        RestCallerUtil util = new RestCallerUtil();
        String jsonArrayAsString = util.invokeREAD(restUri);
        this.headerJsonArray = jsonArrayAsString;
        ArrayList<ItemRequestHeaderEntity> tempList =
            JsonArrayToItemRequestHeaderArray.getHeadersArray(jsonArrayAsString);
        setItemRequestHeaders(tempList);
    }

    public void FilterHeaders() {
        ArrayList<ItemRequestHeaderEntity> tempList =
            JsonArrayToItemRequestHeaderArray.getHeadersArray(headerJsonArray);
        for (int i = 0; i < tempList.size(); i++) {
            Long id = Long.parseLong("0");
            String reqDesc = "", parentPro = "", reqStat = "";
            try {
                id = tempList.get(i).getItemRequestId();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                reqDesc = tempList.get(i).getItemRequestDesc();
                if (reqDesc == null) {
                    reqDesc = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                parentPro = tempList.get(i).getParentProject();
                if (parentPro == null) {
                    parentPro = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                reqStat = tempList.get(i).getItemRequestStatus();
                if (reqStat == null) {
                    reqStat = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!filterHeaderRequestId.equals(Long.parseLong("0"))) {
                if (!id.equals(filterHeaderRequestId)) {
                    tempList.remove(i);
                    i -= 1;
                    continue;
                }
            }
            if (!filterHeaderRequestDesc.equals("")) {
                if (!reqDesc.equalsIgnoreCase(filterHeaderRequestDesc)) {
                    tempList.remove(i);
                    i -= 1;
                    continue;
                }
            }
            if (!filterHeaderParentProject.equals("")) {
                if (!parentPro.equalsIgnoreCase(filterHeaderParentProject)) {
                    tempList.remove(i);
                    i -= 1;
                    continue;
                }
            }
            if (!filterHeaderRequestStatus.equals("")) {
                if (!reqStat.equalsIgnoreCase(filterHeaderRequestStatus)) {
                    tempList.remove(i);
                    i -= 1;
                    continue;
                }
            }
        }
        try {
            if (tempList.size() > 0) {
                setItemRequestHeaders(tempList);
            } else {
                throw new AdfException("No data found", AdfException.INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AdfException("No data found with these parameters", AdfException.INFO);
        }
    }

    public void ShowDetailPage() {
        Long requestId = (Long) AdfmfJavaUtilities.getELValue("#{pageFlowScope.itemRequestId}");
        this.globalRequestId = requestId;
        for (int i = 0; i < itemRequestHeaders.size(); i++) {
            ItemRequestHeaderEntity header = itemRequestHeaders.get(i);
            if (header.getItemRequestId() == requestId) {
                int size = header.getDetails().size();
                if (size < 1) {
                    String detailLink = header.getDetailsLink();
                    RestCallerUtil util = new RestCallerUtil();
                    String jsonArrayAsString = util.invokeREAD(detailLink);
                    this.detailJsonArray = jsonArrayAsString;
                    ArrayList<ItemRequestDetailEntity> tempList =
                        JsonArrayToItemRequestDetailArray.getDetailsArray(jsonArrayAsString);
                    itemRequestHeaders.get(i).setDetails(tempList);
                    setItemRequestDetails(itemRequestHeaders.get(i).getDetails());
                } else {
                    setItemRequestDetails(itemRequestHeaders.get(i).getDetails());
                }
                size = header.getDetails().size();
                fillSubinventories("/AppsSubinventoriesView");
                size = subinventories.size();
                break;
            }
        }
    }

    public void RefreshDetails() {
        for (int i = 0; i < itemRequestHeaders.size(); i++) {
            ItemRequestHeaderEntity header = itemRequestHeaders.get(i);
            if (header.getItemRequestId() == globalRequestId) {
                String detailLink = header.getDetailsLink();
                RestCallerUtil util = new RestCallerUtil();
                String jsonArrayAsString = util.invokeREAD(detailLink);
                ArrayList<ItemRequestDetailEntity> tempList =
                    JsonArrayToItemRequestDetailArray.getDetailsArray(jsonArrayAsString);
                itemRequestHeaders.get(i).setDetails(tempList);
                setItemRequestDetails(itemRequestHeaders.get(i).getDetails());
                break;
            }
        }
    }

    public void FilterDetails() {
        ArrayList<ItemRequestDetailEntity> tempList =
            JsonArrayToItemRequestDetailArray.getDetailsArray(detailJsonArray);
        for (int i = 0; i < tempList.size(); i++) {
            Long line = Long.parseLong("0"), reqSubinv = Long.parseLong("0");
            String reqItemNumber = "", reqLineStatus = "";
            line = tempList.get(i).getItemRequestLine();
            reqSubinv = tempList.get(i).getSubinvid();
            try {
                reqItemNumber = tempList.get(i).getItemNumber();
                if (reqItemNumber == null) {
                    reqItemNumber = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                reqLineStatus = tempList.get(i).getRequestLineStatus();
                if (reqLineStatus == null) {
                    reqLineStatus = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!filterDetailLineId.equals(Long.parseLong("0"))) {
                if (!line.equals(filterDetailLineId)) {
                    tempList.remove(i);
                    i -= 1;
                    continue;
                }
            }
            if (!filterDetailReqSubinv.equals(Long.parseLong("0"))) {
                if (!reqSubinv.equals(filterDetailReqSubinv)) {
                    tempList.remove(i);
                    i -= 1;
                    continue;
                }
            }
            if (!filterDetailInvItemNumber.equals("")) {
                if (!reqItemNumber.equalsIgnoreCase(filterDetailInvItemNumber)) {
                    tempList.remove(i);
                    i -= 1;
                    continue;
                }
            }
            if (!filterDetailLineStatus.equals("")) {
                if (!reqLineStatus.equalsIgnoreCase(filterDetailLineStatus)) {
                    tempList.remove(i);
                    i -= 1;
                    continue;
                }
            }
        }
        try {
            if (tempList.size() > 0) {
                setItemRequestDetails(tempList);
            } else {
                throw new AdfException("No data found", AdfException.INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AdfException("No data found with these parameters", AdfException.INFO);
        }
    }

    public void ShowDistributionPage() {
        try {
            Long requestLine = (Long) AdfmfJavaUtilities.getELValue("#{pageFlowScope.itemRequestLine}");
            this.globalRequestLine = requestLine;
            for (int i = 0; i < itemRequestHeaders.size(); i++) {
                ItemRequestHeaderEntity header = itemRequestHeaders.get(i);
                if (header.getItemRequestId() == globalRequestId) {
                    for (int j = 0; j < header.getDetails().size(); j++) {
                        ItemRequestDetailEntity detail = header.getDetails().get(j);
                        if (detail.getItemRequestLine() == globalRequestLine) {

                            this.globalDistributionLink = detail.getDistributionsLink();
                            int size = 0;

                            try {
                                size = detail.getDistributions().size();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (size < 1) {
                                String distributionsLink = detail.getDistributionsLink();
                                RestCallerUtil util = new RestCallerUtil();
                                String jsonArrayAsString = util.invokeREAD(distributionsLink);
                                ArrayList<ItemRequestDistributionEntity> tempList =
                                    JsonArrayToItemRequestDistributionArray.getDistributionsArray(jsonArrayAsString);
                                itemRequestHeaders.get(i)
                                                  .getDetails()
                                                  .get(j)
                                                  .setDistributions(tempList);
                                setItemRequestDistributions(itemRequestHeaders.get(i)
                                                                              .getDetails()
                                                                              .get(j)
                                                                              .getDistributions());
                            } else {
                                setItemRequestDistributions(itemRequestHeaders.get(i)
                                                                              .getDetails()
                                                                              .get(j)
                                                                              .getDistributions());
                            }
                            size = detail.getDistributions().size();
                            fillSubinventories("/AppsSubinventoriesView");
                            fillLocators("/AppsLocatorsView");
                            AdfmfContainerUtilities.invokeContainerJavaScriptFunction("entryPoint",
                                                                                      "adf.mf.api.amx.doNavigation",
                                                                                      new Object[] { "goDistribution" });
                            break;
                        }
                    }
                }
            }
        } catch (AdfException ae) {
            // TODO: Add catch code
            ae.printStackTrace();
            throw new AdfException("Please, first select a row after that you can swipe left for distributions.", AdfException.INFO);
        }
    }

    public void ShowDistributionPage2() {
        Long requestLine = (Long) AdfmfJavaUtilities.getELValue("#{pageFlowScope.itemRequestLine}");
        this.globalRequestLine = requestLine;
        for (int i = 0; i < itemRequestHeaders.size(); i++) {
            ItemRequestHeaderEntity header = itemRequestHeaders.get(i);
            if (header.getItemRequestId() == globalRequestId) {
                for (int j = 0; j < header.getDetails().size(); j++) {
                    ItemRequestDetailEntity detail = header.getDetails().get(j);
                    if (detail.getItemRequestLine() == globalRequestLine) {

                        this.globalDistributionLink = detail.getDistributionsLink();
                        int size = 0;

                        try {
                            size = detail.getDistributions().size();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (size < 1) {
                            String distributionsLink = detail.getDistributionsLink();
                            RestCallerUtil util = new RestCallerUtil();
                            String jsonArrayAsString = util.invokeREAD(distributionsLink);
                            ArrayList<ItemRequestDistributionEntity> tempList =
                                JsonArrayToItemRequestDistributionArray.getDistributionsArray(jsonArrayAsString);
                            itemRequestHeaders.get(i)
                                              .getDetails()
                                              .get(j)
                                              .setDistributions(tempList);
                            setItemRequestDistributions(itemRequestHeaders.get(i)
                                                                          .getDetails()
                                                                          .get(j)
                                                                          .getDistributions());
                        } else {
                            setItemRequestDistributions(itemRequestHeaders.get(i)
                                                                          .getDetails()
                                                                          .get(j)
                                                                          .getDistributions());
                        }
                        size = detail.getDistributions().size();
                        fillSubinventories("/AppsSubinventoriesView");
                        fillLocators("/AppsLocatorsView");
                        break;
                    }
                }
            }
        }
    }

    public void RefreshDistributions() {
        Long requestLine = (Long) AdfmfJavaUtilities.getELValue("#{pageFlowScope.itemRequestLine}");
        this.globalRequestLine = requestLine;
        for (int i = 0; i < itemRequestHeaders.size(); i++) {
            ItemRequestHeaderEntity header = itemRequestHeaders.get(i);
            if (header.getItemRequestId() == globalRequestId) {
                for (int j = 0; j < header.getDetails().size(); j++) {
                    ItemRequestDetailEntity detail = header.getDetails().get(j);
                    if (detail.getItemRequestLine() == globalRequestLine) {
                        int size = detail.getDistributions().size();
                        String distributionsLink = detail.getDistributionsLink();
                        RestCallerUtil util = new RestCallerUtil();
                        String jsonArrayAsString = util.invokeREAD(distributionsLink);
                        ArrayList<ItemRequestDistributionEntity> tempList =
                            JsonArrayToItemRequestDistributionArray.getDistributionsArray(jsonArrayAsString);
                        itemRequestHeaders.get(i)
                                          .getDetails()
                                          .get(j)
                                          .setDistributions(tempList);
                        setItemRequestDistributions(itemRequestHeaders.get(i)
                                                                      .getDetails()
                                                                      .get(j)
                                                                      .getDistributions());

                        size = detail.getDistributions().size();
                        AdfmfContainerUtilities.invokeContainerJavaScriptFunction("entryPoint",
                                                                                  "adf.mf.api.amx.doNavigation",
                                                                                  new Object[] { "goDistribution" });
                        break;
                    }
                }
            }
        }
    }

    public void FilterDistributions() {
        RestCallerUtil util = new RestCallerUtil();
        String jsonArrayAsString = util.invokeREAD(this.globalDistributionLink);
        ArrayList<ItemRequestDistributionEntity> tempList =
            JsonArrayToItemRequestDistributionArray.getDistributionsArray(jsonArrayAsString);
        for (int i = 0; i < tempList.size(); i++) {
            Long reqDistLine = Long.parseLong("0");
            String reqLineStatus = "", reqSubinv = "", reqLoc = "";
            reqDistLine = tempList.get(i).getItemRequestLine();
            reqSubinv = tempList.get(i).getRequestSubinventory();
            reqLoc = tempList.get(i).getRequestLocator();

            try {
                reqLineStatus = tempList.get(i).getRequestLineStatus();
                if (reqLineStatus == null) {
                    reqLineStatus = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                reqSubinv = tempList.get(i).getRequestSubinventory();
                if (reqSubinv == null) {
                    reqSubinv = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                reqLoc = tempList.get(i).getRequestLocator();
                if (reqLoc == null) {
                    reqLoc = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!filterDistributionLine.equals(Long.parseLong("0"))) {
                if (!reqDistLine.equals(filterDistributionLine)) {
                    tempList.remove(i);
                    i -= 1;
                    continue;
                }
            }
            if (!filterDistributionReqSubinv.equals("")) {
                if (!reqSubinv.equalsIgnoreCase(filterDistributionReqSubinv)) {
                    tempList.remove(i);
                    i -= 1;
                    continue;
                }
            }
            if (!filterDistributionLocator.equals("")) {
                if (!reqLoc.equalsIgnoreCase(filterDistributionLocator)) {
                    tempList.remove(i);
                    i -= 1;
                    continue;
                }
            }
            if (!filterDistributionLineStatus.equals("")) {
                if (!reqLineStatus.equalsIgnoreCase(filterDistributionLineStatus)) {
                    tempList.remove(i);
                    i -= 1;
                    continue;
                }
            }
        }
        try {
            if (tempList.size() > 0) {
                setItemRequestDistributions(tempList);
            } else {
                throw new AdfException("No data found", AdfException.INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AdfException("No data found with these parameters", AdfException.INFO);
        }
    }

    public void ShowDistributionEdit() {
        Long distributionLine = (Long) AdfmfJavaUtilities.getELValue("#{pageFlowScope.itemDistributionLine}");
        this.globalDistributionLine = distributionLine;
        for (int i = 0; i < itemRequestDistributions.size(); i++) {
            ItemRequestDistributionEntity distribution = itemRequestDistributions.get(i);
            if (distribution.getItemRequestId().equals(globalRequestId) &&
                distribution.getItemRequestLine().equals(globalRequestLine) &&
                distribution.getItemDistributionLine().equals(globalDistributionLine)) {
                String subinventoriesLink = distribution.getSubinventoriesLink();
                if (!subinventoriesLink.equals(globalSubinventoriesLink)) {
                    //These are for SubinventoriesView
                    this.globalSubinventoriesLink = subinventoriesLink;
                    RestCallerUtil util = new RestCallerUtil();
                    String jsonArrayAsString = util.invokeREAD(globalSubinventoriesLink);
                    ArrayList<SubinventoryEntity> tempList =
                        JsonArrayToSubinventoryArray.getSubinventoriesArray(jsonArrayAsString);

                    //These are for LocatorsView
                    String locatorLink = "/AppsLocatorsView?limit=999";
                    util = new RestCallerUtil();
                    jsonArrayAsString = util.invokeREAD(locatorLink);
                    ArrayList<LocatorEntity> allLocators = JsonArrayToLocatorArray.getLocatorsArray(jsonArrayAsString);

                    for (int j = 0; j < tempList.size(); j++) {
                        ArrayList<LocatorEntity> tempLocators = new ArrayList<LocatorEntity>();
                        SubinventoryEntity subinventory = tempList.get(j);
                        for (int k = 0; k < allLocators.size(); k++) {
                            LocatorEntity locator = allLocators.get(k);
                            if (subinventory.getOrganizationId().equals(locator.getOrganizationId()) &&
                                subinventory.getSubinventoryId().equals(locator.getSubinventoryId())) {
                                tempLocators.add(locator);
                            }
                        }
                        if (j == 0) {
                            setLocators(tempLocators);
                        }
                        subinventory.setLocators(tempLocators);
                    }
                    setSubinventories(tempList);
                    break;
                } else {
                    break;
                }
            }
        }
    }

    public void SubinventoryVCE() {
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction("entryPoint", "adf.mf.api.amx.doNavigation",
                                                                  new Object[] { "load" });
        String selectedSubinventory =
            AdfmfJavaUtilities.getELValue("#{bindings.sourceSubinventory.inputValue}").toString();
        int subinvIndex = 0;
        for (int i = 0; i < subinventories.size(); i++) {
            SubinventoryEntity subinventory = subinventories.get(i);
            if (subinventory.getSecondaryInventoryName().equals(selectedSubinventory)) {
                subinvIndex = i;
                setLocators(subinventory.getLocators());
                break;
            }
        }
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction("entryPoint", "adf.mf.api.amx.doNavigation",
                                                                  new Object[] { "__back" });
    }

    public void DetailCheckIn() {
        String response = "";
        try {
            Long requestLine = (Long) AdfmfJavaUtilities.getELValue("#{pageFlowScope.itemRequestLine}");

            RestCallerUtil util = new RestCallerUtil();
            //For RequestSetup
            String jsonArrayAsString = util.invokeREAD("/AppsInventoryRequestSetupView");
            ArrayList<InventoryRequestSetupEntity> setupList =
                JsonArrayToInventoryRequestSetupArray.getInventoryRequestSetupsArray(jsonArrayAsString);
            InventoryRequestSetupEntity setupObj = setupList.get(0);
            Long intransitSubinvId = setupObj.getInTransitSubinventoryId();
            Long intransitLocid = setupObj.getInTransitLocatorId();
            //For Subinventory
            jsonArrayAsString = util.invokeREAD("/AppsSubinventoriesView");
            ArrayList<SubinventoryEntity> tempList =
                JsonArrayToSubinventoryArray.getSubinventoriesArray(jsonArrayAsString);
            setSubinventories(tempList);
            //For Subinventory
            jsonArrayAsString = util.invokeREAD("/AppsLocatorsView");
            ArrayList<LocatorEntity> tempList2 = JsonArrayToLocatorArray.getLocatorsArray(jsonArrayAsString);
            setLocators(tempList2);
            String intransitSubinvCode = "", intransitLocator = "";

            for (int i = 0; i < subinventories.size(); i++) {
                if (intransitSubinvId.equals(subinventories.get(i).getSubinventoryId())) {
                    intransitSubinvCode = subinventories.get(i).getSecondaryInventoryName();
                    break;
                }
            }

            for (int i = 0; i < locators.size(); i++) {
                if (intransitLocid.equals(locators.get(i).getInventoryLocationId())) {
                    intransitLocator = locators.get(i).getLocator();
                    break;
                }
            }
            
            String itemNumber = "";Long orgid = Long.parseLong("0");
            
            for(int i=0; i<itemRequestDetails.size(); i++){
                if(requestLine.equals(itemRequestDetails.get(i).getItemRequestLine())){
                    itemNumber = itemRequestDetails.get(i).getItemNumber();
                    orgid = itemRequestDetails.get(i).getOrgid();
                    break;
                }
            }
            Calendar cln = Calendar.getInstance();
            ShowDistributionPage2();
            String date = cln.getTime().toString();
            int month = cln.get(Calendar.MONTH);
            String mnth = "0";
            if(month<10){
                mnth += month;
            }else{
                mnth = "" + month;
            }
            String correctedDate = cln.get(Calendar.YEAR) + "-" + mnth + "-" + cln.get(Calendar.DAY_OF_MONTH);
            
            String inventoryTransactionsRequestJson = "{\"transactionLines\":[";
            for (int i = 0; i < itemRequestDistributions.size(); i++) {
                if(i>0){
                    inventoryTransactionsRequestJson += ",";
                }
                ItemRequestDistributionEntity distribution = itemRequestDistributions.get(i);
                inventoryTransactionsRequestJson += InventoryTransactionEntityToJson.getJson(distribution, itemNumber, 
                                                orgid, correctedDate, intransitSubinvCode, intransitLocator, "checkin");
            }
            inventoryTransactionsRequestJson += "]}";
            RestCallerUtilForSaas saasutil = new RestCallerUtilForSaas();
            //For RequestSetup
            jsonArrayAsString = saasutil.invokeCREATE("/fscmRestApi/resources/11.13.18.05/inventoryTransactions", inventoryTransactionsRequestJson);
            response = jsonArrayAsString;
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
            throw new AdfException("Please, select a row", AdfException.INFO);
        }
    }

    public void DetailCheckOut() {
        String response = "";Long requestLine = Long.parseLong("0");
        try {
             requestLine = (Long) AdfmfJavaUtilities.getELValue("#{pageFlowScope.itemRequestLine}");
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
            throw new AdfException("Please, select a row", AdfException.INFO);
        }
        
            if (!requestLine.equals(Long.parseLong("0"))) {
            RestCallerUtil util = new RestCallerUtil();
            //For RequestSetup
            String jsonArrayAsString = util.invokeREAD("/AppsInventoryRequestSetupView");
            ArrayList<InventoryRequestSetupEntity> setupList =
                JsonArrayToInventoryRequestSetupArray.getInventoryRequestSetupsArray(jsonArrayAsString);
            InventoryRequestSetupEntity setupObj = setupList.get(0);
            Long intransitSubinvId = setupObj.getInTransitSubinventoryId();
            Long intransitLocid = setupObj.getInTransitLocatorId();
            //For Subinventory
            jsonArrayAsString = util.invokeREAD("/AppsSubinventoriesView");
            ArrayList<SubinventoryEntity> tempList =
                JsonArrayToSubinventoryArray.getSubinventoriesArray(jsonArrayAsString);
            setSubinventories(tempList);
            //For Subinventory
            jsonArrayAsString = util.invokeREAD("/AppsLocatorsView");
            ArrayList<LocatorEntity> tempList2 = JsonArrayToLocatorArray.getLocatorsArray(jsonArrayAsString);
            setLocators(tempList2);
            String intransitSubinvCode = "", intransitLocator = "";

            for (int i = 0; i < subinventories.size(); i++) {
                if (intransitSubinvId.equals(subinventories.get(i).getSubinventoryId())) {
                    intransitSubinvCode = subinventories.get(i).getSecondaryInventoryName();
                    break;
                }
            }

            for (int i = 0; i < locators.size(); i++) {
                if (intransitLocid.equals(locators.get(i).getInventoryLocationId())) {
                    intransitLocator = locators.get(i).getLocator();
                    break;
                }
            }

            String itemNumber = "";
            Long orgid = Long.parseLong("0");

            for (int i = 0; i < itemRequestDetails.size(); i++) {
                if (requestLine.equals(itemRequestDetails.get(i).getItemRequestLine())) {
                    itemNumber = itemRequestDetails.get(i).getItemNumber();
                    orgid = itemRequestDetails.get(i).getOrgid();
                    break;
                }
            }
            Calendar cln = Calendar.getInstance();
            ShowDistributionPage2();
            String date = cln.getTime().toString();
            int month = cln.get(Calendar.MONTH);
            String mnth = "0";
            if (month < 10) {
                mnth += month;
            } else {
                mnth = "" + month;
            }
            String correctedDate = cln.get(Calendar.YEAR) + "-" + mnth + "-" + cln.get(Calendar.DAY_OF_MONTH);

            String inventoryTransactionsRequestJson = "{\"transactionLines\":[";
            for (int i = 0; i < itemRequestDistributions.size(); i++) {
                if (i > 0) {
                    inventoryTransactionsRequestJson += ",";
                }
                ItemRequestDistributionEntity distribution = itemRequestDistributions.get(i);
                inventoryTransactionsRequestJson +=
                    InventoryTransactionEntityToJson.getJson(distribution, itemNumber, orgid, correctedDate,
                                                             intransitSubinvCode, intransitLocator, "checkout");
            }
            inventoryTransactionsRequestJson += "]}";
            RestCallerUtilForSaas saasutil = new RestCallerUtilForSaas();
            //For RequestSetup
            jsonArrayAsString =
                saasutil.invokeCREATE("/fscmRestApi/resources/11.13.18.05/inventoryTransactions",
                                      inventoryTransactionsRequestJson);
            response = jsonArrayAsString;
            }
    }

    public void SaveDistribution() {
        Long distributionLine = (Long) AdfmfJavaUtilities.getELValue("#{bindings.itemDistributionLine.inputValue}");
        for (int i = 0; i < itemRequestDistributions.size(); i++) {
            ItemRequestDistributionEntity distribution = itemRequestDistributions.get(i);
            if (distribution.getItemDistributionLine().equals(distributionLine)) {
                String sourceSubinv = distribution.getSourceSubinventory();
                String sourceLocator = distribution.getSourceLocator();

                for (int j = 0; j < subinventories.size(); j++) {
                    if (subinventories.get(j)
                                      .getSecondaryInventoryName()
                                      .equals(sourceSubinv)) {
                        distribution.setSourceSubinventoryId(subinventories.get(j).getSubinventoryId());
                        break;
                    }
                }

                for (int j = 0; j < locators.size(); j++) {
                    if (locators.get(j)
                                .getLocator()
                                .equals(sourceLocator)) {
                        distribution.setSourceLocatorId(locators.get(j).getInventoryLocationId());
                        break;
                    }
                }

                try {
                    //distribution.setItemDistributionLine(new Long(4));
                    String jsonString = ItemRequestDistributionEntityToJson.getJson(distribution);
                    String restUri = globalDistributionLink;
                    String restUriForPost = restUri;
                    restUri += "/" + distributionLine;
                    RestCallerUtil util = new RestCallerUtil();
                    String temp = util.invokeDELETE(restUri);
                    String jsonArrayAsString = util.invokeCREATE(restUriForPost, jsonString);
                    ShowDistributionPage();
                    throw new AdfException("Changes successfully saved.", AdfException.INFO);
                } catch (Exception e) {
                    String message = e.getMessage() + e.getCause();
                    throw new AdfException(message, AdfException.INFO);
                }
            }
        }
    }

    public void fillSubinventories(String endpoint) {
        RestCallerUtil util = new RestCallerUtil();
        String jsonArrayAsString = util.invokeREAD(endpoint);
        ArrayList<SubinventoryEntity> tempList = JsonArrayToSubinventoryArray.getSubinventoriesArray(jsonArrayAsString);
        setSubinventories(tempList);
    }

    public void fillLocators(String endpoint) {
        RestCallerUtil util = new RestCallerUtil();
        String jsonArrayAsString = util.invokeREAD(endpoint);
        ArrayList<LocatorEntity> tempList = JsonArrayToLocatorArray.getLocatorsArray(jsonArrayAsString);
        setLocators(tempList);
    }


    /***************************************************/

    /************** GETTERS AND SETTERS ****************/

    public void setItemRequestHeaders(ArrayList<ItemRequestHeaderEntity> itemRequestHeaders) {
        ArrayList<ItemRequestHeaderEntity> oldItemRequestHeaders = this.itemRequestHeaders;
        this.itemRequestHeaders = itemRequestHeaders;
        _propertyChangeSupport.firePropertyChange("itemRequestHeaders", oldItemRequestHeaders, itemRequestHeaders);
    }

    public ArrayList<ItemRequestHeaderEntity> getItemRequestHeaders() {
        if (itemRequestHeaders.size() < 1) {
            String restUri = URIs.GetAllItemRequestHeaderURI();
            RestCallerUtil util = new RestCallerUtil();
            String jsonArrayAsString = util.invokeREAD(restUri);
            this.headerJsonArray = jsonArrayAsString;
            ArrayList<ItemRequestHeaderEntity> tempList =
                JsonArrayToItemRequestHeaderArray.getHeadersArray(jsonArrayAsString);
            setItemRequestHeaders(tempList);
        }
        return itemRequestHeaders;
    }

    public void setItemRequestDetails(ArrayList<ItemRequestDetailEntity> itemRequestDetails) {
        ArrayList<ItemRequestDetailEntity> oldItemRequestDetails = this.itemRequestDetails;
        this.itemRequestDetails = itemRequestDetails;
        _propertyChangeSupport.firePropertyChange("itemRequestDetails", oldItemRequestDetails, itemRequestDetails);
    }

    public ArrayList<ItemRequestDetailEntity> getItemRequestDetails() {
        return itemRequestDetails;
    }

    public void setItemRequestDistributions(ArrayList<ItemRequestDistributionEntity> itemRequestDistributions) {
        ArrayList<ItemRequestDistributionEntity> oldItemRequestDistributions = this.itemRequestDistributions;
        this.itemRequestDistributions = itemRequestDistributions;
        _propertyChangeSupport.firePropertyChange("itemRequestDistributions", oldItemRequestDistributions,
                                                  itemRequestDistributions);
    }

    public ArrayList<ItemRequestDistributionEntity> getItemRequestDistributions() {
        return itemRequestDistributions;
    }

    public void setLocators(ArrayList<LocatorEntity> locators) {
        ArrayList<LocatorEntity> oldLocators = this.locators;
        this.locators = locators;
        _propertyChangeSupport.firePropertyChange("locators", oldLocators, locators);
    }

    public ArrayList<LocatorEntity> getLocators() {
        return locators;
    }

    public void setSubinventories(ArrayList<SubinventoryEntity> subinventories) {
        ArrayList<SubinventoryEntity> oldSubinventories = this.subinventories;
        this.subinventories = subinventories;
        _propertyChangeSupport.firePropertyChange("subinventories", oldSubinventories, subinventories);
    }

    public ArrayList<SubinventoryEntity> getSubinventories() {
        return subinventories;
    }

    public void setLocatorVisible(String locatorVisible) {
        String oldLocatorVisible = this.locatorVisible;
        this.locatorVisible = locatorVisible;
        _propertyChangeSupport.firePropertyChange("locatorVisible", oldLocatorVisible, locatorVisible);
    }

    public String getLocatorVisible() {
        return locatorVisible;
    }

    public void setHeaderFilterVisible(String headerFilterVisible) {
        String oldHeaderFilterVisible = this.headerFilterVisible;
        this.headerFilterVisible = headerFilterVisible;
        _propertyChangeSupport.firePropertyChange("headerFilterVisible", oldHeaderFilterVisible, headerFilterVisible);
    }

    public String getHeaderFilterVisible() {
        return headerFilterVisible;
    }

    public void setFilterHeaderRequestId(Long filterHeaderRequestId) {
        Long oldFilterHeaderRequestId = this.filterHeaderRequestId;
        this.filterHeaderRequestId = filterHeaderRequestId;
        _propertyChangeSupport.firePropertyChange("filterHeaderRequestId", oldFilterHeaderRequestId,
                                                  filterHeaderRequestId);
    }

    public Long getFilterHeaderRequestId() {
        return filterHeaderRequestId;
    }

    public void setFilterHeaderRequestDesc(String filterHeaderRequestDesc) {
        String oldFilterHeaderRequestDesc = this.filterHeaderRequestDesc;
        this.filterHeaderRequestDesc = filterHeaderRequestDesc;
        _propertyChangeSupport.firePropertyChange("filterHeaderRequestDesc", oldFilterHeaderRequestDesc,
                                                  filterHeaderRequestDesc);
    }

    public String getFilterHeaderRequestDesc() {
        return filterHeaderRequestDesc;
    }

    public void setFilterHeaderParentProject(String filterHeaderParentProject) {
        String oldFilterHeaderParentProject = this.filterHeaderParentProject;
        this.filterHeaderParentProject = filterHeaderParentProject;
        _propertyChangeSupport.firePropertyChange("filterHeaderParentProject", oldFilterHeaderParentProject,
                                                  filterHeaderParentProject);
    }

    public String getFilterHeaderParentProject() {
        return filterHeaderParentProject;
    }

    public void setFilterHeaderRequestStatus(String filterHeaderRequestStatus) {
        String oldFilterHeaderRequestStatus = this.filterHeaderRequestStatus;
        this.filterHeaderRequestStatus = filterHeaderRequestStatus;
        _propertyChangeSupport.firePropertyChange("filterHeaderRequestStatus", oldFilterHeaderRequestStatus,
                                                  filterHeaderRequestStatus);
    }

    public String getFilterHeaderRequestStatus() {
        return filterHeaderRequestStatus;
    }

    public void setFilterDetailLineId(Long filterDetailLineId) {
        Long oldFilterDetailLineId = this.filterDetailLineId;
        this.filterDetailLineId = filterDetailLineId;
        _propertyChangeSupport.firePropertyChange("filterDetailLineId", oldFilterDetailLineId, filterDetailLineId);
    }

    public Long getFilterDetailLineId() {
        return filterDetailLineId;
    }

    public void setFilterDetailInvItemNumber(String filterDetailInvItemNumber) {
        String oldFilterDetailInvItemNumber = this.filterDetailInvItemNumber;
        this.filterDetailInvItemNumber = filterDetailInvItemNumber;
        _propertyChangeSupport.firePropertyChange("filterDetailInvItemNumber", oldFilterDetailInvItemNumber,
                                                  filterDetailInvItemNumber);
    }

    public String getFilterDetailInvItemNumber() {
        return filterDetailInvItemNumber;
    }

    public void setFilterDetailReqSubinv(Long filterDetailReqSubinv) {
        Long oldFilterDetailReqSubinv = this.filterDetailReqSubinv;
        this.filterDetailReqSubinv = filterDetailReqSubinv;
        _propertyChangeSupport.firePropertyChange("filterDetailReqSubinv", oldFilterDetailReqSubinv,
                                                  filterDetailReqSubinv);
    }

    public Long getFilterDetailReqSubinv() {
        return filterDetailReqSubinv;
    }

    public void setFilterDetailLineStatus(String filterDetailLineStatus) {
        String oldFilterDetailLineStatus = this.filterDetailLineStatus;
        this.filterDetailLineStatus = filterDetailLineStatus;
        _propertyChangeSupport.firePropertyChange("filterDetailLineStatus", oldFilterDetailLineStatus,
                                                  filterDetailLineStatus);
    }

    public String getFilterDetailLineStatus() {
        return filterDetailLineStatus;
    }

    public void setDetailFilterVisible(String detailFilterVisible) {
        String oldDetailFilterVisible = this.detailFilterVisible;
        this.detailFilterVisible = detailFilterVisible;
        _propertyChangeSupport.firePropertyChange("detailFilterVisible", oldDetailFilterVisible, detailFilterVisible);
    }

    public String getDetailFilterVisible() {
        return detailFilterVisible;
    }

    public void setFilterDistributionLine(Long filterDistributionLine) {
        Long oldFilterDistributionLine = this.filterDistributionLine;
        this.filterDistributionLine = filterDistributionLine;
        _propertyChangeSupport.firePropertyChange("filterDistributionLine", oldFilterDistributionLine,
                                                  filterDistributionLine);
    }

    public Long getFilterDistributionLine() {
        return filterDistributionLine;
    }

    public void setFilterDistributionLocator(String filterDistributionLocator) {
        String oldFilterDistributionLocator = this.filterDistributionLocator;
        this.filterDistributionLocator = filterDistributionLocator;
        _propertyChangeSupport.firePropertyChange("filterDistributionLocator", oldFilterDistributionLocator,
                                                  filterDistributionLocator);
    }

    public String getFilterDistributionLocator() {
        return filterDistributionLocator;
    }

    public void setFilterDistributionLineStatus(String filterDistributionLineStatus) {
        String oldFilterDistributionLineStatus = this.filterDistributionLineStatus;
        this.filterDistributionLineStatus = filterDistributionLineStatus;
        _propertyChangeSupport.firePropertyChange("filterDistributionLineStatus", oldFilterDistributionLineStatus,
                                                  filterDistributionLineStatus);
    }

    public String getFilterDistributionLineStatus() {
        return filterDistributionLineStatus;
    }

    public void setDistributionFilterVisible(String distributionFilterVisible) {
        String oldDistributionFilterVisible = this.distributionFilterVisible;
        this.distributionFilterVisible = distributionFilterVisible;
        _propertyChangeSupport.firePropertyChange("distributionFilterVisible", oldDistributionFilterVisible,
                                                  distributionFilterVisible);
    }

    public String getDistributionFilterVisible() {
        return distributionFilterVisible;
    }

    public void setFilterDistributionReqSubinv(String filterDistributionReqSubinv) {
        String oldFilterDistributionReqSubinv = this.filterDistributionReqSubinv;
        this.filterDistributionReqSubinv = filterDistributionReqSubinv;
        _propertyChangeSupport.firePropertyChange("filterDistributionReqSubinv", oldFilterDistributionReqSubinv,
                                                  filterDistributionReqSubinv);
    }

    public String getFilterDistributionReqSubinv() {
        return filterDistributionReqSubinv;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        _propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        _propertyChangeSupport.removePropertyChangeListener(l);
    }
}
