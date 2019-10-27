package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;
import java.util.StringTokenizer;



//Henry Imports

import java.io.BufferedReader;
import java.io.File;


public class CreateAudio {

    @FXML
    private Pane _create;
    @FXML
    private TextField _textfield;
    @FXML
    private TextArea textArea1;
    @FXML
    private TextArea textArea2;
    @FXML
    private ChoiceBox _menubutton;
    @FXML
    private CheckMenuItem _voice1;
    @FXML
    private CheckMenuItem _voice2;
    @FXML
    private TextField _audioName;
    @FXML
    private TextField _textfield2;
    @FXML private ProgressIndicator pi;
    @FXML
    private ListView _list;
    @FXML
    private TextField _merged;







    String term;


    //Henry part;
    String num;
    int number;
    //done


    @FXML
    public void initialize(){
        _menubutton.getItems().addAll("Auckland Voice","Default Voice");
        _menubutton.setValue("Default Voice");
        pi.setVisible(false);

        String s = pbuilder.getInstance().getTerm();
        _list.getItems().clear();
        pbuilder pro = pbuilder.getInstance();
        pro.probuild2("cd " + term + ".au; " + "ls *.wav ; cd .. 2> /dev/null");
        List<String> str = pro.getStd();

        for (int i = 0; i < str.size(); i++) {
            _list.getItems().add(str.get(i).substring(0, str.get(i).length() - 4));
        }
        _list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        _textfield.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    search();
                }
            }
        });

        _audioName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    Save();
                }
            }
        });

        _merged.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    merge();
                }
            }
        });

    }



    public void search() {
        term = _textfield.getText();

        if (term.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please enter a valid term that you want to search");
            alert.setTitle("Empty term name");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {

                }
            });
        } else {
            pi.setVisible(true);
            Thread object1 = new Thread(new Multi1());
            object1.start();
            initialize();

        }

    }


public String select(){
        String str = textArea1.getSelectedText();
        textArea2.appendText(str);

        return str;
}




    File f = new File("voice1.scm");

public void play(){
        String str = textArea1.getSelectedText();
    try {
        if(_menubutton.getValue()=="Auckland Voice") {

            FileWriter fw = new FileWriter(f);
            fw.write("(voice_akl_nz_jdt_diphone) ;; select Jono" + " \n(SayText \"" + str + "\")");
            fw.close();
            String cmd3 = "festival -b " + f;
            pbuild(cmd3);
        }else if(_menubutton.getValue()=="Default Voice"){
            FileWriter fw = new FileWriter(f);
            fw.write("(voice_kal_diphone) ;; select Jono" + " \n(SayText \"" + str + "\")");
            fw.close();
            String cmd3 = "festival -b " + f;
            pbuild(cmd3);

        }
    }catch(Exception e){
        e.printStackTrace();
    }


}

public void Save() {

    StringTokenizer tokens = new StringTokenizer(textArea2.getText());
    int i = tokens.countTokens();


    if (i > 40) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("The text is over the limit");
        alert.setTitle("Over the limit");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

            }
        });

    }else if (_audioName.getText().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Please enter a valid name for your audio.");
        alert.setTitle("Invalid audio name");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

            }
        });

    } else {

        try {

            String str2 = textArea2.getText();


            if (_menubutton.getValue()=="Auckland Voice") {
                helpSave(str2, "(voice_akl_nz_jdt_diphone) ");
            } else if (_menubutton.getValue()=="Default Voice") {
                helpSave(str2, "(voice_kal_diphone) ");

            }
            initialize();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Your Audio has been Saved");
        alert.setTitle("Processing done");


        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {


            }
        });


    }

}

    public void helpSave(String str2, String s) throws IOException {
        File f2 = new File("text");
        FileWriter fw2 = new FileWriter(f2);
        fw2.write(str2);
        fw2.close();
        FileWriter fw = new FileWriter(f);
        fw.write(s);
        fw.close();
        File audio = new File(_textfield.getText());
        if(! audio.exists()) {
            String cmd4 = "mkdir " + _textfield.getText()+".au";
            pbuilder.getInstance().probuild(cmd4);
        }
        String cmd3 = "text2wave -o "+_textfield.getText()+".au/"+_audioName.getText()+".wav "+f2+" -eval "+f;
        pbuilder.getInstance().probuild(cmd3);


    }


    public void pbuild(String cmd){
        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);

            Process process = pb.start();



        } catch(Exception e){
            e.printStackTrace();
        }


    }





    public void switchScenes(String fxml) throws IOException {

        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage) _create.getScene().getWindow();

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.sizeToScene();
    }

    public void MainMenu(){
        Platform.runLater(new Multi() {
            @Override
            public void run() {

                SwitchScenes sw = new SwitchScenes(_create);

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
            String command = "wikit " + term + "| sed -e :1 -e 's/\\([.?!]\\)[[:blank:]]\\{1,\\}\\([^[:blank:]]\\)/\\1\\\n" +
                    "\\2/;t1' ";
//        pbuild(command);

            try {
                ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);

                Process process = pb.start();

                BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                int exitStatus = process.waitFor();

                StringBuilder sb = new StringBuilder();
                if (exitStatus == 0) {
                    String line;
//                    int count = 1;
                    while ((line = stdout.readLine()) != null) {
                        sb.append(line).append("\n\n");

                    }
                } else {
                    String line;
                    while ((line = stderr.readLine()) != null) {
                        System.err.println(line);
                    }
                }

                textArea1.setText(sb.toString());
                pi.setVisible(false);

                stderr.close();
                stdout.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        }else if (_merged.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please enter a file name for the audio");
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

            pbuilder.getInstance().probuild2("cd " + term + ".au;pwd");

            pbuilder.getInstance().probuild("cd " + term + ".au ; sox " + files.toString() + "" + _merged.getText() + ".wav");

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
                        pbuilder.getInstance().probuild ("cd "+term+".au; rm "+ topics+".wav");

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


    public void Play(){


        String s = "cd "+term+".au ;play "+ _list.getSelectionModel().getSelectedItem().toString()+".wav";
        pbuilder.getInstance().probuild(s);
    }


}
