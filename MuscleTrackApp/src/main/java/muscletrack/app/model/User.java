package muscletrack.app.model;

import muscletrack.app.database.FBResponseBodyParser;
import muscletrack.app.utils.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import muscletrack.app.database.FBRequestBodyFactory;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private String email;
    private String password;
    private String localID;
    private String idToken;
    private String refreshToken;
    private Ciclo ciclo;
    private boolean isLoaded;
    private List<TreinoRealizado> treinosRealizados;

    public User() {
        this.treinosRealizados = new ArrayList<>();
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.treinosRealizados = new ArrayList<>();
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.treinosRealizados = new ArrayList<>();
    }

    // Talvez tenha alguma forma melhor de fazer isso, mas por enquanto funciona
    public boolean loadFromJSON(JSONObject data) {

        FBResponseBodyParser parser = new FBResponseBodyParser();

        JSONObject fields = parser.getFields(data);

        this.username = parser.getStringValue(parser.getKey("user_name", fields));

        JSONObject treinosRealizadosJSON = parser.getKey("treinos_realizados", fields);

        if (treinosRealizadosJSON != null) {

            JSONArray treinosRealizadosValues = parser.getArrayValues(treinosRealizadosJSON);

            if(treinosRealizadosValues != null){

                for (int i = 0; i < treinosRealizadosValues.length(); i++) {

                    JSONObject treinoRealizado = (JSONObject) treinosRealizadosValues.get(i);

                    JSONObject treinoRealizadoFields = parser.getMapValue(treinoRealizado);
                    String dataISO = parser.getTimestampValue(parser.getKey("data", treinoRealizadoFields));

                    TreinoRealizado tr = new TreinoRealizado(dataISO);

                    JSONObject treinoFields = parser.getMapValue(parser.getKey("treino", treinoRealizadoFields));

                    String titulo = parser.getStringValue(parser.getKey("titulo", treinoFields));

                    int qntExercicios = parser.getIntegerValue(parser.getKey("qnt_exercicios", treinoFields));

                    String agrupamento = parser.getStringValue(parser.getKey("agrupamento", treinoFields));

                    Treino t = new Treino(titulo, qntExercicios);

                    t.setAgrupamento(agrupamento);

                    JSONArray exerciciosValues = parser.getArrayValues(parser.getKey("exercicios", treinoFields));

                    if(exerciciosValues != null){
                        for (int j = 0; j < exerciciosValues.length(); j++) {

                            JSONObject exercicio = (JSONObject) exerciciosValues.get(j);

                            JSONObject exercicioFields = parser.getMapValue(exercicio);

                            int qntSeries = parser.getIntegerValue(parser.getKey("qnt_series", exercicioFields));

                            String nome = parser.getStringValue(parser.getKey("nome", exercicioFields));

                            Exercicio e = new Exercicio(nome, qntSeries);

                            JSONArray seriesValues = parser.getArrayValues(parser.getKey("series", exercicioFields));

                            for (int k = 0; k < seriesValues.length(); k++) {
                                JSONObject serie = (JSONObject) seriesValues.get(k);

                                JSONObject serieFields = parser.getMapValue(serie);

                                int repeticoes = parser.getIntegerValue(parser.getKey("rep", serieFields));
                                double peso = parser.getDoubleValue(parser.getKey("peso", serieFields));

                                Serie s = new Serie(peso, repeticoes);

                                e.series.add(s);
                            }

                            t.exercicios.add(e);

                        }
                    }


                    tr.setTreino(t);
                    this.treinosRealizados.add(tr);
                }
            }

        }

        JSONObject ciclo = parser.getKey("ciclo", fields);

        if (ciclo != null) {

            JSONObject cicloFields = parser.getMapValue(parser.getKey("ciclo", fields));

            String data_inicioISO = parser.getTimestampValue(parser.getKey("data_inicio", cicloFields));
            int duracao = parser.getIntegerValue(parser.getKey("duracao", cicloFields));

            DateUtils dt = new DateUtils();

            this.setCiclo(new Ciclo(duracao, dt.convertIsoToDate(data_inicioISO)));

            JSONArray treinosValues = parser.getArrayValues(parser.getKey("treinos", cicloFields));

            for (int i = 0; i < treinosValues.length(); i++) {
                JSONObject treino = (JSONObject) treinosValues.get(i);

                JSONObject treinoFields = parser.getMapValue(treino);

                String titulo = parser.getStringValue(parser.getKey("titulo", treinoFields));

                int qntExercicios = parser.getIntegerValue(parser.getKey("qnt_exercicios", treinoFields));

                String agrupamento = parser.getStringValue(parser.getKey("agrupamento", treinoFields));

                Treino t = new Treino(titulo, qntExercicios);

                t.setAgrupamento(agrupamento);

                JSONArray exerciciosValues = parser.getArrayValues(parser.getKey("exercicios", treinoFields));

                if (exerciciosValues != null){
                    for (int j = 0; j < exerciciosValues.length(); j++) {

                        JSONObject exercicio = (JSONObject) exerciciosValues.get(j);

                        JSONObject exercicioFields = parser.getMapValue(exercicio);

                        int qntSeries = parser.getIntegerValue(parser.getKey("qnt_series", exercicioFields));

                        String nome = parser.getStringValue(parser.getKey("nome", exercicioFields));

                        Exercicio e = new Exercicio(nome, qntSeries);

                        JSONArray seriesValues = parser.getArrayValues(parser.getKey("series", exercicioFields));

                        for (int k = 0; k < seriesValues.length(); k++) {
                            JSONObject serie = (JSONObject) seriesValues.get(k);

                            JSONObject serieFields = parser.getMapValue(serie);

                            int repeticoes = parser.getIntegerValue(parser.getKey("rep", serieFields));
                            double peso = parser.getDoubleValue(parser.getKey("peso", serieFields));

                            Serie s = new Serie(peso, repeticoes);

                            e.series.add(s);
                        }

                        t.exercicios.add(e);

                    }
                }


                this.getCiclo().treinos.add(t);
            }

        }

        this.isLoaded = true;
        return true;
    }

    public JSONObject toFirebaseRequestBody() {
        FBRequestBodyFactory f = new FBRequestBodyFactory();

        List<String> keys = new ArrayList<>();
        keys.add("user_name");

        List<JSONObject> values = new ArrayList<>();

        values.add(f.stringValue(this.username));

        if(this.ciclo != null){
            JSONObject ciclo = this.ciclo.toFirebaseRequestBody();
            values.add(f.mapValue(ciclo));
            keys.add("ciclo");
        }

        if(this.treinosRealizados != null && !this.treinosRealizados.isEmpty()) {
            List<JSONObject> treinosRealizadosJSON = new ArrayList<>();

            keys.add("treinos_realizados");

            for (TreinoRealizado t : this.treinosRealizados) {
                treinosRealizadosJSON.add(t.toFirebaseRequestBody());
            }

            values.add(f.arrayValue(treinosRealizadosJSON));
        }

        return f.fields(keys, values);
    }


    @Override
    public String toString() {
        return " User: " + this.username + " " + this.email + " " + this.password + " \n" + this.ciclo.toString();
    }

    public TreinoRealizado getTreinoRealizadoByData(String dataYMD) {

        DateUtils dt = new DateUtils();

        for (TreinoRealizado t : this.treinosRealizados) {
            String data = dt.convertIsoToYmd(t.getDateISO());
            if(dataYMD.length() > 10){
                dataYMD = dataYMD.substring(0, dataYMD.indexOf('T'));
            }
            if (data.equals(dataYMD)) {
                return t;
            }
        }

        return null;
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

    public boolean getIsLoaded(){
        return this.isLoaded;
    }
}
