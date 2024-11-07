package muscletrack.app.controller;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import muscletrack.app.App;
import muscletrack.app.model.TreinoRealizado;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DayController {
    public void diaClicado(MouseEvent mouseEvent) {
        VBox box = (VBox) mouseEvent.getSource();
        Label dia = (Label) box.getChildren().getFirst();
        Calendar cal = Calendar.getInstance();

        int mesAtual = cal.get(Calendar.MONTH) + 1;
        int anoAtual = cal.get(Calendar.YEAR);

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        d.setTimeZone(tz);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(tz);
        String isoTimestamp;
        try {
            isoTimestamp = df.format(d.parse(anoAtual + "-" + mesAtual + "-" + dia.getText()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        TreinoRealizado t = new TreinoRealizado(isoTimestamp);

        Label diaDoCiclo = (Label) box.getChildren().getLast();

        t.setTreino(App.user.getCiclo().getTreinos().get(Integer.parseInt(diaDoCiclo.getText()) - 1));

        TreinoRealizado tr = App.user.getTreinoRealizadoByData(isoTimestamp);
        if (tr == null) {
            App.user.getTreinosRealizados().add(t);
            App.fb.saveUserData(App.user);
        } else {
            System.out.println("Já há um treino cadastrado para esse dia. Só é possível editá-lo!");
        }


    }
}
