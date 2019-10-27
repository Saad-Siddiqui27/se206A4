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

    /**
     * method which switches scene to the Creating Audio scene. this is done by using the functionality of the SwitchScenes singleton class.
     */
    public void switchToCreate() {
        SwitchScenes sw = new SwitchScenes(_Menu);
        try {
            sw.switchScenes("/Fxml/CreateAudio.fxml");
        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    /**
     * method which switches scene to the Creating Video scene. this is done by using the functionality of the SwitchScenes singleton class.
     */
    public void switchToVideo() {


        SwitchScenes sw = new SwitchScenes(_Menu);


        try {
            sw.switchScenes("/Fxml/Directory.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * method which switches scene to the playing creations scene. this is done by using the functionality of the SwitchScenes singleton class.
     */
        public void switchToPlay() {

            SwitchScenes sw = new SwitchScenes(_Menu);

            try {
                sw.switchScenes("/Fxml/Play.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    /**
     * method which switches scene to the quiz scene. this is done by using the functionality of the SwitchScenes singleton class.
     */
    public void switchToRevise() {

        SwitchScenes sw = new SwitchScenes(_Menu);

        try {
            sw.switchScenes("/Fxml/Revise.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}







