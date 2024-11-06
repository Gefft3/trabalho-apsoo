package muscletrack.app.model;

import org.json.JSONArray;
import org.json.JSONObject;
import muscletrack.app.database.FBRequestBodyFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class User {

    private String username;
    private String email;
    private String password;
    private String localID;
    private String idToken;
    private String refreshToken;
    private Ciclo ciclo;
    private List<TreinoRealizado> treinosRealizados;

    public User(){
        this.treinosRealizados = new ArrayList<>();
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.treinosRealizados = new ArrayList<>();
    }

    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
        this.treinosRealizados = new ArrayList<>();
    }

    // Talvez tenha alguma forma melhor de fazer isso, mas por enquanto funciona
    public boolean loadFromJSON(JSONObject data){

        JSONObject fields = data.getJSONObject("fields");

        JSONObject userNameJSON = fields.getJSONObject("user_name");

        this.username = userNameJSON.getString("stringValue");

        JSONObject treinosRealizados = fields.getJSONObject("treinos_realizados");
        JSONObject treinosRealizadosArrayValue = treinosRealizados.getJSONObject("arrayValue");

        JSONArray treinosRealizadosValues = treinosRealizadosArrayValue.getJSONArray("values");

        for(int i = 0; i < treinosRealizadosValues.length(); i++){

            JSONObject treinoRealizado = (JSONObject) treinosRealizadosValues.get(i);

            JSONObject treinoRealizadoMapValue = treinoRealizado.getJSONObject("mapValue");

            JSONObject treinoRealizadoFields = treinoRealizadoMapValue.getJSONObject("fields");

            JSONObject dataJson = treinoRealizadoFields.getJSONObject("data");
            String dataISO = dataJson.getString("timestampValue");

            TreinoRealizado tr = new TreinoRealizado(dataISO);

            JSONObject treino = treinoRealizadoFields.getJSONObject("treino");

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

            tr.setTreino(t);
            this.treinosRealizados.add(tr);
        }

        JSONObject ciclo = fields.getJSONObject("ciclo");
        JSONObject cicloMapValue = ciclo.getJSONObject("mapValue");

        JSONObject cicloFields = cicloMapValue.getJSONObject("fields");

        int duracao = cicloFields.getJSONObject("duracao").getInt("integerValue");
        String data_inicioISO = cicloFields.getJSONObject("data_inicio").getString("timestampValue");

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);

        try {
            // Parse the ISO 8601 string and convert to Date
            Date date = df.parse(data_inicioISO);
            this.setCiclo(new Ciclo(duracao, date));
        }
        catch (Exception e) {
            return false;
        }


        JSONObject treinos = cicloFields.getJSONObject("treinos");
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

    public JSONObject toFirebaseRequestBody(){
        FBRequestBodyFactory f = new FBRequestBodyFactory();

        List<String> keys = new ArrayList<>();
        keys.add("user_name");
        keys.add("ciclo");
        keys.add("treinos_realizados");

        List<JSONObject> values = new ArrayList<>();

        values.add(f.stringValue(this.username));

        JSONObject ciclo = this.ciclo.toFirebaseRequestBody();
        values.add(f.mapValue(ciclo));

        List<JSONObject> treinosRealizadosJSON = new ArrayList<>();

        for(TreinoRealizado t: this.treinosRealizados){
            treinosRealizadosJSON.add(t.toFirebaseRequestBody());
        }

        values.add(f.arrayValue(treinosRealizadosJSON));

        return f.fields(keys, values);
    }


    @Override
    public String toString(){
        return " User: " + this.username + " " + this.email + " " + this.password + " \n" + this.ciclo.toString();
    }

    public TreinoRealizado getTreinoRealizadoByData(String date){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        d.setTimeZone(tz);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);

        for(TreinoRealizado t: this.treinosRealizados){
            String data;
            try {
                data = d.format(df.parse(t.getDateISO()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            System.out.println(data + " " + date    );
            if(data.equals(date)){
                System.out.println("é igual");
                return t;
            }
        }

        return new TreinoRealizado("");
    }

    public List<TreinoRealizado> getTreinosRealizados() {
        return treinosRealizados;
    }

    public void setTreinosRealizados(List<TreinoRealizado> treinosRealizados) {
        this.treinosRealizados = treinosRealizados;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
