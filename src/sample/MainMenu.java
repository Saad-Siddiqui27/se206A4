package sample;


import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class MainMenu {

    @FXML
    Pane _Menu;





    public void switchScenes(String fxml) throws IOException {

        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage) _Menu.getScene().getWindow();

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.sizeToScene();
    }


    public void switchToCreate() {
        SwitchScenes sw = new SwitchScenes(_Menu);
        try {
            sw.switchScenes("/Fxml/CreateAudio.fxml");
        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    public void switchToAudio() {


        SwitchScenes sw = new SwitchScenes(_Menu);


        try {
            sw.switchScenes("/Fxml/Directory.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public void switchToPlay() {

            SwitchScenes sw = new SwitchScenes(_Menu);

            try {
                sw.switchScenes("/Fxml/Play.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public void switchToRevise() {

        SwitchScenes sw = new SwitchScenes(_Menu);

        try {
            sw.switchScenes("/Fxml/Revise.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}







