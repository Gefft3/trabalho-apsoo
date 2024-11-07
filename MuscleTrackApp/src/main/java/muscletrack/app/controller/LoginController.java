package muscletrack.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import muscletrack.app.App;

import java.util.Objects;

public class LoginController {
    @FXML
    public Label errorText;
    @FXML
    private TextField emailInput;
    @FXML
    private PasswordField passwordInput;

    @FXML
    protected void onLoginButtonClick() {
        logar();
    }

    @FXML
    public void onRegisterLabelClick() {
        passwordInput.setText("");
        emailInput.setText("");
        App.changeToRegister();
    }

    public void onPasswordEnterPressed(KeyEvent keyEvent) {
        if (Objects.equals(keyEvent.getCode().toString(), "ENTER")) {
            logar();
        }
    }

    private void logar() {
        String email = emailInput.getCharacters().toString();
        String pass = passwordInput.getCharacters().toString();

        errorText.setText("");

        if (email.isBlank() || pass.isBlank()) {
            errorText.setText("E-mail e/ou senha incorretos");
            passwordInput.setText("");
        } else {
            App.user.setEmail(email);
            App.user.setPassword(pass);

            if (!App.fb.authenticateUser(App.user)) {
                errorText.setText("E-mail e/ou senha incorretos");
                passwordInput.setText("");
            } else {
                System.out.println("Logado com sucesso!!!");
                passwordInput.setText("");
                emailInput.setText("");
                App.changeToHome();
            }
        }
    }
}