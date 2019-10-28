package sample;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * a class which abstracts the functionality for switching scenes with a transition.
 */
public class SwitchScenes {

    private Pane _pane;

    public SwitchScenes( Pane pane){

        _pane = pane;

    }

    /**
     * the generic switch scene method which includes the code which is used in all other classes to switch scenes.
     * @param fxml the scene to be loaded.
     * @throws IOException
     */
    public void switchScenes(String fxml) throws IOException {

        /**
         * this adds a fade transition when switching the scenes.
         * code inspired by youtube channel named Genuine Coder.
         * url: https://www.youtube.com/watch?v=rMQrXSYHl8w&t=574s
         */
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(_pane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        fadeTransition.setOnFinished((ActionEvent event)->{
            try {
                Parent pane = FXMLLoader.load(getClass().getResource(fxml));
                Stage stage = (Stage) _pane.getScene().getWindow();

                Scene scene = new Scene(pane);
                stage.setScene(scene);
                stage.sizeToScene();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        fadeTransition.play();
    }
}
