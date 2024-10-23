package trabalho.apsoo;

import java.util.ArrayList;
import java.util.List;

public class Treino {

    String titulo;
    int qntExercicios;
    List<Exercicio> exercicios;

    public Treino(String titulo, int qntExercicios){
        this.titulo = titulo;
        this.qntExercicios = qntExercicios;
        this.exercicios = new ArrayList<Exercicio>(qntExercicios);
    }

    public void addExercicio(Exercicio e){
        this.exercicios.add(e);
    }

    @Override
    public String toString(){
        return " Treino: " + this.titulo + ", " + this.qntExercicios + " \n" + exercicios.toString();
    }

}
