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

public class splashController implements Initializable {

    @FXML
    private Circle c1;
    @FXML
    private Circle c2;
    @FXML
    private Circle c3;
    @FXML
    private Pane rootpane;


//    @FXML
//    private void initialize(){
//
//        setRotate(c1,true,360,10);
//        setRotate(c2,true,180,18);
//        setRotate(c3,true,145,24);
//
//
//
//    }

    public void setRotate(Circle c,boolean reverse, int angle, int duration) {

        RotateTransition rt = new RotateTransition(Duration.seconds(duration), c);

        rt.setAutoReverse(reverse);

        rt.setByAngle(angle);
        rt.setDelay(Duration.seconds(0));
        rt.setRate(3);
        rt.setCycleCount(18);
        rt.play();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        new SplashScreen().start();
        setRotate(c1,true,360,10);
        setRotate(c2,true,180,18);
        setRotate(c3,true,145,24);



    }

    public class SplashScreen extends Thread{

        @Override
        public void run() {
            try {
                Thread.sleep(0);

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
