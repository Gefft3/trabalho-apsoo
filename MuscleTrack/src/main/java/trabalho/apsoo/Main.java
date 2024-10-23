package trabalho.apsoo;


import java.util.Date;

public class Main {
    public static void main(String[] args) {

        User user = new User("teste", "teste@teste.br", "teste");

        user.Authenticate();

        user.ciclo = new Ciclo(1, new Date());

        Treino t = new Treino("peito", 1);

        Exercicio e = new Exercicio("Supino máquina", 3);

        Serie s1 = new Serie(10, 10);
        Serie s2 = new Serie(15, 10);
        Serie s3 = new Serie(20, 10);

        e.AddSerie(s1);
        e.AddSerie(s2);
        e.AddSerie(s3);

        t.addExercicio(e);

        user.ciclo.AddTreino(t);


        System.out.println(user);
    }

}
