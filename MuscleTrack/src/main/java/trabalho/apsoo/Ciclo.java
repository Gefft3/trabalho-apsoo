package trabalho.apsoo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ciclo implements FBRequestBodyConvertible{

    int duracao;
    Date inicio;
    List<Treino> treinos;

    public Ciclo(){

    }

    public Ciclo(int duracao, Date inicio){
        this.duracao = duracao;
        this.inicio = inicio;
        this.treinos = new ArrayList<>(duracao);
    }

    void addTreino(Treino t){
        this.treinos.add(t);
    }

    @Override
    public String toString(){
        return this.toJSON().toString();
    }

    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();

        obj.put("duracao", this.duracao);
        obj.put("data_inicio", this.inicio);

        List<JSONObject> treinosJSON = new ArrayList<JSONObject>(this.duracao);

        for(Treino t : this.treinos){
            treinosJSON.add(t.toJSON());
        }

        obj.put("treinos", treinosJSON);

        return obj;
    }

    public JSONObject toFirebaseRequestBody(){

        FBRequestBodyFactory f = new FBRequestBodyFactory();

        JSONObject data_inicio = f.timestampValue(this.inicio);
        JSONObject duracao = f.integerValue(this.duracao);

        List<JSONObject> treinosValues = new ArrayList<JSONObject>();

        for(Treino t: this.treinos){
            treinosValues.add(t.toFirebaseRequestBody());
        }

        JSONObject treinos = f.arrayValue(treinosValues);

        List<String> keys = new ArrayList<String>();
        keys.add("data_inicio");
        keys.add("duracao");
        keys.add("treinos");

        List<JSONObject> values = new ArrayList<JSONObject>();
        values.add(data_inicio);
        values.add(duracao);
        values.add(treinos);

        return f.fields(keys, values);
    }

}
