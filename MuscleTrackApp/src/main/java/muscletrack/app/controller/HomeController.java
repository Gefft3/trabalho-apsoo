package muscletrack.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import muscletrack.app.App;
import muscletrack.app.model.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.*;

public class HomeController {
    @FXML
    public GridPane calendar;

    @FXML
    private void initialize() {

        App.fb.loadUserData(App.user);

//        Ciclo c = new Ciclo(7, new Date());
//        Treino t1= new Treino("peito", 1);
//        Treino t7= new Treino("peito1", 1);
//        Treino t2= new Treino("peito2", 1);
//        Treino t3= new Treino("peito3", 1);j
//        Treino t4= new Treino("peito4", 1);
//        Treino t5= new Treino("peito5", 1);
//        Treino t6= new Treino("peito6", 1);
//        Exercicio ex = new Exercicio("supino", 1);
//        Serie s = new Serie(10, 10);
//        ex.addSerie(s);
//        t1.addExercicio(ex);
//        t7.addExercicio(ex);
//        t2.addExercicio(ex);
//        t3.addExercicio(ex);
//        t4.addExercicio(ex);
//        t5.addExercicio(ex);
//        t6.addExercicio(ex);
//        c.getTreinos().add(t1);
//        c.getTreinos().add(t7);
//        c.getTreinos().add(t2);
//        c.getTreinos().add(t3);
//        c.getTreinos().add(t4);
//        c.getTreinos().add(t5);
//        c.getTreinos().add(t6);
//
//        App.user.setCiclo(c);
//
//        App.fb.saveUserData(App.user);


        try {
            Calendar cal = Calendar.getInstance();
            int diaAtual = cal.get(Calendar.DAY_OF_MONTH);
            int diadaSemana = cal.get(Calendar.DAY_OF_WEEK) - 1; // 0 à 6 (dom a sab)
            int semana = cal.get(Calendar.WEEK_OF_MONTH);
            int ano = cal.get(Calendar.YEAR);
            int mes = cal.get(Calendar.MONTH) + 1;

            YearMonth mesAtual = YearMonth.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
            int diasMesAtual = mesAtual.lengthOfMonth();

            YearMonth mesAnterior = YearMonth.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
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
                        if(diaInicial < 10) {
                            dataAtual = ano + "-" + mes + "-0" + String.valueOf(diaInicial-1);
                        }else{
                            dataAtual = ano + "-" + mes + "-" + String.valueOf(diaInicial-1);
                        }

                        VBox treinoBox = (VBox) child.getChildren().get(1);
                        Label treinoRealizado = (Label) treinoBox.getChildren().getLast();

                        Treino t = App.user.getTreinoRealizadoByData(dataAtual).getTreino();
                        if(t != null){
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
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void updateTreinos(User u) {

        int diaDoCiclo = 1;
        Ciclo ciclo = u.getCiclo();

        for (int i = 0; i < 35; i++) {
            VBox dia = (VBox) calendar.getChildren().get(i+7);
            VBox treinos = (VBox) dia.getChildren().get(1);
            Label diaCiclo = (Label) dia.getChildren().getLast();

            diaCiclo.setText(String.valueOf(diaDoCiclo));

            Label treinoPlanejado = (Label) treinos.getChildren().getFirst();

            if(ciclo != null){
                treinoPlanejado.setText(ciclo.getTreinos().get(diaDoCiclo-1).getTitulo());
                diaDoCiclo += 1;

                if (diaDoCiclo > u.getCiclo().getDuracao()){
                    diaDoCiclo = 1;
                }
            }


        }
    }

    public void planejamentoButtonClick(ActionEvent actionEvent) {
        App.changeToPlanejamento();
    }

    public void userButtonClick(ActionEvent actionEvent) {
        App.changeToUser();
    }
}
