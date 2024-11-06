package muscletrack.app.model;

import muscletrack.app.database.FBRequestBodyFactory;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TreinoRealizado {
    private String dateISO;
    private Treino treino;

    public TreinoRealizado(String dateISO) {
        this.dateISO = dateISO;
    }

    public String getDateISO() {
        return dateISO;
    }

    public void setDateISO(String dateISO) {
        this.dateISO = dateISO;
    }

    public Treino getTreino() {
        return treino;
    }

    public void setTreino(Treino treino) {
        this.treino = treino;
    }

    public JSONObject toFirebaseRequestBody() {
        FBRequestBodyFactory f = new FBRequestBodyFactory();

        List<String> keys = new ArrayList<>();
        keys.add("data");
        keys.add("treino");

        JSONObject data = f.timestampValue(this.dateISO);
        JSONObject treino = this.treino.toFirebaseRequestBody();

        List<JSONObject> values = new ArrayList<>();

        values.add(data);
        values.add(treino);

        return f.mapValue(f.fields(keys, values));
    }
}
