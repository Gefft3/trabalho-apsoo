package trabalho.apsoo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ciclo {

    int duracao;
    Date inicio;
    List<Treino> treinos;

    public Ciclo(){

    }

    public Ciclo(int duracao, Date inicio){
        this.duracao = duracao;
        this.inicio = inicio;
        this.treinos = new ArrayList<>(duracao);
    }

    void AddTreino(Treino t){
        this.treinos.add(t);
    }

    @Override
    public String toString(){
        return " Ciclo: " + this.duracao + ", " + this.inicio + " \n" + treinos.toString();
    }

}
