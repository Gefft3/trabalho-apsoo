package muscletrack.app.model;

import java.util.List;

import org.json.JSONObject;
import muscletrack.app.database.FBRequestBodyConvertible;
import muscletrack.app.database.FBRequestBodyFactory;

import java.util.ArrayList;

public class Exercicio implements FBRequestBodyConvertible {

    String nome;
    int qntSeries;
    List<Serie> series;

    public Exercicio(String nome, int qntSeries) {
        this.nome = nome;
        this.qntSeries = qntSeries;
        this.series = new ArrayList<Serie>(qntSeries);
    }

    public void addSerie(Serie s) {
        this.series.add(s);
    }

    @Override
    public String toString() {
        return this.toJSON().toString();
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("nome", this.nome);
        obj.put("qntSeries", this.qntSeries);

        List<JSONObject> seriesJSON = new ArrayList<JSONObject>(this.qntSeries);

        for (Serie s : this.series) {
            seriesJSON.add(s.toJSON());
        }

        obj.put("series", seriesJSON);

        return obj;
    }

    public JSONObject toFirebaseRequestBody() {

        FBRequestBodyFactory f = new FBRequestBodyFactory();

        List<String> keys = new ArrayList<String>();
        List<JSONObject> values = new ArrayList<JSONObject>();

        List<JSONObject> seriesValues = new ArrayList<JSONObject>();

        for (Serie s : this.series) {
            seriesValues.add(s.toFirebaseRequestBody());
        }

        JSONObject series = f.arrayValue(seriesValues);

        keys.add("qnt_series");
        keys.add("nome");
        keys.add("series");

        values.add(f.integerValue(this.qntSeries));
        values.add(f.stringValue(this.nome));
        values.add(series);


        JSONObject fields = f.fields(keys, values);

        return f.mapValue(fields);
    }
}
