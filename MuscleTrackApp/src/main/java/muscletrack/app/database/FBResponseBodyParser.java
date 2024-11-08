package muscletrack.app.database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FBResponseBodyParser {
    public FBResponseBodyParser() {

    }

    public JSONObject getKey(String key, JSONObject obj) {
        try {
            return obj.getJSONObject(key);
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject getFields(JSONObject obj) {
        try {
            return obj.getJSONObject("fields");
        } catch (JSONException e) {
            return null;
        }
    }

    public String getStringValue(JSONObject obj) {
        try {
            return obj.getString("stringValue");
        } catch (JSONException e) {
            return null;
        }
    }

    public String getTimestampValue(JSONObject obj) {
        try {
            return obj.getString("timestampValue");
        } catch (JSONException e) {
            return null;
        }
    }

    public int getIntegerValue(JSONObject obj) {
        try {
            return obj.getInt("integerValue");
        } catch (JSONException e) {
            return -1;
        }
    }

    public double getDoubleValue(JSONObject obj) {
        try {
            return obj.getDouble("doubleValue");
        } catch (JSONException e) {
            return -1;
        }
    }

    public JSONArray getArrayValues(JSONObject obj) {
        try {
            return obj.getJSONObject("arrayValue").getJSONArray("values");
        } catch (JSONException e) {
            return null;
        }
    }

    public JSONObject getMapValue(JSONObject obj) {
        try {
            return obj.getJSONObject("mapValue").getJSONObject("fields");
        } catch (JSONException e) {
            return null;
        }
    }


}
