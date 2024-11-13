package muscletrack.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import muscletrack.app.App;

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

    // Método para atualizar as informações do usuário
    public void updateUserInformation(String name, String email, int dailyPerformance, int cyclePerformance) {
        nameField.setText(name);
        emailField.setText(email);
        dailyPerformanceLabel.setText(dailyPerformance + "%");
        cyclePerformanceLabel.setText(cyclePerformance + "%");
    }

    public void logout_button_click() {App.changeToLogin();}

    public void back_button_click() {App.changeToHome();}
}
