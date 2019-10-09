package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.print.DocFlavor;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ActivelearningController {
    @FXML private Pane _learning;
    @FXML private ListView _videos;
    @FXML private MediaView _view;
    @FXML private TextField _answer;
    MediaPlayer player;
    List<String> path = new ArrayList<>();
    @FXML
    public void initialize() {

        _videos.getItems().clear();
        pbuilder pro = pbuilder.getInstance();
        pro.probuild2("cd Activelearning;"+"find . -type f -name \"*.mp4\" 2> /dev/null");
        List<String> str = pro.getStd();

        for (int i = 0; i < str.size(); i++) {
        int j = 0;
        int num = 0;
        while(num < 2){
            if(str.get(i).substring(j,j+1).equals("/")){
                num = num + 1;
            }
            j = j + 1;
        }
        _videos.getItems().add(str.get(i).substring(j, str.get(i).length() - 4));
        path.add(i,str.get(i).substring(0,j));
        }
        _videos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    public void switchToMain() {
        Platform.runLater(new Multi() {
            @Override
            public void run() {
                SwitchScenes sw = new SwitchScenes(_learning);

                try {
                    sw.switchScenes("MainMenu.fxml");
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

    public void play(){
        Thread object1 = new Thread(new Multi1());
        object1.start();
    }
    public class Multi1 implements Runnable{

        @Override
        public void run() {
            setMedia();
        }
    }

    public void setMedia(){
        int a = Integer.parseInt(String.valueOf(_videos.getSelectionModel().getSelectedIndices()).substring(1,2));
        File fileUrl = new File("Activelearning/"+path.get(a).substring(2)+_videos.getSelectionModel().getSelectedItem().toString()+".mp4");
        Media video = new Media(fileUrl.toURI().toString());
        player = new MediaPlayer(video);
        player.setAutoPlay(true);
        _view.setMediaPlayer(player);
    }

    public void check(){
        int a = Integer.parseInt(String.valueOf(_videos.getSelectionModel().getSelectedIndices()).substring(1,2));
        System.out.println(path.get(a).substring(2));
        if(path.get(a).substring(2,path.get(a).length()-1).equals(_answer.getText())){
            alert("Correct");
        } else{alert("wrong");}
    }

    public void alert(String a){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Your answer is " + a);
        alert.setTitle("Result");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

            }
        });
    }
}
