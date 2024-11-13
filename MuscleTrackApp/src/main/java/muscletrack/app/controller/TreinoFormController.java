package muscletrack.app.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import muscletrack.app.App;
import java.io.IOException;
import java.util.Objects;

public class TreinoFormController {
    @FXML
    public VBox seriesBox;

    public void adicionarSerie(MouseEvent mouseEvent) {
        try {
            HBox repCargaInputs = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("rep_carga_inputs.fxml")));

            VBox v = new VBox();
            Label x = new Label("X");
            x.getStyleClass().setAll("remover");
            x.setOnMouseClicked(this::removerSerie);

            v.getChildren().add(x);

            repCargaInputs.getChildren().add(v);

            ObservableList<Node> children = seriesBox.getChildren();

            children.add(children.size() - 1, repCargaInputs);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removerSerie(MouseEvent mouseEvent) {
        HBox repCargaInputToRemove = (HBox) ((Label) mouseEvent.getSource()).getParent().getParent();

        int index = seriesBox.getChildren().indexOf(repCargaInputToRemove);

        System.out.println(index);

        seriesBox.getChildren().remove(index);
    }
}
