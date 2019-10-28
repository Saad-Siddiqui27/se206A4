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

//    public Play(){
//
//    }

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
    @FXML private Button delete;
    @FXML private Button play;
    @FXML private Button menu;


    MediaPlayer player;

//    File file = new File("/afs/ec.auckland.ac.nz/users/m/s/msid633/unixhome/IdeaProjects/se206A3/car.au/car.mkv");


    /**
     * initialise method which initialises the different aspect of the scene when the scene loads up.
     * the things initialised are the list of creations.
     */
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
        if(_list.getItems().isEmpty()){
            delete.setDisable(true);
            play.setDisable(true);

        }
    }

    /**
     * this method deletes the creation that the user has made. the creations are presented in a list which can then be chosen to delete.
     */
    public void delete(){

        if(!_list.getSelectionModel().getSelectedItem().toString().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure that you want to delete the creation?");
            alert.setTitle("Delete the creation?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {

                    try{

                        if(player!=null){
                            player.stop();
                        }
                        String topics;
                        topics = _list.getSelectionModel().getSelectedItem().toString();
                        pbuilder.getInstance().probuild ("cd Creations/; rm -rf "+ topics);

                    } catch(Exception e)

                    {
                        e.printStackTrace();
                    }
                }
            });
            initialize();
        }


    }

    /**
     * a helper method which sets up the media player for the selected creation to be played in. this method is called from the play() method
     * which is multithreaded.
     */
    public void setMedia(){
        File fileUrl = new File("Creations/"+_list.getSelectionModel().getSelectedItem().toString()+"/"+_list.getSelectionModel().getSelectedItem().toString()+".mp4");
        Media video = new Media(fileUrl.toURI().toString());
        player = new MediaPlayer(video);
        player.setAutoPlay(true);
        _view.setMediaPlayer(player);
    }

    /**
     * the method which is linked to the play button on the scene and plays the required creation,
     * the player is set up in a seperate thread so that the GUI thread has no effect and keeps on running smoothly
     */
    public void play(){

        if(player!=null){
            player.stop();
        }
        Thread object1 = new Thread(new Multi1());
        object1.start();

    }

    /**
     *  method which switches scene to the playing creations scene. this is done by using the functionality of the SwitchScenes singleton class.
     *  Platform.runlater method used to carry out the Gui part.
     */
    public void MainMenu(){
        Platform.runLater(new Multi() {
            @Override
            public void run() {
                if(player!=null){
                    player.stop();
                }
                SwitchScenes sw = new SwitchScenes(_Play);

                try {
                    sw.switchScenes("/Fxml/MainMenu.fxml");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    /**
     * this class is used to achive concurrency and implements Runnable.
     */
    public class Multi implements Runnable {

        @Override
        public void run() {

        }
    }

    /**
     * this class is used to achive concurrency and implements Runnable.
     */
    public class Multi1 implements Runnable{

        @Override
        public void run() {
            setMedia();
        }
    }

    /**
     * this method is linked to the pause button and pauses the current running media,
     * and changes the text of the button accordingly
     */
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

    /**
     * this method is linked to the Mute button and mutes the current running media,
     * and changes the text of the button accordingly
     *
     */
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
