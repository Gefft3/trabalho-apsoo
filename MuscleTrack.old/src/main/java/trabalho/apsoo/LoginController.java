package trabalho.apsoo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginController {
    @FXML
    private Label loginText;

    @FXML
    protected void onLoginButtonClick(){
        loginText.setText("Você apertou no meu botão!!!");
    }
}
