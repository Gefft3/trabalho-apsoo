package muscletrack.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import muscletrack.app.database.Firebase;
import muscletrack.app.model.TreinoRealizado;
import muscletrack.app.model.User;
import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    public static User user;
    public static Firebase fb;
    public static TreinoRealizado treinoRealizadoAtual;
    public static int treinoRealizadoIndex = -1;
    public static String treinoRealizadoTimestamp;
    private static Scene scene;
    private static Parent loginPage;
    private static Parent registerPage;
    private static Parent homePage;
    private static Parent userPage;
    private static Parent planejamentoPage;
    private static Parent cadastroTreinoRealizadoPage;
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
        scene.getStylesheets().add(Objects.requireNonNull(App.class.getResource("planejamento.css")).toExternalForm());

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
    }

    public static void changeToLogin() {
        scene.setRoot(loginPage);
    }

    public static void changeToPlanejamento() {

        try{
            planejamentoPage = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("planejamento-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene.setRoot(planejamentoPage);
    }

    public static void changeToUser() {
        try{
            userPage = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("user-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

    public static void changeToCadastroTreinoRealizado() {
        try{
            cadastroTreinoRealizadoPage = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("registrar-treino-view.fxml")));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene.setRoot(cadastroTreinoRealizadoPage);
    }

}