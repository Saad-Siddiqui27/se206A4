package sample;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Creation implements Serializable {

    private int Priority;
    private String _creationName;
    private String query;


    public Creation(int priority, String CreationName){
        Priority = priority;
        _creationName = CreationName;
//        query = term;
    }

//    public void createVideo() {
//
////        num = _numpics.getText();
//        List<String> n = new ArrayList<>();
//        int m = 1;
//        while (m <= 10) {
//            n.add(Integer.toString(m))
//            ;
//            m = m + 1;
//        }
//        if (!n.contains(num)) {
//
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setContentText("Please enter a valid number between 1 and 10");
//            alert.setTitle("Invalid Number");
//
//            alert.showAndWait().ifPresent(response -> {
//                if (response == ButtonType.OK) {
//
//                }
//            });
//        } else if (_creationName=="") {
//
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setContentText("Please enter a creation name");
//            alert.setTitle("Creation Name not entered");
//
//            alert.showAndWait().ifPresent(response -> {
//                if (response == ButtonType.OK) {
//
//                }
//            });
//        } else {
//
//            Thread object1 = new Thread(new Multi1());
//            object1.start();
//
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setContentText("your creation has been created");
//            alert.setTitle("Successful");
//
//
//            alert.showAndWait().ifPresent(response -> {
//                if (response == ButtonType.OK) {
//
//
//                }
//            });
//
//
//        }
//
//
//    }
//
//
//    public void CombineVideo(double time) throws IOException {
//
//        int number = Integer.parseInt(num);
//
//        StringBuilder s = new StringBuilder();
//        for (int j = 1; j <= number; j++) {
//            s.append(query + j + ".jpg ");
//
//
//        }
//
//
//        time = number / time;
//        String cmd = "cd " + query + ".au; cat " + s.toString() + "| ffmpeg -framerate " + time + " -f image2pipe -i - -vf \"scale=iw*min(1920/iw\\,1080/ih):ih*min(1920/iw\\,1080/ih), pad=1920:1080:(1920-iw*min(1920/iw\\,1080/ih))/2:(1080-ih*min(1920/iw\\,1080/ih))/2,format=yuv420p,drawtext=fontfile=myfont.ttf:fontsize=100: fontcolor=white:shadowx=2:x=(w-text_w)/2:y=(h-text_h)/2:text='" + query + "'\" -r 25 " + query + ".mp4";
//
//        pbuilder.getInstance().probuild(cmd);
//
//
//        String cmd7 = "cd " + query + ".au; ffmpeg -i " + query + ".mp4 -i " + _list.getSelectionModel().getSelectedItem().toString() + ".wav -vcodec copy -strict -2 " + _creationName.getText() + ".mp4; " + "mv " + _creationName.getText() + ".mp4 ../Creations";
//
////        if (music.isSelected()) {
////
////            cmd7 = "cd " + query + ".au ;ffmpeg -i ../backgroundMusic.mp3 " + _list.getSelectionModel().getSelectedItem().toString() + ".wav -filter_complex amix=inputs=2:duration=longest output.mp3; ffmpeg -i " + query + ".mp4 -i output.mp3 -vcodec copy -strict -2 " + _creationName.getText() + ".mp4; " + "mv " + _creationName.getText() + ".mp4 ../Creations";
////        }
//
//        pbuilder.getInstance().probuild(cmd7);
//
////        String cmd8 = "cd "+ query+".au; "+"mv "+_creationName.getText()+".mp4 ../Creations/";
////        System.out.println(cmd8);
////        pbuilder.getInstance().probuild(cmd8);
//
////        String cmd9 = "mv "+_creationName.getText()+".mp4 ./Creations";
////        pbuilder.getInstance().probuild(cmd9);
//
//
//    }
//
//
//    private static String getAPIKey(String key) throws Exception {
//        // TODO fix the following based on where you will have your config file stored
//
//        String config = System.getProperty("user.dir")
//                + System.getProperty("file.separator") + "flickr-api-keys.txt";
//        File file = new File(config);
//        BufferedReader br = new BufferedReader(new FileReader(file));
//
//        String line;
//        while ((line = br.readLine()) != null) {
//            if (line.trim().startsWith(key)) {
//                br.close();
//                return line.substring(line.indexOf("=") + 1).trim();
//            }
//        }
//        br.close();
//        throw new RuntimeException("Couldn't find " + key + " in config file " + file.getName());
//
////finish
//
//    }
//
//
//
//
//    public class Multi implements Runnable {
//
//        @Override
//        public void run() {
//
//        }
//    }
//
//    public class Multi1 implements Runnable {
//
//        @Override
//        public void run() {
//            try {
//                String apiKey = getAPIKey("apiKey");
//                String sharedSecret = getAPIKey("sharedSecret");
//
//                Flickr flickr = new Flickr(apiKey, sharedSecret, new REST());
//
//
//                String query = pbuilder.getInstance().getTerm();
//                System.out.println(query);
//                int resultsPerPage = 10;
//                int page = 0;
//
//                PhotosInterface photos = flickr.getPhotosInterface();
//                SearchParameters params = new SearchParameters();
//                params.setSort(SearchParameters.RELEVANCE);
//                params.setMedia("photos");
//                params.setText(query);
//
//                PhotoList<Photo> results = photos.search(params, resultsPerPage, page);
//                System.out.println("Retrieving " + results.size() + " results");
//                int i = 1;
//
//                int number = Integer.parseInt(num);
//                for (Photo photo : results) {
//                    if (i <= number) {
//                        try {
//
//                            BufferedImage image = photos.getImage(photo, Size.SMALL);
//                            String filename = query + Integer.toString(i) + ".jpg";
//                            File outputfile = new File(filename);
//                            ImageIO.write(image, "jpg", outputfile);
//                            System.out.println("Downloaded " + filename);
//                            i = i + 1;
//
//                        } catch (FlickrException | IOException fe) {
//                            System.err.println("Ignoring image " + photo.getId() + ": " + fe.getMessage());
//                        }
//                    } else {
//                        break;
//                    }
//                }
//
//                String cmd5 = "mv *.jpg " + query + ".au";
//                pbuilder.getInstance().probuild(cmd5);
//
//                File f = new File(_list.getSelectionModel().getSelectedItem().toString() + ".wav");
//                pbuilder.getInstance().probuild2("cd " + query + ".au;" + " soxi -D " + _list.getSelectionModel().getSelectedItem().toString() + ".wav");
//                List<String> time1 = pbuilder.getInstance().getStd();
//                Double time = Double.parseDouble(time1.get(0));
//                System.out.println("time =" + time);
//
//
//
//                CombineVideo(time);
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//        }
//
//
//    }
}




