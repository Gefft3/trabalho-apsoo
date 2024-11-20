package muscletrack.app.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import muscletrack.app.App;
import muscletrack.app.components.CadastroDia;
import muscletrack.app.components.RepCarga;
import muscletrack.app.components.TreinoForm;
import muscletrack.app.model.Ciclo;
import muscletrack.app.model.Exercicio;
import muscletrack.app.model.Serie;
import muscletrack.app.model.Treino;
import muscletrack.app.utils.DateUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PlanejamentoController {
    @FXML
    public DatePicker datePicker;
    @FXML
    public Label errorLabel;
    private int duracao;
    @FXML
    public VBox planejamentoFormBox;

    public void initialize(){
        Ciclo ciclo = App.user.getCiclo();

        if(ciclo != null){

            this.duracao = ciclo.getDuracao();
            this.datePicker.setValue(ciclo.getInicioLocalDate());

            List<Treino> treinos = ciclo.getTreinos();
            if(treinos != null){
                int dia = 1;
                for (Treino t : treinos){

                    CadastroDia cd = new CadastroDia();
                    cd.setDia(dia);
                    cd.setTitulo(t.getTitulo());
                    cd.setAgrupamento(t.getAgrupamento());
                    List<Exercicio> exercicios = t.getExercicios();

                    if(exercicios != null){
                        for(Exercicio e : exercicios){

                            TreinoForm tf = new TreinoForm();
                            tf.setTitulo(e.getNome());

                            List<Serie> series = e.getSeries();
                            if(series != null){
                                for(Serie s: series){

                                    RepCarga rc = new RepCarga();
                                    rc.setCarga(s.getPeso());
                                    rc.setRep(s.getRepeticoes());

                                    tf.addSerie(rc);
                                }
                            }

                            cd.addTreinoForm(tf);

                        }
                    }

                    dia += 1;

                    planejamentoFormBox.getChildren().add(planejamentoFormBox.getChildren().size() -1 ,cd.getCadastroDiaPane());
                }
            }
        }
    }


    public void onCancelarButtonClick(ActionEvent actionEvent) {
        App.changeToHome();
    }

    public void onCadastrarButtonClick(ActionEvent actionEvent) {

        if(datePicker.getValue() == null){
            errorLabel.setVisible(true);
            return;
        }

        Ciclo c = new Ciclo();
        c.setDuracao(this.duracao);

        if(c.getInicio() == null){
            LocalDate ld = datePicker.getValue();
            c.setInicio(Date.from(ld.atTime(20, 0, 0).atZone(ZoneId.systemDefault()).toInstant()));
        }

        ObservableList<Node> children = planejamentoFormBox.getChildren();

        if(children.size() > 2){
            for(int i = 0; i < duracao; i++){

                Treino t = new Treino();
                GridPane cadastroDia = (GridPane) planejamentoFormBox.getChildren().get(i + 1);

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

                c.getTreinos().add(t);
            }

            App.user.setCiclo(c);

            c.getInicio().setHours(20);
            c.getInicio().setMinutes(0);
            c.getInicio().setSeconds(0);

            if(!App.fb.saveUserData(App.user)){
                System.out.println("Erro ao salvar alterações");
            }else{
                App.changeToHome();
            }

        }
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

        ObservableList<Node> children = planejamentoFormBox.getChildren();

        if(children.size() > 2){
            planejamentoFormBox.getChildren().remove(planejamentoFormBox.getChildren().size() - 2);
            this.duracao -= 1;
        }
    }
}
