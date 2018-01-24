package BTCodeEvaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MonitoringSystemDisplay {

    /**
     * Instance variables.
     * The object will require a file path to run as well as variables to store the data being read.
     */
    private String fileName;
    protected String received, generated, previousGenerated;
    protected String node1, node2;
    protected String notification;


    /**
     * Main method.
     *
     * @param args Takes the file path as an argument.
     */
    public static void main(String[] args) {
        MonitoringSystemDisplay monitor = new MonitoringSystemDisplay(args[0]);
    }


    /**
     * Calls the read() method. Catches FileNotFoundException if the user passes an invalid file or file path.
     *
     * @param fileName path where the file is located
     */
    public MonitoringSystemDisplay(String fileName){
        this.fileName = fileName;

        try {
            read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Reads the data from the file, splits each item of text and passes the strings to the decipher(String item).
     * Calls the output() method after a message has been read but only if the timestamps are valid.
     * A new message is recognised every third time stamp.
     *
     * Exception is caught in constructor
     * @throws FileNotFoundException
     */
    private void read() throws FileNotFoundException{
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        try{
            String dataStreamArray[] = reader.readLine().split(" ");

            int timeStampsRead = 0;
            for (String e : dataStreamArray){
              if(isInteger(e)){
                  if(timeStampsRead == 2){
                      timeStampsRead = 0;
                      if(timeStampValid()){output();}
                      previousGenerated = generated;
                      clearVariables();
                  }
                  timeStampsRead++;
              }
                decipher(e);
            }
            output();

        }catch (IOException e){
            System.out.println(e);
        }
    }


    /**
     * Sorts and stores the individual strings temporarily.
     *
     * @param item the individual string to be sorted.
     */
    protected void decipher(String item){

        if (isInteger(item)){
            if(received == null){
                received = item;
            }else if (generated == null){
                generated = item;
            }
        }else{
            if(item.contains("HELLO") || item.contains("LOST") || item.contains("FOUND")){
                notification = item;
            }else if(node1 == null){
                node1 = item;
            }else{
                node2 = item;
            }
        }
    }


    /**
     * Prints the analysed messages to console.
     * There will always be one ALIVE message printed regardless of the notification type since
     * receiving a notification implies the sending node is alive.
     * A second message is printed for LOST and FOUND notification types.
     */
    private void output(){
        StringBuilder sb = new StringBuilder();

        sb.append(node1 + " ");
        if(inSync()){sb.append("ALIVE ");}else{sb.append("UNKNOWN ");}
        sb.append(received + " ");
        sb.append(node1 + " " + notification);
        if(node2 != null){sb.append(" " + node2);}
        sb.append("\n");
        System.out.print(sb.toString());

        sb = new StringBuilder();
        if(!notification.equals("HELLO")){
            sb.append(node2 + " ");
            if(notification.equals("LOST")){
                if(inSync()){sb.append("DEAD ");}else{sb.append("UNKNOWN ");}
            }else{
                if(inSync()){sb.append("ALIVE ");}else{sb.append("UNKNOWN ");}
            }
            sb.append(received + " ");
            sb.append(node1 + " " + notification);
            if(node2 != null){
                sb.append(" " + node2);
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());
    }


    /**
     * Check if a string contains numbers only. Used to determine whether a string is a timestamp.
     *
     * @param text takes individual strings and tries to parse it as an integer.
     * @return returns true if it is an integer and false if not.
     */
    protected boolean isInteger(String text){
        try{
            if(text.length()>9){
                String firstHalf = text.substring(0, 8);
                String secondHalf = text.substring(8, text.length());
                Integer.parseInt(firstHalf);
                Integer.parseInt(secondHalf);
            }else if(text.length()>0){
                Integer.parseInt(text);
            }
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }


    /**
     * Checks if the monitoring system is receiving notifications before they are generated.
     *
     * @return true if the received timestamp is greater than the generated timestamp and false if not.
     */
    protected boolean timeStampValid() {
        int recHalfUpper = Integer.parseInt(received.substring(0, 8));
        int recHalfLower = Integer.parseInt(received.substring(8, received.length()));
        int genHalfUpper = Integer.parseInt(generated.substring(0, 8));
        int genHalfLower = Integer.parseInt(generated.substring(8, generated.length()));

        return recHalfUpper >= genHalfUpper && (recHalfLower > genHalfLower);
    }


    /**
     * Resets the variables to null values.
     */
    protected void clearVariables(){
        received = generated = node1 = node2 = notification = null;
    }


    /**
     * Checks if the current notification was generated before the previous one.
     * If so, the message is out of sync and the method returns false.
     *
     * @return true if the current message was generated after the previous, false if not.
     */
    protected boolean inSync(){
        if (previousGenerated == null){
            previousGenerated = generated;
        }

        boolean inSync;

        int intGenerated, intPreviousGenerated;
        intGenerated = Integer.parseInt(generated.substring(generated.length()-5, generated.length()));
        intPreviousGenerated = Integer.parseInt(previousGenerated.substring(previousGenerated.length()-5, previousGenerated.length()));

        if (intPreviousGenerated>intGenerated){
            inSync = false;
        }else{
            inSync = true;
        }
        return inSync;
    }
}