package trabalho.apsoo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class User {

    private String nome;
    private String email;
    private String password;
    private String localID;
    private String idToken;
    private Ciclo ciclo;

    public User(String nome, String email, String password){
        this.nome = nome;
        this.email = email;
        this.password = password;
    }

    // Talvez tenha alguma forma melhor de fazer isso, mas por enquanto funciona
    public boolean loadFromJSON(JSONObject data){
        JSONObject fields = data.getJSONObject("fields");

        int duracao = fields.getJSONObject("duracao").getInt("integerValue");
        String data_inicioISO = fields.getJSONObject("data_inicio").getString("timestampValue");

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);

        try {
            // Parse the ISO 8601 string and convert to Date
            Date date = df.parse(data_inicioISO);
            this.setCiclo(new Ciclo(duracao, date));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject treinos = fields.getJSONObject("treinos");
        JSONObject treinosArrayValue = treinos.getJSONObject("arrayValue");
        JSONArray treinosValues = treinosArrayValue.getJSONArray("values");

        for(int i = 0; i < treinosValues.length(); i++) {
            JSONObject treino = (JSONObject) treinosValues.get(i);

            JSONObject treinoMapValue = treino.getJSONObject("mapValue");

            JSONObject treinoFields = treinoMapValue.getJSONObject("fields");

            JSONObject tituloJSON = treinoFields.getJSONObject("titulo");
            String titulo = tituloJSON.getString("stringValue");

            JSONObject qntExerciciosJSON = treinoFields.getJSONObject("qnt_exercicios");
            int qntExercicios = qntExerciciosJSON.getInt("integerValue");

            Treino t = new Treino(titulo, qntExercicios);

            JSONObject exercicios = treinoFields.getJSONObject("exercicios");
            JSONObject exerciciosArrayValue = exercicios.getJSONObject("arrayValue");
            JSONArray exerciciosValues = exerciciosArrayValue.getJSONArray("values");

            for (int j = 0; j < exerciciosValues.length(); j++) {

                JSONObject exercicio = (JSONObject) exerciciosValues.get(j);

                JSONObject exercicioMapValue = exercicio.getJSONObject("mapValue");

                JSONObject exercicioFields = exercicioMapValue.getJSONObject("fields");

                JSONObject qntSeriesJSON = exercicioFields.getJSONObject("qnt_series");
                int qntSeries = qntSeriesJSON.getInt("integerValue");

                JSONObject nomeJSON = exercicioFields.getJSONObject("nome");
                String nome = nomeJSON.getString("stringValue");

                Exercicio e = new Exercicio(nome, qntSeries);

                JSONObject series = exercicioFields.getJSONObject("series");
                JSONObject seriesArrayValue = series.getJSONObject("arrayValue");
                JSONArray seriesValues = seriesArrayValue.getJSONArray("values");

                for (int k = 0; k < seriesValues.length(); k++) {
                    JSONObject serie = (JSONObject) seriesValues.get(k);

                    JSONObject serieMapValue = serie.getJSONObject("mapValue");
                    JSONObject serieFields = serieMapValue.getJSONObject("fields");

                    JSONObject pesoJSON = serieFields.getJSONObject("peso");
                    JSONObject repJSON = serieFields.getJSONObject("rep");

                    int repeticoes = repJSON.getInt("integerValue");
                    double peso = pesoJSON.getDouble("doubleValue");

                    Serie s = new Serie(peso, repeticoes);

                    e.series.add(s);
                }

                t.exercicios.add(e);

            }

            this.getCiclo().treinos.add(t);
        }


        return true;
    }

    @Override
    public String toString(){
        return " User: " + this.nome + " " + this.email + " " + this.password + " \n" + this.ciclo.toString();
    }

    public String getLocalID() {
        return localID;
    }

    public void setLocalID(String localID) {
        this.localID = localID;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Ciclo getCiclo() {
        return ciclo;
    }

    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
