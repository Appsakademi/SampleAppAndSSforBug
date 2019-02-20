package application.helper;

import application.entities.ItemRequestDetailEntity;
import application.entities.ItemRequestHeaderEntity;

import java.util.ArrayList;

import java.util.logging.Level;

import oracle.adfmf.framework.api.JSONBeanSerializationHelper;
import oracle.adfmf.json.JSONArray;
import oracle.adfmf.json.JSONObject;
import oracle.adfmf.util.logging.Trace;

public class JsonArrayToItemRequestHeaderArray {
    public JsonArrayToItemRequestHeaderArray() {
        super();
    }

    @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
    public static ArrayList<ItemRequestHeaderEntity> getHeadersArray(String jsonArrayAsString){
        
        //Java object that represents the Java structure for the JSON notation structure. Its simple in 
        //this sample as the array only contains an array of departments with no additional child objects 
        //or primitive values
        ArrayList<ItemRequestHeaderEntity> headers = null;
        
        //object that serializes the JSON payload into the Java object
        JSONBeanSerializationHelper jbsh = new JSONBeanSerializationHelper();

        try {
            JSONObject json = new JSONObject(jsonArrayAsString);
            JSONArray items = json.getJSONArray("items");
            ArrayList<ItemRequestHeaderEntity> tempList = new ArrayList<>();
            for(int i=0; i<items.length(); i++){
                
                ItemRequestHeaderEntity header = new ItemRequestHeaderEntity();
                //JSON Array'in içindeki her bir sat?r için i?lem yapaca??z. Önce JSON Objesi olarak item'? al?p daha 
                //daha sonra JBSH class?n?n yard?m?yla ilgili Entity class?na parse edece?iz. 11.02.2019 - Yunus Emre BAYRAM a.k.a. Akitas
                JSONObject item = (JSONObject)items.get(i);
                header = (ItemRequestHeaderEntity)jbsh.fromJSON(ItemRequestHeaderEntity.class, item);
                //Detay tablosunun linkine eri?ece?iz. Links array'i içerisinde 3 obje tutuyor. self, cannonical, child yani detail.
                JSONArray links = item.getJSONArray("links");
                JSONObject link = (JSONObject)links.get(2);
                String detailLink = link.getString("href");
                Object[] splitLink = detailLink.split("rest/1/");
                String endPoint = "/" + splitLink[1].toString();
                header.setDetailsLink(endPoint);
                header.setDetails(new ArrayList<ItemRequestDetailEntity>());
                tempList.add(header);
            }
            headers = tempList;
        } catch (Exception e) {
            Trace.log("JSONArray_to_JavaArray",Level.SEVERE, JsonArrayToItemRequestHeaderArray.class,"getDepartmentsArray", "Parsing of REST response failed: "+e.getLocalizedMessage());
        }

      return headers;
    }
}
