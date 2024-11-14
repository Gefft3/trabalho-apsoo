package muscletrack.app.controller;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RepCargaController {
    public void removerRepCarga(MouseEvent mouseEvent) {

        HBox repCarga = (HBox) ((Parent) mouseEvent.getSource()).getParent().getParent();
        VBox parent = (VBox) ((Parent) mouseEvent.getSource()).getParent().getParent().getParent();

        parent.getChildren().remove(repCarga);

    }
}
