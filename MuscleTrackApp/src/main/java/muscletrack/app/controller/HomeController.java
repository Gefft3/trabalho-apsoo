package muscletrack.app.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import muscletrack.app.App;

import java.io.IOException;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class HomeController {
    @FXML
    public GridPane calendar;


    @FXML
    private void initialize(){
        try{
            Calendar cal = Calendar.getInstance();
            int diaAtual = cal.get(Calendar.DAY_OF_MONTH);
            int diadaSemana = cal.get(Calendar.DAY_OF_WEEK) - 1; // 0 à 6 (dom a sab)
            int semana = cal.get(Calendar.WEEK_OF_MONTH);

            System.out.println(cal.get(Calendar.MONTH));

            YearMonth mesAtual = YearMonth.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
            int diasMesAtual = mesAtual.lengthOfMonth();

            YearMonth mesAnterior = YearMonth.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
            int diasMesAnterior = mesAnterior.lengthOfMonth();

            int diaInicial = diaAtual - ((semana-1) * 7) - diadaSemana;
            int quantidadeMesAnterior = 0;

            if(diaInicial < 1){
                quantidadeMesAnterior = -diaInicial + 1;
                diaInicial = diasMesAnterior + diaInicial;
            }

            for (int i = 0; i < 5; i++){
                for(int j = 0; j < 7; j++){
                    VBox child = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("day-component.fxml")));
                    Label lb = (Label) child.getChildren().getFirst();
                    lb.setText(String.valueOf(diaInicial));
                    diaInicial += 1;
                    if(quantidadeMesAnterior == 0) {
                        if(diaInicial - 1 == diaAtual){
                            lb.setStyle("-fx-opacity: 1;");
                        }
                        if (diaInicial == diasMesAtual + 1) {
                            diaInicial = 1;
                        }
                    }else{
                        if (diaInicial == diasMesAnterior + 1) {
                            diaInicial = 1;
                        }

                        quantidadeMesAnterior -=1;
                    }
                    calendar.add(child, j,i+1);
                }
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
