package trabalho.apsoo;

import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Firebase {

    private final String apiKey;
    private final String firestoreBaseURL;
    private final String authURL;

    public Firebase(String apiKey, String authURL, String firestoreBaseURL) {
        this.apiKey = apiKey;
        this.authURL = authURL;
        this.firestoreBaseURL = firestoreBaseURL;
    }

    public boolean loadUserData(User u){
        try{

            URI finalUrl = new URI(this.firestoreBaseURL + u.getLocalID() + "?key=" + this.apiKey);

            HttpURLConnection connection = (HttpURLConnection) finalUrl.toURL().openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + u.getIdToken());

            int responseCode = connection.getResponseCode();

            if(responseCode == 200) {
                JSONObject responseJSON = readResponse(connection.getInputStream());

                return u.loadFromJSON(responseJSON);
            }
            return false;

        }catch (URISyntaxException | IOException e){
            return false;
        }
    }

    public boolean saveUserData(User u){
        try{

            URI finalUrl = new URI(this.firestoreBaseURL + u.getLocalID() + "?key=" + this.apiKey);

            HttpURLConnection connection = (HttpURLConnection) finalUrl.toURL().openConnection();

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("X-HTTP-Method-Override", "PATCH"); // Por algum motivo o metodo PATCH não exite na biblioteca
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + u.getIdToken());

            JSONObject body = u.toFirebaseRequestBody();

            OutputStream os = connection.getOutputStream();

            os.write(body.toString().getBytes(StandardCharsets.UTF_8));
            os.close();

            int responseCode = connection.getResponseCode();

            if(responseCode == 200){
                System.out.println("Data Saved!");
                return true;
            }

        }catch (URISyntaxException | IOException e){
            return false;
        }
        return false;
    }

    public boolean registerUser(User u){
        try {
            URI finalURI = new URI(this.authURL + "signUp?key=" + this.apiKey);

            HttpURLConnection connection = (HttpURLConnection) finalURI.toURL().openConnection();

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            JSONObject reqBody = new JSONObject();

            reqBody.put("email", u.getEmail());
            reqBody.put("password", u.getPassword());
            reqBody.put("returnSecureToken", true);

            OutputStream os = connection.getOutputStream();
            os.write(reqBody.toString().getBytes(StandardCharsets.UTF_8));
            os.close();

            int responseCode = connection.getResponseCode();

            if(responseCode == 200) {
                JSONObject responseJSON = readResponse(connection.getInputStream());

                u.setIdToken(responseJSON.getString("idToken"));
                u.setLocalID(responseJSON.getString("localId"));
                return true;
            }else if(responseCode == 400){
                System.out.println("Não foi possível cadastrar");
            }
            return false;
        }catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean authenticateUser(User u) {
        try {

            URI finalURI = new URI(this.authURL + "signInWithPassword?key=" + this.apiKey);

            HttpURLConnection connection = (HttpURLConnection) finalURI.toURL().openConnection();

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            JSONObject reqBody = new JSONObject();

            reqBody.put("email", u.getEmail());
            reqBody.put("password", u.getPassword());
            reqBody.put("returnSecureToken", true);

            OutputStream os = connection.getOutputStream();

            os.write(reqBody.toString().getBytes(StandardCharsets.UTF_8));
            os.close();

            int responseCode = connection.getResponseCode();

            if(responseCode == 200) {

                JSONObject responseJSON = readResponse(connection.getInputStream());

                u.setIdToken(responseJSON.getString("idToken"));
                u.setLocalID(responseJSON.getString("localId"));
                return true;
            }
            return false;
        } catch (IOException | URISyntaxException e) {
            return false;
        }
    }

    private JSONObject readResponse(InputStream inputStream){
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder res = new StringBuilder();

        String inputLine;

        try {
            while ((inputLine = in.readLine()) != null) {
                res.append(inputLine);
            }
            in.close();
        }catch (IOException e){
            return new JSONObject();
        }

        return new JSONObject(res.toString());
    }

}
