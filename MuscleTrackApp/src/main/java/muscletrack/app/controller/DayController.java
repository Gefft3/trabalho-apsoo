package muscletrack.app.controller;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import muscletrack.app.App;
import muscletrack.app.model.Ciclo;
import muscletrack.app.model.TreinoRealizado;
import muscletrack.app.utils.DateUtils;

public class DayController {
    public void diaClicado(MouseEvent mouseEvent) {
        VBox box = (VBox) mouseEvent.getSource();
        Label dia = (Label) box.getChildren().getFirst();
        DateUtils dt = new DateUtils();

        int mesAtual = dt.getMonth();
        int anoAtual = dt.getYear();

        String isoTimestamp = dt.convertYmdToIso(anoAtual + "-" + mesAtual + "-" + dia.getText());

        TreinoRealizado t = new TreinoRealizado(isoTimestamp);

        Label diaDoCiclo = (Label) box.getChildren().getLast();

        Ciclo c = App.user.getCiclo();

        if (c != null) {
            t.setTreino(c.getTreinos().get(Integer.parseInt(diaDoCiclo.getText()) - 1));
            TreinoRealizado tr = App.user.getTreinoRealizadoByData(isoTimestamp);
            if (tr == null) {
                App.user.getTreinosRealizados().add(t);
                App.fb.saveUserData(App.user);
            } else {
                System.out.println("Já há um treino cadastrado para esse dia. Só é possível editá-lo!");
            }
        }
    }
}
