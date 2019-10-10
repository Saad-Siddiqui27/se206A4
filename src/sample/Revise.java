package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.FileInputStream;
import java.util.List;

public class Revise {

    @FXML
    private Pane revise;
    @FXML
    private MediaView media;
    @FXML
    private ListView _list;
    @FXML
    private TextField answer;


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

    public void confirm(){

        if(pbuilder.getInstance().getTerm()==answer.getText()){

            System.out.println("yes");
        }else{

            System.out.println(pbuilder.getInstance().getTerm());
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
