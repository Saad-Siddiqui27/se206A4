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


    MediaPlayer player ;




    @FXML
    public void initialize(){
        File fileUr = new File("Creations/");

        File[] files = fileUr.listFiles();


        if(files.length==0){
            next.setDisable(true);
            noCreations.setText("You have no Creations to Revise");
            confirmButton.setDisable(true);

        }

        ans.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        confirm();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

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

    public class Multi1 implements Runnable{

        @Override
        public void run() {
            setMedia();
        }
    }

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
                System.out.println("no");
                confirmButton.setDisable(true);
                next.setDisable(false);
                wrong.setText("Wrong Answer");
            }
        }
    }


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
