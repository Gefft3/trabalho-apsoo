module teste.muscletrackapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens muscletrack.app to javafx.fxml;
    exports muscletrack.app;
    exports muscletrack.app.controller;
    opens muscletrack.app.controller to javafx.fxml;
}