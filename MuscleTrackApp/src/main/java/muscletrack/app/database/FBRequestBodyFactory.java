package muscletrack.app.database;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class FBRequestBodyFactory {

    public FBRequestBodyFactory(){

    }

    public JSONObject fields(List<String> keys, List<JSONObject> values){
        JSONObject obj = new JSONObject();

        JSONObject fields = new JSONObject();

        for(int i = 0; i < keys.size(); i++){
            fields.put(keys.get(i), values.get(i));
        }

        obj.put("fields", fields);
        return obj;
    }

    public JSONObject arrayValue(List<JSONObject> values){
        JSONObject arrayValue = new JSONObject();

        JSONObject valuesJSON = new JSONObject();

        valuesJSON.put("values", values);

        arrayValue.put("arrayValue", valuesJSON);
        return arrayValue;
    }

    public JSONObject mapValue(JSONObject value){
        JSONObject obj = new JSONObject();
        obj.put("mapValue", value);
        return obj;
    }

    public JSONObject timestampValue(Date timestamp){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);
        String isoTimestamp = df.format(timestamp);
        return new JSONObject("{timestampValue:'" + isoTimestamp + "'}");
    }

    public JSONObject integerValue(int value){
        return new JSONObject("{integerValue:" + value + "}");
    }

    public JSONObject doubleValue(double value){
        return new JSONObject("{doubleValue:" + value + "}");
    }


    public JSONObject stringValue(String  value){
        return new JSONObject("{stringValue:" + value + "}");
    }

}
