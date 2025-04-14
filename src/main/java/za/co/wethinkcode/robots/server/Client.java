package za.co.wethinkcode.robots.server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
//
//public class Client {
//    private Socket socket;
//    private BufferedReader reader;
//    private BufferedWriter writer;
//
//    public Client(String host, int port) throws IOException {
//        socket = new Socket(host, port);
//        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//    }
//
//
//
//    public void sendMessage(String message) throws IOException {
//        writer.write(message);
//        writer.newLine();
//        writer.flush();
//    }
//
//    public String receiveMessage() throws IOException {
//        return reader.readLine();
//    }
//
//    public void close() {
//        try {
//            if (socket != null) socket.close();
//            if (reader != null) reader.close();
//            if (writer != null) writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}



public class Client {
    public static void main(String[] args) {

        Socket socket = null;
        InputStreamReader inputStreamReader = null;  //byte based
        OutputStreamWriter outputStreamWriter = null;    // char based output stream. byte to char stream
        BufferedReader bufferedReader = null;    // large block/array of char at a time.
        BufferedWriter bufferedWriter = null;    // Not good for files of text

        try {
            socket = new Socket("localhost", 1234);

            inputStreamReader = new InputStreamReader(socket.getInputStream());     //End in Stream is byte
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            bufferedReader = new BufferedReader(inputStreamReader);     //Not end in Stream so = char
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            Scanner scanner = new Scanner(System.in);

            while (true) {

                String msgToSend = scanner.nextLine();
                bufferedWriter.write(msgToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                System.out.println("Server: " + bufferedReader.readLine());

                if (msgToSend.equalsIgnoreCase("BYE"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null)
                    socket.close();
                if (inputStreamReader != null)
                    inputStreamReader.close();
                if (outputStreamWriter != null)
                    outputStreamWriter.close();
                if (bufferedReader != null)
                    bufferedReader.close();
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
