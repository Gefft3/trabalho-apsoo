package trabalho.apsoo;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Firebase {

    private String apiKey;
    private String firestoreBaseURL;
    private URI authURL;

    public Firebase(String apiKey, String authURL, String firestoreBaseURL) {
        this.apiKey = apiKey;
        try {
            this.authURL = new URI(authURL + "?key=" + apiKey);
            this.firestoreBaseURL = firestoreBaseURL;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
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
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject responseJSON = new JSONObject(response.toString());

                u.loadFromJSON(responseJSON);

                return true;
            }
            return false;

        }catch (URISyntaxException | IOException e){
            return false;
        }
    }

    public boolean saveUserData(User u){
        try{

            HttpURLConnection connection = getHttpURLConnection(u);

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

    private HttpURLConnection getHttpURLConnection(User u) throws URISyntaxException, IOException {
        URI finalUrl = new URI(this.firestoreBaseURL + u.getLocalID() + "?key=" + this.apiKey);

        HttpURLConnection connection = (HttpURLConnection) finalUrl.toURL().openConnection();

        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("X-HTTP-Method-Override", "PATCH"); // Por algum motivo o metodo PATCH não exite na biblioteca
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + u.getIdToken());
        return connection;
    }

    public boolean authenticateUser(User u) {
        try {

            HttpURLConnection connection = (HttpURLConnection) this.authURL.toURL().openConnection();

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
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();


                JSONObject responseJSON = new JSONObject(response.toString());

                u.setIdToken(responseJSON.getString("idToken"));
                u.setLocalID(responseJSON.getString("localId"));
                return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

}
