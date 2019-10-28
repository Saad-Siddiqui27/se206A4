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







    private String term;



    String num;
    int number;


    /**
     * initialise method which initialises the different aspect of the scene when the scene loads up.
     * this method initialises and populates the list of audio files that are to be used in order to generate a video.
     * it also populates choice box for selecting voices.
     * it also has functionality to implement the Enter shortcut key on text fields.
     *
     */
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

        /**
         * Enter shortcut key implentation
         */
        _audioName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    Save();
                }
            }
        });

        /**
         * Enter shortcut key implentation
         */
        _merged.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    merge();
                }
            }
        });

    }

    /**
     * this method is linked to the search button on the scene.
     * this method has concurrency and searches the wikipedia using wikit by utilising the services of
     * another worker thread.
     */
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


    /**
     * a helper thread which get the selected text from text area 1. this text is the one retrived fom wikipedia.
     * @return
     */
    public String select(){
        String str = textArea1.getSelectedText();
        textArea2.appendText(str);

        return str;
}


    private File f = new File("voice1.scm");

    /**
     * this method plays the audio files the selected audio from text area 1 in different voices.
     */
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

    /**
     * this method saves the audio generated and selected over to the text area 2.
     *
     */
    public void Save() {

    StringTokenizer tokens = new StringTokenizer(textArea2.getText());
    int i = tokens.countTokens();


        /**
         * waring allerts if the number of words in the selected test is over 40.
         */
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

    /**
     * a helper method which helps to save the audio files.
     * @param str2 the string to save
     * @param s the voice to save it in.
     * @throws IOException
     */
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


    /**
     * a helper method which reduces code duplication and carries out all bash commands trough the process builder.
     * @param cmd
     */
    public void pbuild(String cmd){
        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);

            Process process = pb.start();

        } catch(Exception e){
            e.printStackTrace();
        }

    }






    /**
     * method which switches scene to the main menu scene. this is done by using the functionality of the SwitchScenes class.
     */
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

    /**
     * helper class to aid in switching scenes.
     */
    public class Multi implements Runnable {

        @Override
        public void run() {

        }
    }

    /**
     * a helper class to implement concurrency and run the wikit search terms.
     */
    public class Multi1 implements Runnable{

        @Override
        public void run() {
            String command = "wikit " + term + "| sed -e :1 -e 's/\\([.?!]\\)[[:blank:]]\\{1,\\}\\([^[:blank:]]\\)/\\1\\\n" +
                    "\\2/;t1' ";

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


    /**
     * method connected to the merge button which merges two different audio files and generates a merged version of them.
     */
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

    /**
     * method connected to the delete button which deleted the selected audio that has already been created.
     */
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


    /**
     * play method which is connected to the preview method and plays the preselected text.
     */
    public void Play(){


        String s = "cd "+term+".au ;play "+ _list.getSelectionModel().getSelectedItem().toString()+".wav";
        pbuilder.getInstance().probuild(s);
    }


}
