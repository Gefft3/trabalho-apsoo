package trabalho.apsoo;

import java.util.Date;

public class Main {
  public static void main(String[] args) {

    Firebase fb = new Firebase(System.getenv("FIREBASE_API_KEY"), System.getenv("AUTH_BASE_URL"),
        System.getenv("FIREBASE_BASE_URL"));

    User user = new User("Luiz", "teste", "teste");

    fb.authenticateUser(user);

    user.setCiclo(new Ciclo(5, new Date()));

    Treino t = new Treino("peito", 2);

    Exercicio e = new Exercicio("TESTE TESTE", 3);

    Serie s1 = new Serie(100, 10);
    Serie s2 = new Serie(15, 10);
    Serie s3 = new Serie(20, 10);

    e.addSerie(s1);
    e.addSerie(s2);
    e.addSerie(s3);

    t.addExercicio(e);
    t.addExercicio(e);

    user.getCiclo().addTreino(t);
    user.getCiclo().addTreino(t);
    user.getCiclo().addTreino(t);
    user.getCiclo().addTreino(t);
    user.getCiclo().addTreino(t);

    System.out.println(fb.saveUserData(user));
  }

}
