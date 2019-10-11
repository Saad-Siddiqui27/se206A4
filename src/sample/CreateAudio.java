package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
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






    String term;


    //Henry part;
    String num;
    int number;
    //done


    @FXML
    public void initialize(){
        _menubutton.getItems().addAll("Auckland Voice","Default Voice");
        _menubutton.setValue("Default Voice");
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
            Thread object1 = new Thread(new Multi1());
            object1.start();

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

        //To do Alerts;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("The text is over the limit");
        alert.setTitle("Over the limit");


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
            String cmd4 = "mkdir \"" + _textfield.getText()+"\".au";
            pbuilder.getInstance().probuild(cmd4);
        }
        String cmd3 = "text2wave -o \""+_textfield.getText()+"\".au/"+_audioName.getText()+".wav "+f2+" -eval "+f;
        pbuild(cmd3);
        System.out.println(_audioName.getText() + " saved");

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

                try {
                    switchScenes("/Fxml/MainMenu.fxml");
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
                    int count = 1;
                    while ((line = stdout.readLine()) != null) {
                        sb.append(count++ + ". " + line).append("\n");

                    }
                } else {
                    String line;
                    while ((line = stderr.readLine()) != null) {
                        System.err.println(line);
                    }
                }

                textArea1.setText(sb.toString());

                stderr.close();
                stdout.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
