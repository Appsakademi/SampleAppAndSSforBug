package application.helper;

import application.entities.ItemRequestDetailEntity;

import application.entities.ItemRequestDistributionEntity;

import java.util.ArrayList;
import java.util.logging.Level;

import oracle.adfmf.framework.api.JSONBeanSerializationHelper;
import oracle.adfmf.json.JSONArray;
import oracle.adfmf.json.JSONObject;
import oracle.adfmf.util.logging.Trace;

public class JsonArrayToItemRequestDetailArray {
    public JsonArrayToItemRequestDetailArray() {
        super();
    }

    @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
    public static ArrayList<ItemRequestDetailEntity> getDetailsArray(String jsonArrayAsString){
        
        //Java object that represents the Java structure for the JSON notation structure. Its simple in 
        //this sample as the array only contains an array of departments with no additional child objects 
        //or primitive values
        ArrayList<ItemRequestDetailEntity> details = null;
        
        //object that serializes the JSON payload into the Java object
        JSONBeanSerializationHelper jbsh = new JSONBeanSerializationHelper();

        try {
            JSONObject json = new JSONObject(jsonArrayAsString);
            JSONArray items = json.getJSONArray("items");
            ArrayList<ItemRequestDetailEntity> tempList = new ArrayList<>();
            for(int i=0; i<items.length(); i++){
                
                ItemRequestDetailEntity detail = new ItemRequestDetailEntity();
                //JSON Array'in i�indeki her bir sat?r i�in i?lem yapaca??z. �nce JSON Objesi olarak item'? al?p daha 
                //daha sonra JBSH class?n?n yard?m?yla ilgili Entity class?na parse edece?iz. 11.02.2019 - Yunus Emre BAYRAM a.k.a. Akitas
                JSONObject item = (JSONObject)items.get(i);
                detail = (ItemRequestDetailEntity)jbsh.fromJSON(ItemRequestDetailEntity.class, item);
                Long a = Long.parseLong(item.get("ItemRequestLine").toString());
                System.out.println(a);
                detail.setItemRequestLine(a);
                //Detay tablosunun linkine eri?ece?iz. Links array'i i�erisinde 3 obje tutuyor. self, cannonical, child yani detail.
                JSONArray links = item.getJSONArray("links");
                JSONObject link = (JSONObject)links.get(3);
                String detailLink = link.getString("href");
                Object[] splitLink = detailLink.split("rest/1/");
                String endPoint = "/" + splitLink[1].toString();
                detail.setDistributionsLink(endPoint);
                detail.setDistributions(new ArrayList<ItemRequestDistributionEntity>());
                tempList.add(detail);
            }
            details = tempList;
        } catch (Exception e) {
            Trace.log("JSONArray_to_JavaArray",Level.SEVERE, JsonArrayToItemRequestHeaderArray.class,"getDepartmentsArray", "Parsing of REST response failed: "+e.getLocalizedMessage());
        }

      return details;
    }
}
