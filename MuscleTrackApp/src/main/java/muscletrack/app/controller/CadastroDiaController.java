package muscletrack.app.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import muscletrack.app.App;

import java.io.IOException;
import java.util.Objects;

public class CadastroDiaController {
    @FXML
    public ComboBox comboBox;
    @FXML
    public HBox treinoFormsPane;

    public void comboBoxSelected(ActionEvent actionEvent) {
        System.out.println(comboBox.getValue());
    }

    public void adicionarFormTreino() {
        try {
            VBox treinoForm = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("treino_form_box.fxml")));

            ObservableList<Node> children = treinoFormsPane.getChildren();

            children.add(children.size() - 1, treinoForm);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
