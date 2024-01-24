import java.io.*;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    // single threaded - one client at a time
    // To make this we need a socket - which is an endpoint of communication between two computers.
    // An endpoint is a combination of an IP address and a port number. Sockets are used to send adn receive data
    // TCP
    public static void main(String[] args) {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try {
            socket = new Socket("localhost", 1234); // can connect to this server
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            // to improve efficiency
            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            // because input is from the console
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // sends message to server
                String messageToSend = scanner.nextLine().trim();
                bufferedWriter.write(messageToSend);
                bufferedWriter.newLine();
                // writes a line separator to the  underlying streams.
                // This is important as the scanner's nextLine() method does not return a line separator.
                bufferedWriter.flush();

                // reads the message from the server
                System.out.println("Server: " + bufferedReader.readLine());
               // does not know the line has ended if we don't send the line separator

                if(messageToSend.equalsIgnoreCase("BYE")){
                    break;
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // to make sure everything is closed right
                // checking for null before closing the streams to avoid a null pointer exception
                if(socket != null){
                     socket.close();
                }
                if(inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if(outputStreamWriter != null){
                    outputStreamWriter.close();
                }
                if(bufferedReader != null){
                    bufferedReader.close();
                }
                if(bufferedWriter != null){
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
