package muscletrack.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import muscletrack.app.App;

public class LoginController {
    @FXML
    public Label errorText;
    @FXML
    private TextField emailInput;
    @FXML
    private PasswordField passwordInput;

    @FXML
    protected void onLoginButtonClick() {
        String email = emailInput.getCharacters().toString();
        String pass = passwordInput.getCharacters().toString();

        if (email.isBlank() || pass.isBlank()) {
            errorText.setText("E-mail e/ou senha incorretos");
        }else{
            App.user.setEmail(email);
            App.user.setPassword(pass);

            if(!App.fb.authenticateUser(App.user)){
                errorText.setText("E-mail e/ou senha incorretos");
            }else{
                System.out.println("Logado com sucesso!!!");
                App.changeToHome();
            }
        }

    }

    @FXML
    public void onRegisterLabelClick() {
        App.changeToRegister();
    }
}