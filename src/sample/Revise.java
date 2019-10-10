package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
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
    private ListView _list;
    @FXML
    private TextField ans;

    MediaPlayer player;




    @FXML
    public void initialize(){
        _list.getItems().clear();
        pbuilder pro = pbuilder.getInstance();
        pro.probuild2("cd Creations; ls *.mp4 2> /dev/null");
        List<String> str = pro.getStd();

        for (int i = 0; i < str.size(); i++) {
            _list.getItems().add(str.get(i).substring(0, str.get(i).length() - 4));
        }
        _list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void go(){

        Thread object1 = new Thread(new Multi1());
        object1.start();

    }

//    public void selectfile(){
//        File fileUrl = new File("Creations/");
//
//        File[] files = fileUrl.listFiles();
//
//        Random rand = new Random();
//
//        File file = files[rand.nextInt(files.length)];
//    }

    public void setMedia(){
        File fileUr = new File("Creations/");

        File[] files = fileUr.listFiles();

        Random rand = new Random();

        File file = files[rand.nextInt(files.length)];
        System.out.println(file);
        File fileUrl = new File(file.toString());
        Media video = new Media(fileUrl.toURI().toString());
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

    public void confirm() throws FileNotFoundException {
        File file = new File("Creations/"+_list.getSelectionModel().getSelectedItem()+"/term.txt");
        Scanner sc = new Scanner(file);
//        System.out.println(ans.getText());
        if(ans.getText().equals(sc.next().toString())){
            System.out.println("yes");
        }else{
//            System.out.println();
            System.out.println("no");
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
