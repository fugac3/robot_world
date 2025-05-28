# oop-ex-toy-robot-group

## Getting Started
This project is a `Java` project using `maven` as build tool.

The structure is as follow:
* `src/main/java` - in here is some skeleton code that you must use as starting point for the problem.
* `src/test/java` - add your unittests here (your unittests will also be reviewed)

### IntelliJ
To open it in `IntelliJ` IDE:
1. _File_ -> _New_ -> _Project from Existing Sources..._
1. Select the directory where this code has been checked out to by the LMS
1. Choose _External Model_ as *Maven*

## Build, Test & Run
You may use IntelliJ to run your code and tests, but alternatively you can use the Maven build tool:
* First ensure you are in the root directory of the project
* To compile your code, run: `mvn compile`
* To run the tests: `mvn test`
* To run your application: `mvn compile exec:java`

## Server
This module is the server component for the za.co.wethinkcode.robots project. It handles incoming client
connections, manages the game world (TextWorld), and coordinates client communication using multithreaded handlers.

## Package
package za.co.wethinkcode.robots.server;

## Features
* Accepts multiple client connections via sockets.
* Uses a singleton TextWorld instance shared among all clients.
* Multithreaded client handling using ClientHandler.
* Graceful shutdown of server and client connections.
* Integrated with Recorder for flow monitoring (do not remove).

## Dependancies 
* Internal dependancies - za.co.wethinkcode.robots.world.TextWorld
                        - za.co.wethinkcode.flow.Recorder

## Architecture Overview 
* ServerSocket: Listens for incoming connections on port 4402. 
* Socket: Each client gets a dedicated socket for communication.
* ClientHandler: Runnable class that handles interaction with a single client.
* TextWorld: Shared game state (singleton) used by all clients.

## Usage 
Use this command to start the server. 

## Shutting down 
Use this to shuttdown the server. 

* Closes the ServerSocket.
* Cleans up all resources.
* Outputs shutdown status to the console.

## Note
* Recorder().logRun() is used to monitor execution flow.


## Client

🤖 Robot World Client

This module acts as a console-based client that connects to the Robot World Server. It allows users to input commands, send them to the server, and receive structured responses based on game logic handled on the server side.
Package

package za.co.wethinkcode.robots.server;

## Features

* Connects to the server on localhost:4402
* Prompts for a valid username
* Sends typed commands to the server
* Receives structured, multi-line responses terminated by an ===END=== marker
* Exits cleanly on QUIT command

## Dependencies
* No external libraries

## Usage
Starting the Client

Compile the client:
* javac za/co/wethinkcode/robots/server/Client.java

Run the client:
* java za.co.wethinkcode.robots.server.Client

You should see:

    Connected to server.
    Enter your name:

Interaction Example

Connected to server.
Enter your name: robot123
Server: Hello robot123, welcome to the world!
> launch
Server:
{ "result": "OK", "state": {...} }
> QUIT
Server:
{ "result": "OK", "message": "Goodbye robot123!" }

## Implementation Details
* Uses BufferedReader and BufferedWriter for efficient socket I/O
* Relies on newline (\n) to mark end of each message
* Uses "===END===" to determine full server response
* Falls back to "null" message if input is empty (for protocol compliance)

## Cleanup
All network and stream resources are closed safely in a finally block to ensure no resource leaks on disconnect or error.

## Notes
* The client must match the server protocol and expect structured messages.
* The server must be running before the client is started.
* A valid name (non-blank) is required before communication begins.


## ClientHandler – Robot World Server
* ClientHandler is a core class in the za.co.wethinkcode.robots.server package. It manages a single client's connection to the server, processes their commands, and maintains session state, including whether the robot is "alive" or "dead".

## Package
* package za.co.wethinkcode.robots.server;

## Purpose
* Handles one client per thread.
* Reads user input (commands) via socket.
* Delegates command execution to CommandHandler.
* Formats server responses in pretty-printed JSON using Gson.
* Enforces rules like disabling input after robot death.

## Key Components
Component	Purpose
ConnectionManager  |Manages socket I/O streams safely
CommandHandler	   |Parses and processes client commands
Response	       |Encapsulates server replies in a JSON-serializable object
Gson	           |Formats responses for readability
