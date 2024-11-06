package muscletrack.app.model;

public class TreinoRealizado {
    private String dateISO;
    private Treino treino;

    public TreinoRealizado(String dateISO) {
        this.dateISO = dateISO;
    }

    public String getDateISO() {
        return dateISO;
    }

    public void setDateISO(String dateISO) {
        this.dateISO = dateISO;
    }

    public Treino getTreino() {
        return treino;
    }

    public void setTreino(Treino treino) {
        this.treino = treino;
    }
}
