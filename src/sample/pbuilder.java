package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class pbuilder {

//    private String _cmd;

    // a static field as the variable refers to singleton class object
    private static pbuilder _instance;

    private static List<String> _stdOut = new ArrayList<String>();
//    private static int num;
    private String _term ;

    /**
     * a constructor for the singleton class which returns the same object whenever new object is created.
     */
    private pbuilder() {}

    /**
     * singleton class generator which returns the same object of the class
     * @return pbuilder object
     */
    public static pbuilder getInstance() {
        if (_instance == null) {
            _instance = new pbuilder();
        }

        return _instance;
    }


    /**
     * a method which runs the linux commands and returns nothing.
     * @param _cmd is the string of linux command to be run.
     *
     */
        public void probuild(String _cmd) {
            try {
                ProcessBuilder pb = new ProcessBuilder("bash", "-c", _cmd);

                Process process = pb.start();
                process.waitFor();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    /**
     * method which returns the output generated when a linux bash command is run.
     * @param _cmd the command to be run.
     */
    public void probuild2(String _cmd){

            try {

                ProcessBuilder pb = new ProcessBuilder("bash", "-c", _cmd);

                Process process = pb.start();
                process.waitFor();

                BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                int exitStatus = process.waitFor();

                if (exitStatus == 0) {
                    String line;
                    _stdOut.clear();// here.
                    while ((line = stdout.readLine()) != null) {

                        _stdOut.add(line);

                    }

                } else {
                    String line;
                    while ((line = stderr.readLine()) != null) {
                        System.err.println(line);
                    }
                }
                stdout.close();
                stderr.close();

            } catch(Exception e)

            {
                e.printStackTrace();
            }

        }

    /**
     * getter method which gets the output generated when the bash command is processed.
     * @return a list of Strings which is the output of the linux command
     */
    public static List<String> getStd() {
        return _stdOut;
    }


    /**
     * setter method which saves a term for future use
     * @param str term to be saved
     */
    public void saveTerm(String str){
        _term = str;

    }


    /**
     * getter method which returns the term
     * @return A string which is the saved term.
     */
    public String getTerm(){
        return _term;

    }

}
