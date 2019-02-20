package application.helper;

import application.entities.ItemRequestDistributionEntity;

public class InventoryTransactionEntityToJson {
    
    public static String getJson(ItemRequestDistributionEntity item, String itemNumber, Long orgid, String date, 
                                 String intransitSubinv, String intransitLoc, String type){
        
    StringBuffer sb = new StringBuffer();
    sb.append("{");
    
    if(type.equals("checkin")){
        sb.append("\"Item\":\"");
        sb.append(itemNumber+"\",");
        
        sb.append("\"OrganizationId\":");
        sb.append(orgid+",");
        
        sb.append("\"Subinventory\":\"");
        sb.append(intransitSubinv+"\",");
        
        sb.append("\"Locator\":\"");
        sb.append(intransitLoc+"\",");
        
        sb.append("\"TransactionDate\":\"");
        sb.append(date+"\",");
        
        sb.append("\"TransactionQuantity\":");
        sb.append(item.getTransactionQuantity()+",");
        
        sb.append("\"TransferSubinventory\":\"");
        sb.append(item.getRequestSubinventory()+"\",");
        
        sb.append("\"TransferLocatorName\":\"");
        sb.append(item.getRequestLocator()+"\",");
        
        sb.append("\"TransactionType\":\"Subinventory Transfer\",");
        
        sb.append("\"TransactionUnitOfMeasure\":\"");
        sb.append(item.getRequestUom()+"\"}");
    }else{     sb.append("\"Item\":\"");
     sb.append(itemNumber+"\",");
     
     sb.append("\"OrganizationId\":");
     sb.append(orgid+",");
     
     sb.append("\"Subinventory\":\"");
     sb.append(item.getSourceSubinventory()+"\",");
     
     sb.append("\"Locator\":\"");
     sb.append(item.getSourceLocator()+"\",");
     
     sb.append("\"TransactionDate\":\"");
     sb.append(date+"\",");
     
     sb.append("\"TransactionQuantity\":");
     sb.append(item.getTransactionQuantity()+",");
     
     sb.append("\"TransferSubinventory\":\"");
     sb.append(intransitSubinv+"\",");
     
     sb.append("\"TransferLocatorName\":\"");
     sb.append(intransitLoc+"\",");
     
     sb.append("\"TransactionType\":\"Subinventory Transfer\",");
     
     sb.append("\"TransactionUnitOfMeasure\":\"");
     sb.append(item.getRequestUom()+"\"}");
        
    }

    String jsonObject = sb.toString();     
                
    return jsonObject;
    
    }
}
