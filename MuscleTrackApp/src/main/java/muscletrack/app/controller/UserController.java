package muscletrack.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import muscletrack.app.App;
import muscletrack.app.model.User;

public class UserController {

    // Componentes da interface definidos no FXML
    @FXML
    private Label greetingLabel;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private Label dailyPerformanceLabel;

    @FXML
    private Label cyclePerformanceLabel;

    // Inicializa a interface com valores padrão
    @FXML
    public void initialize() {
        greetingLabel.setText("Olá, " + App.user.getUsername());
        nameField.setText(App.user.getUsername());
        passwordField.setText(App.user.getPassword());
        emailField.setText(App.user.getEmail());


        dailyPerformanceLabel.setText("0%");
        cyclePerformanceLabel.setText("0%");
    }

    public void logout_button_click() {
        App.user = new User();
        App.changeToLogin();
    }



    public void back_button_click() {App.changeToHome();}

    public void save_button_click() {
        App.user.setEmail(emailField.getText());
        App.user.setPassword(passwordField.getText());
        App.user.setUsername((nameField.getText()));

        App.fb.changeUserEmail(App.user);
        App.fb.changeUserPassword(App.user);
        App.fb.saveUserData(App.user);


    }
}
