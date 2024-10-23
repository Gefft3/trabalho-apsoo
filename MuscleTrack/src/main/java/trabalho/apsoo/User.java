package trabalho.apsoo;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class User {

    String nome;
    String email;
    String password;
    Ciclo ciclo;

    public User(String nome, String email, String password){
        this.nome = nome;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString(){
        return " User: " + this.nome + " " + this.email + " " + this.password + " " + this.ciclo.toString();
    }

    public boolean Authenticate() {
        try {
            String apiKey = System.getenv("FIREBASE_API_KEY");

            URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + apiKey);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            JSONObject body = new JSONObject();

            body.put("email", this.email);
            body.put("password", this.password);
            body.put("returnSecureToken", true);

            OutputStream os = connection.getOutputStream();

            os.write(body.toString().getBytes("UTF-8"));
            os.close();

            int responseCode = connection.getResponseCode();
            System.out.println("Response code: " + responseCode);

            if(responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Print result
                System.out.println("Response: " + response.toString());
                return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

}
