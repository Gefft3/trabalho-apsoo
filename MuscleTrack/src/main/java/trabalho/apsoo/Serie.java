package trabalho.apsoo;

public class Serie {

    int repeticoes;
    double peso;

    public Serie(double peso, int repeticoes) {
        this.peso = peso;
        this.repeticoes = repeticoes;
    }

    @Override
    public String toString(){
        return " Serie: " + this.repeticoes + ", " + this.peso;
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
