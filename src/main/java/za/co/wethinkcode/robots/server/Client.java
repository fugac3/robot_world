package za.co.wethinkcode.robots.server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
//fdg

public class Client {
    public static void main(String[] args) {


        PortNum portNum = new PortNum(4433);

        Socket socket = null;
//        InputStreamReader inputStreamReader = null;  //byte based
//        OutputStreamWriter outputStreamWriter = null;    // char based output stream. byte to char stream
        BufferedReader bufferedReader = null;    // large block/array of char at a time.
        BufferedWriter bufferedWriter = null;    // Not good for files of text

        try {
            socket = new Socket("localhost", portNum.getPort());
//            socket = new Socket("localhost", portNum.getPort());
            System.out.println("Connected to server.");

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));    //End in Stream is byte     //Not end in Stream so = char
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            Scanner scanner = new Scanner(System.in);

            String clientName;
            System.out.println("Enter your name: ");
            clientName = scanner.nextLine();
            System.out.println("Hello: "+ clientName);

            bufferedWriter.write(clientName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            while (true) {

                String msgToSend = scanner.nextLine();
                bufferedWriter.write(msgToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();


                String response = bufferedReader.readLine();
                if (response == null) {
                    System.out.println("Server closed the connection.");
                    break;
                }


                System.out.println("Server: " + response);

                if (msgToSend.equalsIgnoreCase("BYE")) {
                    System.out.println("Bye " + clientName);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to connect to server. Is it running?");
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) socket.close();
                if (bufferedReader != null) bufferedReader.close();
                if (bufferedWriter != null) bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}