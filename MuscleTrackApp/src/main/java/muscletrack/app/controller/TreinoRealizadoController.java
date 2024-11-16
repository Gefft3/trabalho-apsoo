package muscletrack.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import muscletrack.app.App;
import muscletrack.app.components.CadastroDia;
import muscletrack.app.components.RepCarga;
import muscletrack.app.components.TreinoForm;
import muscletrack.app.model.Exercicio;
import muscletrack.app.model.Serie;
import muscletrack.app.model.Treino;
import muscletrack.app.model.TreinoRealizado;
import java.util.List;

public class TreinoRealizadoController {
    @FXML
    public VBox treinoRealizadoBox;

    public void initialize(){

        Treino t;

        CadastroDia cd = new CadastroDia();
        cd.setDia("Hoje");
        if(App.treinoRealizadoAtual != null){
            t = App.treinoRealizadoAtual.getTreino();

            if(t != null){
                cd.setTitulo(t.getTitulo());
                cd.setAgrupamento(t.getAgrupamento());

                List<Exercicio> exercicioList = t.getExercicios();

                for(Exercicio e : exercicioList){

                    TreinoForm tf = new TreinoForm();
                    tf.setTitulo(e.getNome());

                    List<Serie> serieList = e.getSeries();

                    for(Serie s: serieList){
                        RepCarga rc = new RepCarga();
                        rc.setCarga(s.getPeso());
                        rc.setRep(s.getRepeticoes());

                        tf.addSerie(rc);
                    }

                    cd.addTreinoForm(tf);
                }

            }

        }

        treinoRealizadoBox.getChildren().add(treinoRealizadoBox.getChildren().size() -1, cd.getCadastroDiaPane());

    }

    public void registrarTreinoRealizado() {

        Treino t = new Treino();

        GridPane cadastroDia = (GridPane) treinoRealizadoBox.getChildren().getFirst();

        ComboBox agrupamentoSelect = (ComboBox) ((VBox)cadastroDia.getChildren().getFirst()).getChildren().getLast();
        TextField tituloField = (TextField) ((VBox)cadastroDia.getChildren().getFirst()).getChildren().get(2);

        String agrupamento = (String) agrupamentoSelect.getValue();
        t.setAgrupamento(agrupamento);
        t.setTitulo(tituloField.getText());

        ScrollPane sp = (ScrollPane) cadastroDia.getChildren().getLast();
        HBox treinoFormsPane = (HBox) sp.getContent();

        int qntExercicio = 0;

        for(int j = 0; j < treinoFormsPane.getChildren().size() - 1; j++){
            VBox exercicioFormBox = (VBox) treinoFormsPane.getChildren().get(j);

            TextField nomeExercicioField = (TextField) ((GridPane) exercicioFormBox.getChildren().getFirst()).getChildren().getFirst();

            Exercicio e = new Exercicio();

            e.setNome(nomeExercicioField.getText());

            ScrollPane sp2 = (ScrollPane) exercicioFormBox.getChildren().getLast();
            VBox seriesBox = (VBox) sp2.getContent();

            int qntSeries = 0;

            for(int k = 0 ; k < seriesBox.getChildren().size() - 1; k++){

                HBox repCargaBox = (HBox) seriesBox.getChildren().get(k);

                Serie s = new Serie();

                TextField repField = (TextField) ((HBox) repCargaBox.getChildren().getFirst()).getChildren().getFirst();
                TextField cargaField = (TextField) ((HBox) repCargaBox.getChildren().get(1)).getChildren().getFirst();

                s.setRepeticoes(Integer.parseInt(repField.getText()));
                s.setPeso(Double.parseDouble(cargaField.getText()));

                e.addSerie(s);
                qntSeries += 1;
            }

            e.setQntSeries(qntSeries);

            t.addExercicio(e);

            qntExercicio += 1;

        }

        t.setQntExercicios(qntExercicio);

        if(App.treinoRealizadoIndex == -1){
            System.out.println("Não tinha entao vai criar um novo");
            TreinoRealizado tr = new TreinoRealizado(App.treinoRealizadoTimestamp);
            tr.setTreino(t);
            App.user.getTreinosRealizados().add(tr);
        }else{
            System.out.println("Já tinha entao vai editar");
            App.user.getTreinosRealizados().get(App.treinoRealizadoIndex).setTreino(t);
        }

        App.fb.saveUserData(App.user);

        App.treinoRealizadoIndex = -1;
        App.treinoRealizadoTimestamp = "";
        App.treinoRealizadoAtual = null;

        App.changeToHome();
    }

    public void onCancelarButtonClick(ActionEvent actionEvent) {
        App.treinoRealizadoAtual = null;
        App.changeToHome();
    }

}
