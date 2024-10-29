module MuscleTrack {
    requires javafx.fxml;
    requires javafx.controls;
    requires org.json;
    requires java.desktop;

    opens trabalho.apsoo to javafx.fxml;
    exports trabalho.apsoo;
}