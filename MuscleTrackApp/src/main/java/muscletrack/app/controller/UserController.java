package muscletrack.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        greetingLabel.setText("Olá, nome do usuário");
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
}
