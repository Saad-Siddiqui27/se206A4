package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    MediaPlayer player ;




//    @FXML
//    public void initialize(){
//        _list.getItems().clear();
//        pbuilder pro = pbuilder.getInstance();
//        pro.probuild2("cd Creations; ls *.mp4 2> /dev/null");
//        List<String> str = pro.getStd();
//
//        for (int i = 0; i < str.size(); i++) {
//            _list.getItems().add(str.get(i).substring(0, str.get(i).length() - 4));
//        }
//        _list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//    }

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
        File file2 = new File(file.toString()+"/term.txt");
        Scanner sc = new Scanner(file2);
        if(ans.getText().equals(sc.next())){

            p=p+10;
           points.setText("Points: "+ p);
           confirmButton.setDisable(true);
           next.setDisable(false);
           wrong.setText("Correct Answer");
        }else{
            System.out.println("no");
            confirmButton.setDisable(true);
            next.setDisable(false);
            wrong.setText("Wrong Answer");
        }
    }


    public void switchToMain() {
        Platform.runLater(new Multi() {
            @Override
            public void run() {
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
