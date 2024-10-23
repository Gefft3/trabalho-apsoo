package trabalho.apsoo;

import java.util.ArrayList;
import java.util.List;

public class Exercicio {

    String nome;
    int qntSeries;
    List<Serie> series;

    public Exercicio(String nome, int qntSeries){
        this.nome = nome;
        this.qntSeries = qntSeries;
        this.series = new ArrayList<Serie>(qntSeries);
    }

    public void AddSerie(Serie s){
        this.series.add(s);
    }

    @Override
    public String toString(){
        return " Exercicio: " + this.nome + ", " + this.qntSeries + " \n" + series.toString();
    }
}
