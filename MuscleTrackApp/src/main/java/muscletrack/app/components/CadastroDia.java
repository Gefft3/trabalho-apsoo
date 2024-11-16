package muscletrack.app.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import muscletrack.app.App;

import java.io.IOException;
import java.util.Objects;

public class CadastroDia {

    private GridPane cadastroDiaPane;

    public CadastroDia(){
        try {
            cadastroDiaPane = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("cadastro_dia.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTreinoForm(TreinoForm tf){

        ScrollPane sp = (ScrollPane) this.cadastroDiaPane.getChildren().getLast();

        HBox box = (HBox) sp.getContent();

        box.getChildren().add(box.getChildren().size()-1, tf.getTreinoFormBox());
    }

    public void setDia(int dia){
        ((Label)((VBox)cadastroDiaPane.getChildren().getFirst()).getChildren().getFirst()).setText("Dia " + String.valueOf(dia));
    }

    public void setDia(String dia){
        ((Label)((VBox)cadastroDiaPane.getChildren().getFirst()).getChildren().getFirst()).setText(dia);
    }

    public void setTitulo(String titulo){
        ((TextField)((VBox)cadastroDiaPane.getChildren().getFirst()).getChildren().get(2)).setText(titulo);
    }

    public GridPane getCadastroDiaPane(){
        return this.cadastroDiaPane;
    }

    public void setAgrupamento(String agrupamento){
        ((ComboBox)((VBox)cadastroDiaPane.getChildren().getFirst()).getChildren().get(4)).setValue(agrupamento);
    }
}
