package sample;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * this class sets up a splash screen for the application.
 */
public class splashController implements Initializable {

    @FXML
    private Circle c1;
    @FXML
    private Circle c2;
    @FXML
    private Circle c3;
    @FXML
    private Pane rootpane;

    /**
     * a helper method. this method allows the circles to be rotated which creates an animation.
     * @param c the Circle
     * @param reverse
     * @param angle how much to rotate
     * @param duration how long to rotate
     */
    public void setRotate(Circle c,boolean reverse, int angle, int duration) {

        RotateTransition rt = new RotateTransition(Duration.seconds(duration), c);

        rt.setAutoReverse(reverse);

        rt.setByAngle(angle);
        rt.setDelay(Duration.seconds(0));
        rt.setRate(3);
        rt.setCycleCount(18);
        rt.play();
    }

    /**
     * initialises the scene and sets up the animation.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /**
         * code inspired from a youtube. channel name Rashid coder
         * url: https://www.youtube.com/watch?v=Fy0ZVF7EPC4&t=136s
         */
        new SplashScreen().start();
        setRotate(c1,true,360,10);
        setRotate(c2,true,180,18);
        setRotate(c3,true,145,24);

    }

    /**
     * Gui concurreny in order to change to the next scene when the splash screen allotted time has finished.
     * this code was inspired from youtube channel named Genuine Coder
     * url: https://www.youtube.com/watch?v=muz6QLIgrC0&t=244s
     */
    public class SplashScreen extends Thread{

        @Override
        public void run() {
            try {
                Thread.sleep(4000);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/Fxml/MainMenu.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("VARPEDIA");
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();

                        rootpane.getScene().getWindow().hide();

                    }
                });
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
