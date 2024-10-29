package trabalho.apsoo.model;

import java.util.List;
import org.json.JSONObject;
import trabalho.apsoo.database.FBRequestBodyConvertible;
import trabalho.apsoo.database.FBRequestBodyFactory;

import java.util.ArrayList;

public class Treino implements FBRequestBodyConvertible {

  String titulo;
  int qntExercicios;
  List<Exercicio> exercicios;

  public Treino(String titulo, int qntExercicios) {
    this.titulo = titulo;
    this.qntExercicios = qntExercicios;
    this.exercicios = new ArrayList<Exercicio>(qntExercicios);
  }

  public void addExercicio(Exercicio e) {
    this.exercicios.add(e);
  }

  @Override
  public String toString() {
    return this.toJSON().toString();
  }

  public JSONObject toJSON() {
    JSONObject obj = new JSONObject();

    obj.put("titulo", this.titulo);
    obj.put("qntExercicios", this.qntExercicios);

    List<JSONObject> exerciciosJSON = new ArrayList<JSONObject>(this.qntExercicios);

    for (Exercicio e : this.exercicios) {
      exerciciosJSON.add(e.toJSON());
    }

    obj.put("exercicios", exerciciosJSON);

    return obj;
  }

  public JSONObject toFirebaseRequestBody() {

    FBRequestBodyFactory f = new FBRequestBodyFactory();

    List<String> keys = new ArrayList<String>();
    List<JSONObject> values = new ArrayList<JSONObject>();

    List<JSONObject> exerciciosValues = new ArrayList<JSONObject>();

    for(Exercicio s: this.exercicios){
      exerciciosValues.add(s.toFirebaseRequestBody());
    }

    JSONObject exercicios = f.arrayValue(exerciciosValues);

    keys.add("exercicios");
    keys.add("titulo");
    keys.add("qnt_exercicios");

    values.add(exercicios);
    values.add(f.stringValue(this.titulo));
    values.add(f.integerValue(this.qntExercicios));


    JSONObject fields = f.fields(keys, values);

    return f.mapValue(fields);
  }

}
