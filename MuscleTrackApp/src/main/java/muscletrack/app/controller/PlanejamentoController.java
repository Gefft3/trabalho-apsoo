package muscletrack.app.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import muscletrack.app.App;

import java.io.IOException;
import java.util.Objects;

public class PlanejamentoController {
    private int duracao;
    @FXML
    public VBox planejamentoFormBox;

    public void onCancelarButtonClick(ActionEvent actionEvent) {
    }

    public void onCadastrarButtonClick(ActionEvent actionEvent) {
    }


    public void adicionarDia(MouseEvent mouseEvent) {
        try {
            GridPane cadastroDia = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("cadastro_dia.fxml")));

            Label labelDia = (Label) ((VBox) cadastroDia.getChildren().getFirst()).getChildren().getFirst();

            this.duracao +=1;

            labelDia.setText("Dia " + this.duracao);

            ObservableList<Node> children = planejamentoFormBox.getChildren();

            children.add(children.size() - 1, cadastroDia);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removerDia(MouseEvent mouseEvent) {
    }
}
