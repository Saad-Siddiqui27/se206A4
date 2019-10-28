package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane;

import java.util.List;


public class Directory {

@FXML private Pane _Audio;
@FXML private ListView _directory;



    /**
     * initialise method which initialises the different aspect of the scene when the scene loads up.
     * this method initialises and populates the list of audio files that are to be used in order to generate a video.
     *
     */
    @FXML
    public void initialize() {


        _directory.getItems().clear();
        pbuilder pro = pbuilder.getInstance();
        pro.probuild2("ls -d *.au 2> /dev/null");
        List<String> str = pro.getStd();

        for (int i = 0; i < str.size(); i++) {
            _directory.getItems().add(str.get(i).substring(0, str.get(i).length() - 3));
        }
        _directory.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


    }


   public void goInAudioMerge() {
       if (_directory.getSelectionModel().getSelectedItem() == null) {
           alert();
       } else {

           pbuilder.getInstance().saveTerm(_directory.getSelectionModel().getSelectedItem().toString());
           SwitchScenes sw = new SwitchScenes(_Audio);
           try {
               sw.switchScenes("/Fxml/AudioMerging.fxml");
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
    }


    /**
     * method which switches scene to the Creating video scene. this is done by using the functionality of the SwitchScenes class.
     */
    public void goInVideoCreate() {
        if (_directory.getSelectionModel().getSelectedItem() == null) {
            alert();
        } else {
            pbuilder.getInstance().saveTerm(_directory.getSelectionModel().getSelectedItem().toString());
            SwitchScenes sw = new SwitchScenes(_Audio);
            try {
                sw.switchScenes("/Fxml/Video.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * method which switches scene to the main menu scene. this is done by using the functionality of the SwitchScenes class.
     */
    public void switchToMain() {
        Platform.runLater(new Multi() {
            @Override
            public void run() {
                SwitchScenes sw = new SwitchScenes(_Audio);

                try {
                    sw.switchScenes("/Fxml/MainMenu.fxml");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * helper class to implement concurrency
     */
    public class Multi implements Runnable {

            @Override
            public void run() {

            }
        }

    /**
     * helper alert method which throws appropiate alerts which something goes wrong
     */
    public void alert(){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please choose a directory");
            alert.setTitle("Directory");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {

                }
            });
        }

}
