package trabalho.apsoo.database;

import org.json.JSONObject;

public interface FBRequestBodyConvertible {
    public JSONObject toFirebaseRequestBody();
}
