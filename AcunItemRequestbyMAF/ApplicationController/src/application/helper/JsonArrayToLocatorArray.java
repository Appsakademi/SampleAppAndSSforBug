package application.helper;

import application.entities.LocatorEntity;

import java.util.ArrayList;
import java.util.logging.Level;

import oracle.adfmf.framework.api.JSONBeanSerializationHelper;
import oracle.adfmf.json.JSONArray;
import oracle.adfmf.json.JSONObject;
import oracle.adfmf.util.logging.Trace;

public class JsonArrayToLocatorArray {
    public JsonArrayToLocatorArray() {
        super();
    }

    @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
    public static ArrayList<LocatorEntity> getLocatorsArray(String jsonArrayAsString){
        
        //Java object that represents the Java structure for the JSON notation structure. Its simple in 
        //this sample as the array only contains an array of departments with no additional child objects 
        //or primitive values
        ArrayList<LocatorEntity> locators = null;
        
        //object that serializes the JSON payload into the Java object
        JSONBeanSerializationHelper jbsh = new JSONBeanSerializationHelper();

        try {
            JSONObject json = new JSONObject(jsonArrayAsString);
            JSONArray items = json.getJSONArray("items");
            ArrayList<LocatorEntity> tempList = new ArrayList<>();
            for(int i=0; i<items.length(); i++){
                
                LocatorEntity locator = new LocatorEntity();
                //JSON Array'in içindeki her bir sat?r için i?lem yapaca??z. Önce JSON Objesi olarak item'? al?p daha 
                //daha sonra JBSH class?n?n yard?m?yla ilgili Entity class?na parse edece?iz. 11.02.2019 - Yunus Emre BAYRAM a.k.a. Akitas
                JSONObject item = (JSONObject)items.get(i);
                locator = (LocatorEntity)jbsh.fromJSON(LocatorEntity.class, item);
                String a = locator.getLocator();
                tempList.add(locator);
            }
            locators = tempList;
        } catch (Exception e) {
            Trace.log("JSONArray_to_JavaArray",Level.SEVERE, JsonArrayToItemRequestHeaderArray.class,"getDepartmentsArray", "Parsing of REST response failed: "+e.getLocalizedMessage());
        }

      return locators;
    }
}
