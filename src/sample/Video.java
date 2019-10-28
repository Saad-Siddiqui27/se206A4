package sample;

        import com.flickr4java.flickr.Flickr;
        import com.flickr4java.flickr.FlickrException;
        import com.flickr4java.flickr.REST;
        import com.flickr4java.flickr.photos.*;
        import javafx.application.Platform;
        import javafx.event.EventHandler;
        import javafx.fxml.FXML;
        import javafx.scene.control.*;
        import javafx.scene.input.KeyCode;
        import javafx.scene.input.KeyEvent;
        import javafx.scene.layout.Pane;

        import javax.imageio.ImageIO;
        import java.awt.image.BufferedImage;
        import java.io.*;
        import java.util.ArrayList;
        import java.util.List;


public class Video {

    @FXML private Pane _Video;
    @FXML private ListView _list;
    @FXML private TextField _numpics;
    @FXML private TextField _creationName;
    @FXML
    private CheckBox music;
    @FXML
    private ChoiceBox musics;
    @FXML
    private ListView _creationList;

    private String num ;

    private int number;

    String query = pbuilder.getInstance().getTerm();


    /**
     * initialise method which initialises the different aspect of the scene when the scene loads up.
     * this method initialises and populates the list of audio files that are to be used in order to generate a video.
     * it also initializes and refreshest the list of creations when a creation is made.
     */
    @FXML
    public void initialize() {

        String s =pbuilder.getInstance().getTerm();
        _list.getItems().clear();
        pbuilder pro = pbuilder.getInstance();
        pro.probuild2("cd \""+s+"\".au; "+"ls *.wav 2> /dev/null");
        List<String> str = pro.getStd();

        for (int i = 0; i < str.size(); i++) {
            _list.getItems().add(str.get(i).substring(0, str.get(i).length() - 4));
        }
        _list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        _creationList.getItems().clear();
        pbuilder pro2 = pbuilder.getInstance();
        pro.probuild2("cd Creations/; ls  2> /dev/null");
        List<String> str2 = pro2.getStd();

        for (int i = 0; i < str2.size(); i++) {
            _creationList.getItems().add(str2.get(i).substring(0, str.get(i).length()));
        }
        _list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        musics.getItems().clear();
        musics.getItems().addAll("Jullibie","something cool","No music","wolf instrumental");
        musics.setValue("No music");

        _creationName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    createVideo();
                }
            }
        });

    }

    /**
     * this method is linked to the the create button and creates the video by doing a number of error checks as well.
     * it sends out an alert message if the appropiate method is not followed.
     */
    public void createVideo() {

        if (_list.getSelectionModel().getSelectedItem()==null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select an audio to create a video of");
            alert.setTitle("No audio selected");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {

                }
            });
        }else {
            num = _numpics.getText();
            List<String> n = new ArrayList<>();
            int m = 1;
            while (m <= 10) {
                n.add(Integer.toString(m))
                ;
                m = m + 1;
            }
            if (!n.contains(num)) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please enter a valid number between 1 and 10");
                alert.setTitle("Invalid Number");

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {

                    }
                });
            } else if (_creationName.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please enter a creation name");
                alert.setTitle("Creation Name not entered");

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {

                    }
                });
            } else {

                Thread object1 = new Thread(new Multi1());
                object1.start();
//            initialize();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("your creation has been created");
                alert.setTitle("Successful");


                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {


                    }
                });
            }
        }
    }


    /**
     *
     * @param time
     * @throws IOException
     */
    public void CombineVideo(double time) throws IOException {

        int number = Integer.parseInt(_numpics.getText());
        StringBuilder s = new StringBuilder();
        for(int j=1 ; j<=number;j++){
            s.append(query+j+".jpg ");
            System.out.println(query);

        }

        time = number/time;
        String cmd = "cd "+ query+".au; cat " +s.toString()+ "| ffmpeg -framerate "+time+" -f image2pipe -i - -vf \"scale=iw*min(1920/iw\\,1080/ih):ih*min(1920/iw\\,1080/ih), pad=1920:1080:(1920-iw*min(1920/iw\\,1080/ih))/2:(1080-ih*min(1920/iw\\,1080/ih))/2,format=yuv420p,drawtext=fontfile=myfont.ttf:fontsize=100: fontcolor=white:shadowx=2:x=(w-text_w)/2:y=(h-text_h)/2:text='\""+query+"\"'\" -r 25 \""+query+"\".mp4";

        pbuilder.getInstance().probuild(cmd);

        //here
        String cmd2 = "cd "+ query+".au; cat " +s.toString()+ "| ffmpeg -framerate "+time+" -f image2pipe -i - -vf \"scale=iw*min(1920/iw\\,1080/ih):ih*min(1920/iw\\,1080/ih), pad=1920:1080:(1920-iw*min(1920/iw\\,1080/ih))/2:(1080-ih*min(1920/iw\\,1080/ih))/2,format=yuv420p,drawtext=fontfile=myfont.ttf:fontsize=100: fontcolor=white:shadowx=2:x=(w-text_w)/2:y=(h-text_h)/2:text=''\" -r 25 "+query+"revision.mp4; rm *.jpg";

        pbuilder.getInstance().probuild(cmd2);


        if (musics.getValue().equals("No music")) {

            String cmd7 = "cd " + query + ".au; ffmpeg -i " + query + ".mp4 -i " + _list.getSelectionModel().getSelectedItem().toString() + ".wav -vcodec copy -strict -2 " + _creationName.getText() + ".mp4; " + "mkdir ../Creations/" + _creationName.getText() + ";echo " + query + " > term.txt; mv -t ../Creations/" + _creationName.getText() + " term.txt " + _creationName.getText() + ".mp4";

            String cmd8 = "cd " + query + ".au; ffmpeg -i " + query + "revision.mp4 -i " + _list.getSelectionModel().getSelectedItem().toString() + ".wav -vcodec copy -strict -2 " + _creationName.getText() + "revision.mp4; " + "mkdir ../Creations/" + _creationName.getText() + ";echo " + query + " > term.txt; mv -t ../Creations/" + _creationName.getText() + " term.txt " + _creationName.getText() + "revision.mp4";

            helpMusic(cmd7, cmd8);

        }else if(musics.getValue().equals("something cool")){

            String cmd9 = "cd "+ query+".au; ffmpeg -i ../Music/backgroundMusic.mp3 -i "+ _list.getSelectionModel().getSelectedItem() +".wav -filter_complex amerge=inputs=2 -ac 2 out.mp3";

            pbuilder.getInstance().probuild(cmd9);

            String c2 =  "cd "+ query+".au; ffmpeg -i "+query+"revision.mp4 -i out.mp3 -vcodec copy -strict -2 " + _creationName.getText()+"revision.mp4; echo "+query+" > term.txt; mv -t ../Creations/"+_creationName.getText()+" term.txt "+_creationName.getText()+"revision.mp4; rm *.mp3";

            String c =  "cd "+ query+".au; ffmpeg -i "+query+".mp4 -i out.mp3 -vcodec copy -strict -2 " + _creationName.getText()+".mp4; "+ "mkdir ../Creations/"+_creationName.getText()+"; echo "+query+" > term.txt; mv -t ../Creations/"+_creationName.getText()+" term.txt "+_creationName.getText()+".mp4";

            helpMusic(c, c2);
        }else if(musics.getValue().equals("wolf instrumental")){

            String cmd9 = "cd "+ query+".au; ffmpeg -i ../Music/wolf.mp3 -i "+ _list.getSelectionModel().getSelectedItem() +".wav -filter_complex amerge=inputs=2 -ac 2 out.mp3";

            pbuilder.getInstance().probuild(cmd9);

            String c2 =  "cd "+ query+".au; ffmpeg -i "+query+"revision.mp4 -i out.mp3 -vcodec copy -strict -2 " + _creationName.getText()+"revision.mp4; echo "+query+" > term.txt; mv -t ../Creations/"+_creationName.getText()+" term.txt "+_creationName.getText()+"revision.mp4; rm *.mp3";

            String c =  "cd "+ query+".au; ffmpeg -i "+query+".mp4 -i out.mp3 -vcodec copy -strict -2 " + _creationName.getText()+".mp4; "+ "mkdir ../Creations/"+_creationName.getText()+"; echo "+query+" > term.txt; mv -t ../Creations/"+_creationName.getText()+" term.txt "+_creationName.getText()+".mp4";

            helpMusic(c, c2);
        }else{

            String cmd9 = "cd "+ query+".au; ffmpeg -i ../Music/Jullibe.mp3 -i "+ _list.getSelectionModel().getSelectedItem() +".wav -filter_complex amerge=inputs=2 -ac 2 out.mp3";

            pbuilder.getInstance().probuild(cmd9);

            String c =  "cd "+ query+".au; ffmpeg -i "+query+".mp4 -i out.mp3 -vcodec copy -strict -2 " + _creationName.getText()+".mp4; "+ "mkdir ../Creations/"+_creationName.getText()+"; echo "+query+" > term.txt; mv -t ../Creations/"+_creationName.getText()+" term.txt "+_creationName.getText()+".mp4";

            String c2 =  "cd "+ query+".au; ffmpeg -i "+query+"revision.mp4 -i out.mp3 -vcodec copy -strict -2 " + _creationName.getText()+"revision.mp4; echo "+query+" > term.txt; mv -t ../Creations/"+_creationName.getText()+" term.txt "+_creationName.getText()+"revision.mp4; rm *.mp3";

            helpMusic(c, c2);
        }


    }

    /**
     * Helper method to create videos with different background musics or no music.
     * @param
     * @return
     * @throws Exception
     */

    public void helpMusic(String s1, String s2){
        pbuilder.getInstance().probuild(s1);
        pbuilder.getInstance().probuild(s2);

    }


    /**
     * gets the specified flickr keys of the user by reading the file.
     * @param key
     * @return
     * @throws Exception
     */
    private static String getAPIKey(String key) throws Exception {
        // TODO fix the following based on where you will have your config file stored

        String config = System.getProperty("user.dir")
                + System.getProperty("file.separator") + "flickr-api-keys.txt";
        File file = new File(config);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().startsWith(key)) {
                br.close();
                return line.substring(line.indexOf("=") + 1).trim();
            }
        }
        br.close();
        throw new RuntimeException("Couldn't find " + key + " in config file " + file.getName());

    }


    /**
     * this method switches back to the previous scene.
     */
    public void switchBack() {
        Platform.runLater(new Multi() {
            @Override
            public void run() {
                SwitchScenes sw = new SwitchScenes(_Video);
                try {
                    sw.switchScenes("/Fxml/Directory.fxml");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     *  method which switches scene to the main menu scene. this is done by using the functionality of the SwitchScenes class.
     */
    public void switchToMain() {
        Platform.runLater(new Multi() {
            @Override
            public void run() {
                SwitchScenes sw = new SwitchScenes(_Video);

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

    /**
     * helper class to implement GUI concurrency.
     * in the run method the pictures are retrieved form flickr.
     * Code was taken from ACP and any neccary changes were done to it in order to make it meet our demands.
     */
    public class Multi1 implements Runnable {

        @Override
        public void run() {
            try {
                String apiKey = getAPIKey("apiKey");
                String sharedSecret = getAPIKey("sharedSecret");

                Flickr flickr = new Flickr(apiKey, sharedSecret, new REST());

                String query = pbuilder.getInstance().getTerm();
                System.out.println(query);
                int resultsPerPage = 10;
                int page = 0;

                PhotosInterface photos = flickr.getPhotosInterface();
                SearchParameters params = new SearchParameters();
                params.setSort(SearchParameters.RELEVANCE);
                params.setMedia("photos");
                params.setText(query);

                PhotoList<Photo> results = photos.search(params, resultsPerPage, page);
                int i = 1;

                int number = Integer.parseInt(_numpics.getText());
                for (Photo photo : results) {
                    if (i <= number) {
                        try {

                            BufferedImage image = photos.getImage(photo, Size.SMALL);
                            String filename = query + Integer.toString(i) + ".jpg";
                            File outputfile = new File(filename);
                            ImageIO.write(image, "jpg", outputfile);
                            i = i + 1;

                        } catch (FlickrException | IOException fe) {
                            System.err.println("Ignoring image " + photo.getId() + ": " + fe.getMessage());
                        }
                    } else {
                        break;
                    }
                }

                String cmd5 = "mv *.jpg " + query + ".au";
                pbuilder.getInstance().probuild(cmd5);

                pbuilder.getInstance().probuild2("cd "+ query+".au;" +" soxi -D "+_list.getSelectionModel().getSelectedItem().toString()+".wav");
                List<String> time1 = pbuilder.getInstance().getStd();
                Double time = Double.parseDouble( time1.get(0));
                System.out.println("time ="+time);

                CombineVideo(time);

                //this code refreshes the scene to update it to present new information.
                Platform.runLater(new Multi() {
                    @Override
                    public void run() {
                        initialize();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}



