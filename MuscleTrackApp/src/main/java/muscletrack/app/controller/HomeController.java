package muscletrack.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import muscletrack.app.App;
import muscletrack.app.model.*;
import muscletrack.app.utils.DateUtils;

import java.io.IOException;
import java.time.YearMonth;
import java.util.*;

public class HomeController {
    @FXML
    public GridPane calendar;
    public Button userButton;

    @FXML
    private void initialize() {
        if(!App.user.getIsLoaded()) {
            App.fb.loadUserData(App.user);
        }

        userButton.setText(App.user.getUsername());

        try {
            DateUtils dt = new DateUtils();
            int diaAtual = dt.getToday();
            int diadaSemana = dt.getWeekDay();
            int semana = dt.getMonthWeek();
            int ano = dt.getYear();
            int mes = dt.getMonth();

            YearMonth mesAtual = YearMonth.of(dt.getYear(), dt.getMonth());
            int diasMesAtual = mesAtual.lengthOfMonth();

            YearMonth mesAnterior = YearMonth.of(dt.getYear(), dt.getMonth() - 1);
            int diasMesAnterior = mesAnterior.lengthOfMonth();

            int diaInicial = diaAtual - ((semana - 1) * 7) - diadaSemana;
            int quantidadeMesAnterior = 0;

            if (diaInicial < 1) {
                quantidadeMesAnterior = -diaInicial + 1;
                diaInicial = diasMesAnterior + diaInicial;
            }

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 7; j++) {
                    VBox child = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("day-component.fxml")));
                    Label lb = (Label) child.getChildren().getFirst();
                    lb.setText(String.valueOf(diaInicial));
                    diaInicial += 1;
                    if (quantidadeMesAnterior == 0) {
                        String dataAtual;
                        if (diaInicial < 10) {
                            dataAtual = ano + "-" + mes + "-0" + (diaInicial - 1);
                        } else {
                            dataAtual = ano + "-" + mes + "-" + (diaInicial - 1);
                        }

                        VBox treinoBox = (VBox) child.getChildren().get(1);
                        Label treinoRealizado = (Label) treinoBox.getChildren().getLast();

                        TreinoRealizado tr = App.user.getTreinoRealizadoByData(dataAtual);

                        if (tr != null) {
                            Treino t = App.user.getTreinoRealizadoByData(dataAtual).getTreino();
                            treinoRealizado.setText(t.getTitulo());
                        }


                        if (diaInicial - 1 == diaAtual) {
                            lb.setStyle("-fx-opacity: 1;");
                        }
                        if (diaInicial == diasMesAtual + 1) {
                            diaInicial = 1;
                        }
                    } else {
                        if (diaInicial == diasMesAnterior + 1) {
                            diaInicial = 1;
                        }

                        quantidadeMesAnterior -= 1;
                    }
                    calendar.add(child, j, i + 1);
                }
            }

            updateTreinos(App.user);
        } catch (IOException _) {

        }
    }

    public void updateTreinos(User u) {

        Ciclo ciclo = u.getCiclo();

        if(ciclo == null){
            return;
        }

        Date dataInicioCiclo = ciclo.getInicio();

        DateUtils dt = new DateUtils();
        int diaAtual = dt.getToday();
        int diadaSemana = dt.getWeekDay();
        int semana = dt.getMonthWeek();
        int ano = dt.getYear();
        int mes = dt.getMonth();

        int diaInicial = diaAtual - ((semana - 1) * 7) - diadaSemana;
        int quantidadeMesAnterior = 0;

        if (diaInicial < 1) {
            quantidadeMesAnterior = -diaInicial + 1;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(dataInicioCiclo);
        int mesInicioCiclo = cal.get(Calendar.MONTH) + 1;

        int inicioFor = 0;

        if (mes == mesInicioCiclo) {
            inicioFor = quantidadeMesAnterior;
        }

        for (int i = inicioFor; i < 35; i++) {
            VBox dia = (VBox) calendar.getChildren().get(i + 7);
            Label diaNoCalendario = (Label) dia.getChildren().getFirst();

            int diaCalendario = Integer.parseInt(diaNoCalendario.getText());

            if(inicioFor == 0 && quantidadeMesAnterior != 0){
                mes = dt.getMonth()-1;
                quantidadeMesAnterior -= 1;
            }else if(quantidadeMesAnterior == 0){
                mes = dt.getMonth();
            }
            String dataCalendarioYMD;
            if (diaCalendario < 10) {
                dataCalendarioYMD = ano + "-" + mes + "-0" + (diaCalendario + 1);
            } else {
                dataCalendarioYMD = ano + "-" + mes + "-" + (diaCalendario + 1);
            }

            Date dataCalendario = dt.convertYmdToDate(dataCalendarioYMD);

            long diferenca = 0;
            try {
                diferenca = dt.betweenDates(dataInicioCiclo, dataCalendario);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            long diaDoCiclo = diferenca % ciclo.getDuracao();

            if(diferenca < 0){
                continue;
            }

            VBox treinos = (VBox) dia.getChildren().get(1);
            Label diaCiclo = (Label) dia.getChildren().getLast();

            diaCiclo.setText(String.valueOf(diaDoCiclo));

            Label treinoPlanejado = (Label) treinos.getChildren().getFirst();

            treinoPlanejado.setText(ciclo.getTreinos().get((int) (diaDoCiclo)).getTitulo());
        }
    }

    public void planejamentoButtonClick(ActionEvent actionEvent) {
        App.changeToPlanejamento();
    }

    public void userButtonClick(ActionEvent actionEvent) {
        App.changeToUser();
    }
}
