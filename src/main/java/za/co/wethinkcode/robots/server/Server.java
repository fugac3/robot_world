package za.co.wethinkcode.robots.server;

import za.co.wethinkcode.flow.Recorder;
import za.co.wethinkcode.robots.serverCommands.DumpCommand;
import za.co.wethinkcode.robots.serverCommands.RobotList;
import za.co.wethinkcode.robots.serverCommands.RobotsCommand;
import za.co.wethinkcode.robots.serverCommands.ShutdownCommand;
import za.co.wethinkcode.robots.world.TextWorld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Server {
    private static volatile boolean running = true;
    private static final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    private static ServerSocket serverSocket;
    private static final TextWorld world = TextWorld.getInstance();
    private static final List<Thread> clientThreads = Collections.synchronizedList(new ArrayList<>());


    public static boolean isRunning() {
        return running;
    }

    public static void setRunning(boolean value) {
        running = value;
    }

    public static void main(String[] args) {
        int port = 4400;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started. Listening on port " + port);

            // creating a background thread to listen for server-only commands (consoleThread)
            Thread consoleThread = getThread();
            consoleThread.start();


            while (running) {
                //Socket object :
                //is the individual connection between server and one specific client.
                //The ServerSocket is listening for new connections.
                //When client connects, serverSocket.accept():
                //returns Socket object that represents the connection to that client.
                //clientSocket is used to read and write from server to that specific client.
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket, world);
                clients.add(handler);

                Thread thread = new Thread(handler);
                clientThreads.add(thread);
                thread.start();
            }
        } catch (IOException e) {
            if (running) {
                System.out.println("Server error: " + e.getMessage());
            } else {
                System.out.println("Server shut down.");
            }
        } finally {
            ShutdownCommand.shutdownServer(serverSocket,clients); // Clean up even if crash
        }
    }


    /**
     * Low priority thread that runs in the background and provides support for non-damon threads.
     * It will automatically terminate when all other non-daemon threads complete (all users disconnect).
     * Normal threads run until completion.
     * If it was a normal thread it will keep running forever waiting for input,
     * unless the main process exits.
     *
     * Blocked on readLine() (i.e., waiting for input) so the while loop would only
     * close when null was read, but it only closes when:
     * The input stream (System.in, a socket, or file) is closed,
     * or EOF is reached (like Ctrl+D in a terminal).
     *
     * Situation	                            Behavior
     * Non-daemon thread blocked on readLine()  readLine()	JVM waits forever unless stream is closed
     * Daemon thread blocked on readLine()      JVM will exit once all user threads are done
     */

    private static Thread getThread() {
        Thread consoleThread = new Thread(() -> {
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String command;
            try {
                while ((command = consoleReader.readLine()) != null) {
                    handleServerCommand(command.trim());
                }
            } catch (IOException e) {
                System.out.println("Error reading server command: " + e.getMessage());
            }
        });
        // If it's the only thread still running, the JVM can shut down
        consoleThread.setDaemon(true); // allow JVM to exit if only this is left
        return consoleThread;
    }

    private static void handleServerCommand(String command) {
//        world.get
        List<Map<String, Object>> allRobots = RobotList.getAllRobotsInfo();
        String formatted = RobotsCommand.formatRobotList(allRobots);
        switch (command.toLowerCase()) {
            case "robots":
                System.out.println(formatted);
                break;

            case "dump":
                System.out.println("== Objects in World ==");
                System.out.println();
                System.out.println(formatted);
                DumpCommand.dumpWorldState(world);
                break;

            case "shutdown":
                System.out.println("Shutting down the server...");
                setRunning(false);
                try {
                    serverSocket.close();  // 🔥 This unblocks serverSocket.accept()
                } catch (IOException e) {
                    System.out.println("Error closing server socket: " + e.getMessage());
                }
                break;

            default:
                System.out.println("Unknown server command: " + command);
        }
    }

    // The following initialisation is REQUIRED for `flow` monitoring.
    // DO NOT REMOVE OR MODIFY THIS CODE.
    static {
        new Recorder().logRun();
    }
}