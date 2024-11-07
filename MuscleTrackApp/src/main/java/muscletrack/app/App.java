package muscletrack.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import muscletrack.app.database.Firebase;
import muscletrack.app.model.User;
import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    public static User user;
    public static Firebase fb;
    private static Scene scene;
    private static Parent loginPage;
    private static Parent registerPage;
    private static Parent homePage;
    private static Parent userPage;
    private static Stage stage;


    @Override
    public void start(Stage stage) throws IOException {

        user = new User();
        fb = new Firebase(System.getenv("FIREBASE_API_KEY"), System.getenv("AUTH_BASE_URL"),
                System.getenv("FIREBASE_BASE_URL"), System.getenv("AUTH_REFRESH_URL"));

        loginPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login-view.fxml")));
        registerPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("register-view.fxml")));
        userPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("user-view.fxml")));

        scene = new Scene(loginPage);
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("styles.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("login.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("register.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("home.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("user.css")).toExternalForm());

        App.stage = stage;

        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void changeToRegister() {
        scene.setRoot(registerPage);
        App.stage.setWidth(1200);
    }

    public static void changeToLogin() {
        scene.setRoot(loginPage);
    }

    public static void changeToPlanejamento() {
        System.out.println("GEFFTE TEM QUE FAZER A TELA DE CADASTRO DE PLANEJAMENTO");
    }

    public static void changeToUser() {
        scene.setRoot(userPage);
    }

    public static void changeToHome() {
        try {
            homePage = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("home-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene.setRoot(homePage);
        App.stage.setMaximized(true);

    }

}