package muscletrack.app.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import muscletrack.app.App;

import java.io.IOException;
import java.util.Objects;

public class TreinoForm {
    private final VBox treinoFormBox;

    public TreinoForm(){
        try {
            treinoFormBox = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("treino_form_box.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSerie(RepCarga rc){

        ScrollPane sp = (ScrollPane) this.treinoFormBox.getChildren().getLast();

        VBox box = (VBox) sp.getContent();

        box.getChildren().add(box.getChildren().size()-1, rc.getRepCargaBox());

    }

    public void setTitulo(String titulo){
        ((TextField) ((GridPane) treinoFormBox.getChildren().getFirst()).getChildren().getFirst()).setText(titulo);
    }

    public VBox getTreinoFormBox(){
        return this.treinoFormBox;
    }
}
