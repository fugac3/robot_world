package za.co.wethinkcode.robots.server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        int Port = 4433;

        Socket socket = null;
//        InputStreamReader inputStreamReader = null;  //byte based
//        OutputStreamWriter outputStreamWriter = null;    // char based output stream. byte to char stream
        BufferedReader bufferedReader = null;    // large block/array of char at a time.
        BufferedWriter bufferedWriter = null;    // Not good for files of text
//        Scanner scanner = new Scanner(in);
        String clientName;

        try {
            socket = new Socket("localhost",Port);
            System.out.println("Connected to server.");

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));    //End in Stream is byte     //Not end in Stream so = char
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Enter your name: ");
                clientName = scanner.nextLine().trim();
                if (clientName.isBlank()) {
                    System.out.println("Invalid name. Try again.");
                }
                else {
                    break;
                }
            }

            bufferedWriter.write(clientName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            // Receive greeting/confirmation from server
            String welcomeMsg = bufferedReader.readLine();
            System.out.println("Server: " + welcomeMsg);

            while (true) {
                System.out.print("> "); // prompt for input
                String msgToSend = scanner.nextLine();

                if (msgToSend.isEmpty()) {
                    msgToSend = "null";
                }

                bufferedWriter.write(msgToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                StringBuilder fullResponse = new StringBuilder();
                String line;

                // Read until the "===END===" marker
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.equals("===END===")) {
                        break;
                    }
                    fullResponse.append(line).append("\n");
                }

                System.out.println("Server:\n" + fullResponse);

                if (msgToSend.equalsIgnoreCase("QUIT")) {
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