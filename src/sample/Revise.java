package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Revise {

    @FXML
    private Pane revise;
    @FXML
    private MediaView media;
    @FXML
    private TextField ans;
    @FXML private Label points;
    @FXML private Label apoints;
    @FXML private Button confirmButton;
    @FXML private Button next;
    @FXML private Label wrong;
    @FXML private Label noCreations;

    private MediaPlayer player ;


    /**
     * initialise method which initialises the different aspect of the scene when the scene loads up.
     * this method disables the take quiz and confirm answer buttons if no creations exist. this is because there would be no creation to take a quiz of
     */

    @FXML
    public void initialize(){
        File fileUr = new File("Creations/");

        File[] files = fileUr.listFiles();


        if(files.length==0){
            next.setDisable(true);
            noCreations.setText("You have no Creations to Revise");
            confirmButton.setDisable(true);

        }

        //event handler to take action if enter is pressed.
        ans.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    confirm();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     *this method is linked to the take quiz button. it starts up the quiz by randomly selecting any of the creations to play.
     * it does not allow the user to go to the next question untill the answer for current question is not provided by disabling the next button.
     */
    public void go(){

        if(player!=null){
            player.stop();
        }

        Thread object1 = new Thread(new Multi1());
        object1.start();
        File fileUr = new File("Creations/");

        File[] files = fileUr.listFiles();
        next.setText("Next");
        next.setDisable(true);
        confirmButton.setDisable(false);
        wrong.setText("");

    }

    /**
     * this is a helper method which sets up the media for the randomly selected creations so that the quiz can be played.
     */
    private File file;
    public void setMedia(){
        File fileUr = new File("Creations/");

        File[] files = fileUr.listFiles();

        Random rand = new Random();

        file = files[rand.nextInt(files.length)];
        String s = file.toString().substring(9);
        Media video = new Media(file.toURI().toString()+s+"revision.mp4");
        player = new MediaPlayer(video);
        player.setAutoPlay(true);
        media.setMediaPlayer(player);
    }

    /**
     * helper class to implement GUI concurrency
     */
    public class Multi1 implements Runnable{

        @Override
        public void run() {
            setMedia();
        }
    }

    /**
     * this method is linked to the confirm button and checks if the answer provided by
     * the user is correct or not. it keeps track of the number of correct answers given by maintaining a
     * points system.
     */
    int p = 0;
    public void confirm() throws FileNotFoundException {
        if(ans.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please enter an answer");
            alert.setTitle("Answer not provided");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                }
            });
        }else {

            File file2 = new File(file.toString() + "/term.txt");
            Scanner sc = new Scanner(file2);
            if (ans.getText().equals(sc.next())) {

                p = p + 10;
                points.setText("Points: " + p);
                confirmButton.setDisable(true);
                next.setDisable(false);
                wrong.setText("Correct Answer");
            } else {
                confirmButton.setDisable(true);
                next.setDisable(false);
                wrong.setText("Wrong Answer");
            }
        }
    }

    /**
     *  method which switches scene to the main menu scene. this is done by using the functionality of the SwitchScenes singleton class.
     */
    public void switchToMain() {
        Platform.runLater(new Multi() {
            @Override
            public void run() {
                if(player!=null){
                    player.stop();
                }
                SwitchScenes sw = new SwitchScenes(revise);
                try {
                    sw.switchScenes("/Fxml/MainMenu.fxml");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public class Multi implements Runnable {

        @Override
        public void run() {

        }
    }


}
