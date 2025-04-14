package za.co.wethinkcode.robots.server;

import za.co.wethinkcode.flow.Recorder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
//        throw new UnsupportedOperationException( "TODO" );

        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        ServerSocket serverSocket = null;

        serverSocket = new ServerSocket(1234);

        while (true){
            try{
                socket = serverSocket.accept();

                inputStreamReader= new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                while (true){
                    System.out.println("Client connected.");
                    String msgFromClient = bufferedReader.readLine();
                    System.out.println("Client: "+msgFromClient);

                    bufferedWriter.write("msg received");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    if(msgFromClient.equalsIgnoreCase("BYE"))
                        break;

                }

                socket.close();
                inputStreamReader.close();
                bufferedWriter.close();
                bufferedReader.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



    }

    // The following initialisation is REQUIRED for `flow` monitoring.
    // DO NOT REMOVE OR MODIFY THIS CODE.
    static {
        new Recorder().logRun();
    }

}
