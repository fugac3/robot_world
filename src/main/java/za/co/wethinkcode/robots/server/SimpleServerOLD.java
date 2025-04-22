package za.co.wethinkcode.robots.server;

import za.co.wethinkcode.flow.Recorder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServerOLD {

    public static void main(String[] args) throws IOException {
//        throw new UnsupportedOperationException( "TODO" );

        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        ServerSocket serverSocket = null;

        serverSocket = new ServerSocket(4444);

//        flag for client wanting to shut down the server
        boolean serverRunning = true;
        System.out.println("Server started. Listening on port " + serverSocket);

        while (serverRunning) {
            try {
                socket = serverSocket.accept();
                                          // char <-- to      // <-- bytes
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
//                Groups the char to all be sent at once
                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                System.out.println("Client connected.");
                while (true) {
                    String msgFromClient = bufferedReader.readLine();
                    System.out.println("Client: " + msgFromClient);

                    bufferedWriter.write("msg received");
//                    client reads with readline(). without client would hang waiting forever.
                    bufferedWriter.newLine();
//                    flush sends the data in the buffer
                    bufferedWriter.flush();

                    if (msgFromClient.equalsIgnoreCase("SHUTDOWN")) {
                        System.out.println("Server shutting down");
                        serverSocket.close();
                        serverRunning = false;
                        break;
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
                serverRunning = false;
            } finally {
                // Ensure resources are cleaned up, even if an error occurs
                try {
                    if (socket != null && !socket.isClosed()) socket.close();
                    if (bufferedReader != null) bufferedReader.close();
                    if (bufferedWriter != null) bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }
    // The following initialisation is REQUIRED for flow monitoring.
    // DO NOT REMOVE OR MODIFY THIS CODE.
//    static {
//        new Recorder().logRun();
//    }
}