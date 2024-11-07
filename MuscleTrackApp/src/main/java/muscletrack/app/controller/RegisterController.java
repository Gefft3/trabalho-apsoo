package muscletrack.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import muscletrack.app.App;

public class RegisterController {
    @FXML
    private TextField nomeInput;
    @FXML
    private Label errorText;
    @FXML
    private PasswordField confirmPasswordInput;
    @FXML
    private TextField emailInput;
    @FXML
    private PasswordField passwordInput;

    @FXML
    protected void onRegisterButtonClick() {

        String nome = nomeInput.getCharacters().toString();
        String email = emailInput.getCharacters().toString();
        String pass = passwordInput.getCharacters().toString();
        String confirmPass = confirmPasswordInput.getCharacters().toString();

        if (email.isBlank() || pass.isBlank() || nome.isBlank() || confirmPass.isBlank()) {
            errorText.setText("Por favor insira todos os campos.");
        }else if(!pass.equals(confirmPass)){
            errorText.setText("As senhas não são iguais.");
        } else {
            App.user.setUsername(nome);
            App.user.setEmail(email);
            App.user.setPassword(pass);


            if(!App.fb.registerUser(App.user)){
                errorText.setText("E-mail ou senha inválidos.");
                passwordInput.setText("");
                confirmPasswordInput.setText("");
            }else{
                nomeInput.setText("");
                emailInput.setText("");
                passwordInput.setText("");
                confirmPasswordInput.setText("");
                App.changeToLogin();
            }
        }
    }

    @FXML
    public void onLoginLabelClick() {
        App.changeToLogin();
    }

}


