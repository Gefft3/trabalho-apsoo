package muscletrack.app.controller;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import muscletrack.app.App;
import muscletrack.app.model.Ciclo;
import muscletrack.app.model.TreinoRealizado;
import muscletrack.app.utils.DateUtils;

import java.io.IOException;

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
            try {
                if(dt.betweenDates(dt.convertYmdToDate(isoTimestamp), c.getInicio()) <= 1){
                    t.setTreino(c.getTreinos().get(Integer.parseInt(diaDoCiclo.getText())));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            TreinoRealizado tr = App.user.getTreinoRealizadoByData(isoTimestamp);
            if (tr == null) {
                App.treinoRealizadoAtual = t;
                App.treinoRealizadoIndex = -1;
                App.treinoRealizadoTimestamp = isoTimestamp;
                App.changeToCadastroTreinoRealizado();
            } else {
                App.treinoRealizadoAtual = tr;
                App.treinoRealizadoIndex = App.user.getTreinosRealizados().indexOf(tr);
                App.treinoRealizadoTimestamp = isoTimestamp;
                App.changeToCadastroTreinoRealizado();
            }
        }
    }
}
