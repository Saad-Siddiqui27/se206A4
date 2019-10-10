package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Play {

    public Play(){

    }


    @FXML
    private Pane _Play;
    @FXML
    private MediaView _view;
    @FXML
    private ListView _list;
    @FXML
    private Button _pause;
    @FXML
    private Button _mute;
    @FXML private TextField ans;

    MediaPlayer player;

//    File file = new File("/afs/ec.auckland.ac.nz/users/m/s/msid633/unixhome/IdeaProjects/se206A3/car.au/car.mkv");



    @FXML
    public void initialize(){
        _list.getItems().clear();
        pbuilder pro = pbuilder.getInstance();
        pro.probuild2("cd Creations; ls 2> /dev/null");
        List<String> str = pro.getStd();

        for (int i = 0; i < str.size(); i++) {
            _list.getItems().add(str.get(i).substring(0, str.get(i).length() - 0));
        }
        _list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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

    public void delete(){


        if(!_list.getSelectionModel().getSelectedItem().toString().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure that you want to delete the creation?");
            alert.setTitle("Delete the creation?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {

                    try{

                        String topics;
                        topics = _list.getSelectionModel().getSelectedItem().toString();
                        pbuilder.getInstance().probuild ("cd Creations/; rm -r "+ topics);

//                        initialize();



                    } catch(Exception e)

                    {
                        e.printStackTrace();
                    }


                }
            });
        }
        initialize();

    }

    public void setMedia(){
        File fileUrl = new File("Creations/"+_list.getSelectionModel().getSelectedItem().toString()+"/"+_list.getSelectionModel().getSelectedItem().toString()+".mp4");
        Media video = new Media(fileUrl.toURI().toString());
        player = new MediaPlayer(video);
        player.setAutoPlay(true);
        _view.setMediaPlayer(player);
    }

    public void play(){

        Thread object1 = new Thread(new Multi1());
        object1.start();

    }


    public void MainMenu(){
        Platform.runLater(new Multi() {
            @Override
            public void run() {
                SwitchScenes sw = new SwitchScenes(_Play);

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

    public class Multi1 implements Runnable{

        @Override
        public void run() {
            setMedia();
        }
    }

    public void pause() {
        MediaPlayer.Status status = player.getStatus();
        if(MediaPlayer.Status.PLAYING == status){
            player.pause();
            _pause.setText(">");
        } else {
            player.play();
            _pause.setText("||");
        }
    }

    public void mute(){
        if(player.getVolume() == 0){
            player.setVolume(50);
            _mute.setText("Mute");
        }else{
            player.setVolume(0);
            _mute.setText("Voice");
        }
    }




}
