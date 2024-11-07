package muscletrack.app.model;

import org.json.JSONObject;
import muscletrack.app.database.FBRequestBodyConvertible;
import muscletrack.app.database.FBRequestBodyFactory;

import java.util.ArrayList;
import java.util.List;

public class Serie implements FBRequestBodyConvertible {

    int repeticoes;
    double peso;

    public Serie(double peso, int repeticoes) {
        this.peso = peso;
        this.repeticoes = repeticoes;
    }

    @Override
    public String toString() {
        return this.toJSON().toString();
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("rep", this.repeticoes);
        obj.put("peso", this.peso);

        return obj;
    }

    public JSONObject toFirebaseRequestBody() {

        FBRequestBodyFactory f = new FBRequestBodyFactory();

        List<String> keys = new ArrayList<String>();
        List<JSONObject> values = new ArrayList<JSONObject>();

        keys.add("peso");
        keys.add("rep");

        values.add(f.doubleValue(this.peso));
        values.add(f.integerValue(this.repeticoes));

        JSONObject fields = f.fields(keys, values);

        return f.mapValue(fields);
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }


}
