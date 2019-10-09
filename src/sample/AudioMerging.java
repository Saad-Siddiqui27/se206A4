package sample;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class AudioMerging {

    @FXML
    private Pane _creation;
    @FXML
    private ListView _list;
    @FXML
    private TextField _numpics;
    @FXML
    private TextField _merged;


    @FXML
    public void initialize() {

//
        String s = pbuilder.getInstance().getTerm();
//        System.out.println(s);
//        s = s.substring(1,s.length()-1);
//        pbuilder.getInstance().probuild2("cd "+s+".au");


        _list.getItems().clear();
        pbuilder pro = pbuilder.getInstance();
        pro.probuild2("cd " + s + ".au; " + "ls *.wav 2> /dev/null");
        List<String> str = pro.getStd();

        for (int i = 0; i < str.size(); i++) {
            _list.getItems().add(str.get(i).substring(0, str.get(i).length() - 4));
        }
        _list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }


    public void delete(){

        String term;
        term = _list.getSelectionModel().getSelectedItem().toString();
        if(!_list.getSelectionModel().getSelectedItem().toString().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure that you want to delete the creation?");
            alert.setTitle("Delete the creation?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {

                    try{

                        String topics;
                        topics = _list.getSelectionModel().getSelectedItem().toString();
                        pbuilder.getInstance().probuild ("cd "+pbuilder.getInstance().getTerm()+".au; rm "+ topics+".wav");

                        initialize();



                    } catch(Exception e)

                    {
                        e.printStackTrace();
                    }


                }
            });
        }
        initialize();



    }


    public void merge() {


        StringBuilder files = new StringBuilder();
        List<String> Files = _list.getSelectionModel().getSelectedItems();

        if (Files.size() < 2) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select two or more files to merge");
            alert.setTitle("Merging Files");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {

                }

            });
        } else {
            for (int i = 0; i < Files.size(); i++) {


                files.append(Files.get(i) + ".wav ");

            }
            String s = pbuilder.getInstance().getTerm();

            pbuilder.getInstance().probuild2("cd " + s + ".au;pwd");

            pbuilder.getInstance().probuild("cd " + s + ".au ; sox " + files.toString() + "" + _merged.getText() + ".wav");

//            pbuilder.getInstance().probuild("rm " + files.toString());

            initialize();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Audios has been Merged successfully");
            alert.setTitle("Success");


            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {


                }
            });


        }


    }



    public void switchToMain() {
        Platform.runLater(new Multi() {
            @Override
            public void run() {
                SwitchScenes sw = new SwitchScenes(_creation);

                try {
                    sw.switchScenes("../Fxml/MainMenu.fxml");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }


    public void switchBack() {

        Platform.runLater(new Multi() {
            @Override
            public void run() {
                SwitchScenes sw = new SwitchScenes(_creation);

                try {
                    sw.switchScenes("../Fxml/Directoy.fxml");
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
