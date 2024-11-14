package muscletrack.app.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import muscletrack.app.App;

import java.io.IOException;
import java.util.Objects;

public class RepCarga  {
    private HBox repCargaBox;

    public RepCarga(){
        try {
            repCargaBox = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("rep_carga_inputs.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setRep(int rep){
        ((TextField)((HBox)repCargaBox.getChildren().getFirst()).getChildren().getFirst()).setText(String.valueOf(rep));
    }

    public void setCarga(double carga){
        ((TextField)((HBox)repCargaBox.getChildren().get(1)).getChildren().getFirst()).setText(String.valueOf(carga));
    }

    public HBox getRepCargaBox() {
        return repCargaBox;
    }
}
