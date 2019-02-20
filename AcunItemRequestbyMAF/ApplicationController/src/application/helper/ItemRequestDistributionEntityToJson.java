package application.helper;

import application.entities.ItemRequestDistributionEntity;

public class ItemRequestDistributionEntityToJson {
    
    public static String getJson(ItemRequestDistributionEntity distribution){
        
    StringBuffer sb = new StringBuffer();
    sb.append("{");
    
    if(distribution.getItemRequestId()!=null){
     sb.append("\"ItemRequestId\":");
     sb.append(distribution.getItemRequestId()+",");
    }
    if(distribution.getItemRequestLine()!=null){
     sb.append("\"ItemRequestLine\":");
     sb.append(distribution.getItemRequestLine()+",");
    }
    if(distribution.getItemDistributionLine()!=null){
     sb.append("\"ItemDistributionLine\":");
     sb.append(distribution.getItemDistributionLine()+",");
    }
    if(distribution.getApprovedQuantity()!= null){
     sb.append("\"ApprovedQuantity\":");
     sb.append(distribution.getApprovedQuantity()+",");
    }
    if(distribution.getSourceSubinventory()!= null){
     sb.append("\"SourceSubinventoryId\":");
     sb.append(distribution.getSourceSubinventoryId()+",");
    }
    if(distribution.getSourceSubinventory()!= null){
     sb.append("\"SourceSubinventory\":\"");
     sb.append(distribution.getSourceSubinventory()+"\",");
    }
    if(distribution.getSourceLocatorId()!= null){
     sb.append("\"SourceLocatorId\":");
     sb.append(distribution.getSourceLocatorId()+",");
    }
    if(distribution.getSourceLocator()!= null){
     sb.append("\"SourceLocator\":\"");
     sb.append(distribution.getSourceLocator()+"\",");
    }

    sb.deleteCharAt(sb.lastIndexOf(","));
    
    sb.append("}");
    
    String jsonObject = sb.toString();     
                
    return jsonObject;
    
    }
}
