package trabalho.apsoo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import trabalho.apsoo.database.Firebase;

import java.io.IOException;
import javafx.stage.Stage;


public class Main extends Application {

  @Override
  public void start(Stage stage) throws IOException{
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 320, 240);

    stage.setTitle("Muscle Track");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {

    launch();

//    SwingUtilities.invokeLater(() -> {
//      Application app = new Application();
//      app.setVisible(true);
//    });

//    SwingUtilities.invokeLater(() -> {
//
//      Firebase fb = new Firebase(System.getenv("FIREBASE_API_KEY"), System.getenv("AUTH_BASE_URL"),
//              System.getenv("FIREBASE_BASE_URL"), System.getenv("AUTH_REFRESH_URL"));
//
//      User user = new User();
//
//      LoginPage lp = new LoginPage(fb, user);
//      lp.setVisible(true);
//
//
//    });

   // User user = new User("geffte.caetano@ufms.br", "teste123");

//    fb.registerUser(user);

    //fb.authenticateUser(user);

    //fb.exchangeToken(user);

//    user.setCiclo(new Ciclo(1, new Date()));
//
//    Treino t = new Treino("peito", 1);
//
//    Exercicio e = new Exercicio("TESTE TESTE", 3);
//
//    Serie s1 = new Serie(100, 10);
//    Serie s2 = new Serie(15, 10);
//    Serie s3 = new Serie(20, 10);
//
//    e.addSerie(s1);
//    e.addSerie(s2);
//    e.addSerie(s3);
//
//    t.addExercicio(e);
//
//    user.getCiclo().addTreino(t);

//    System.out.println(user.toFirebaseRequestBody());
//   fb.saveUserData(user);
//    fb.loadUserData(user);

//    System.out.println(user);

  }

}
