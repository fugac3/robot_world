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

## Build and Run Instructions
Running Tests and Building the Project


If all tests pass, run the following command to clean, install dependencies, compile, and package your project:

    mvn clean install compile package

If some tests are failing or you want to skip tests, run:

    mvn clean install compile package -DskipTests

Navigating to the Build Output



After building, navigate to the directory where the packaged JAR file is located:

    cd target

You should see the JAR file, typically named something like:

    robot-world-0.0.2-jar-with-dependencies.jar

Running the Application

## To run the project from the terminal, use:
    
    java -jar robot-world-0.0.2-jar-with-dependencies.jar


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

## Robot World Client

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
Component	                Purpose
* ConnectionManager     -> Manages socket I/O streams safely
* CommandHandler	    -> Parses and processes client commands
* Response	            -> Encapsulates server replies in a JSON-serializable object
* Gson	                -> Formats responses for readability
 

🧠 CommandHandler

Handles all robot-related client commands, manages robot creation, interaction with the world, and command execution logic.
📦 Package

package za.co.wethinkcode.robots.server;

🎯 Purpose

    Parses, interprets, and processes client commands.

    Creates and manages a Robot per client.

    Routes and executes robot commands (launch, move, turn, repair, quit, etc.).

    Maintains command-response cycle with the server’s world state (TextWorld).

🔧 Constructor

public CommandHandler(TextWorld world, ConnectionManager connectionManager, ClientHandler clientHandler)

Parameters:

    world: Shared instance of the TextWorld.

    connectionManager: Handles low-level socket communication.

    clientHandler: The per-client handler, used to track session state (e.g., if robot is dead).

📜 Key Methods
Response handleClientCommand(String msgFromClient)

Main method that:

    Parses client command.

    Validates command and robot state.

    Launches robot (if it's a launch command).

    Delegates all other commands to the robot.handleCommand() method.

    Returns a Response object or null for QUIT.

void disconnect()

Gracefully disconnects the client by stopping the connection.
void removeRobot()

Removes the client’s robot from the world if it exists.

⚙️ Command Logic
✅ Launch Command:

    Requires: launch <type> <name>

    Validates:

        No duplicate robot name.

        Valid robot type.

        Only one robot per client.

    Spawns robot at a free position.

    Returns initial robot state info (position, shield, etc.).

✅ Standard Commands:

    Includes: forward, back, turn, repair, status, quit

    Requires robot to have been launched.

    Blocks if robot is dead or currently repairing.

    repair is handled explicitly with a RepairCommand.

❌ Error Handling:

    Unknown command

    Launching with an invalid type

    Executing commands before launching

    Trying to launch multiple robots

🧠 Robot Lifecycle Summary

Event and Effect
* launch	-> Spawns robot into world
* Robot dies -> (health=0)	Removed from world, marked DEAD
* Falls into pit	-> Same as above
* quit	-> Disconnects client, removes robot
* repair	-> Temporarily blocks commands

📚 Example Flow

    Client: launch shieldBot Alpha
    Server: OK {"position": [0,0], "shield": 5, ...}

    Client: forward 5
    Server: OK {"position": [0,5]}

    Client: repair
    Server: OK {"message": "Repairing..."}

    Client: turn left
    Server: FAILED {"message": "Robot is currently repairing. Please wait."}

    Client: quit
    Server: Bye, Alpha!

🧩 Dependencies

    Command (for parsing commands)

    Request and Response (for structured messaging)

    TextWorld, Robot, RobotType, RobotCreator

    ConnectionManager (socket-level logic)

🔌 ConnectionManager

A utility class responsible for managing the lifecycle of a client’s socket connection in the robot server.
📦 Package

package za.co.wethinkcode.robots.server;

🎯 Purpose

The ConnectionManager class provides:

    A wrapper around a client's Socket connection.

    Methods to safely stop and retrieve the connection.

    Centralized socket lifecycle management, making the code cleaner and more testable.

🛠️ Constructor

public ConnectionManager(Socket socket)

    Parameter:

        socket: The client’s active socket connection.

🔧 Methods
public void stop()

Safely closes the socket if it is still open.

    Prevents IOException crashes due to attempts to close an already-closed socket.

    Called when the client disconnects or the server is shutting down.

public Socket getSocket()

    Returns: The wrapped Socket instance associated with the client.

    Use case: Needed to read/write data to the client stream.

🧱 Example Usage

Socket clientSocket = serverSocket.accept();
ConnectionManager connection = new ConnectionManager(clientSocket);

// Use socket for communication
BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getSocket().getInputStream()));
PrintWriter writer = new PrintWriter(connection.getSocket().getOutputStream(), true);

// On shutdown or error
connection.stop();

✅ Best Practices

    Use this class to abstract socket operations in server-side handlers.

    Always call stop() in a finally block or on server shutdown to release resources.

📚 Related

Used in:

    ClientHandler (as part of managing each client's connection)

    CommandHandler (to trigger disconnection on quit)


📬 Request

A simple data structure representing a command sent from a client to the robot server, typically over a socket connection. Used as part of the client-server communication protocol.
📦 Package

package za.co.wethinkcode.robots.server;

🎯 Purpose

The Request class encapsulates:

    The command a client wants to execute (e.g. launch, forward, turn).

    The arguments (parameters) associated with that command.

* This is used to pass structured command data into the server logic, typically after parsing from a raw input string.

🛠️ Constructor 

    public Request(String command, Map<String, Object> arguments)

Creates a new Request with a specific command and its arguments.
Parameters:

    command: A String representing the action to perform.

    arguments: A Map<String, Object> containing the key-value pairs relevant to that command (e.g. steps, direction, name).

public Request()

    A no-argument constructor required by Gson (or similar JSON libraries) for deserialization.

🔍 Methods
public String getCommand()

    Returns the command name as a String.

    public Map<String, Object> getArguments()

    Returns the arguments as a Map<String, Object>.

📚 Example Usage
Constructing a request manually:

    Map<String, Object> args = new HashMap<>();
    args.put("name", "Robo");
    args.put("type", "shieldBot");

    Request request = new Request("launch", args);

Example JSON (used with Gson):

    {
    "command": "forward",
    "arguments": {
    "steps": 5
    }
    }

🔗 Used By

    CommandHandler: to structure and interpret incoming client commands.

    Gson (or other serialization libs): to convert JSON payloads into Java objects.



🌍 TextWorld — Robot World Environment

The TextWorld class defines a grid-based world where robots operate, navigate, and interact with obstacles. It is a core component of the za.co.wethinkcode.robots.world package.
📦 Package

package za.co.wethinkcode.robots.world;

🎯 Purpose

    Represents the game world as a bounded rectangular grid.

    Tracks robots and obstacles on the grid.

    Supports singleton pattern to ensure one shared world instance.

    Provides methods for obstacle generation, position validation, and path checking.

🛠️ Key Features
Feature	and Description.
Singleton instance	-> Access via getInstance() methods
World boundaries	-> Defined by top-left and bottom-right Positions
Robot management	-> Add, remove, clear robots
Obstacle management	-> Generates random obstacles and checks blocking paths
Position validation	 ->Checks if a position is blocked by robot or obstacle
Path blockage checking	-> Determines if a path between two points is blocked
Pit detection	-> Identifies if path crosses bottomless pits

📐 World Boundaries

    Top-left and bottom-right positions define the rectangular bounds.

    These are loaded from a configuration via ConfigReader.

⚙️ Usage
Get the World Instance

TextWorld world = TextWorld.getInstance();

Optionally, create with custom bounds:

Position topLeft = new Position(-10, 10);
Position bottomRight = new Position(10, -10);
TextWorld world = TextWorld.getInstance(topLeft, bottomRight);

Robot Management

world.addRobot(robot);
Collection<Robot> robots = world.getAllRobots();
world.removeRobot(robot);
world.clearRobots();

Position Utilities

Position freePos = world.getRandomFreePosition();
boolean blocked = world.blocksPosition(somePosition);
boolean blockedPath = world.blocksPath(startPos, endPos);
boolean pathHasPit = world.pathContainsPit(startPos, endPos);

Reset the World

world.reset(true);  // Clears robots and obstacles, then generates new obstacles
world.reset(false); // Clears everything without generating obstacles

🧩 Internal Details

    Uses a Map<String, Robot> for efficient robot lookup.

    Obstacles are stored in a list inherited from AbstractWorld.

    Random obstacle placement respects the configured maximum obstacle count.

    Position checks ensure robots and obstacles do not overlap.

    Paths crossing "BOTTOMLESS_PIT" obstacles are handled distinctly.

📓 Notes

* The method getRandomFreePosition() may block indefinitely if the world is full (no free spaces).
* Singleton pattern ensures the world state is consistent across the application.
* Configuration loading (via ConfigReader) drives world size and obstacle counts.

📚 AbstractWorld

AbstractWorld is an abstract base class that provides foundational functionality for managing a game world populated by obstacles. It serves as a superclass for more specific world implementations such as TextWorld.
📦 Package

package za.co.wethinkcode.robots.world;

🧩 Purpose

    Holds and manages a list of obstacles within the world.

    Provides methods to generate random obstacles of different types.

    Supports visualization and checking of obstacle overlaps.

    Designed for extension by concrete world classes.

🔑 Key Features
Feature	and Description
Obstacle Storage	-> Stores obstacles in a List<Obstacle>
Obstacle Retrieval	-> Provides getter and setter for the obstacle list
Obstacle Visualization	-> Displays all obstacles with their coordinates
Random Obstacle Generation	-> Creates random obstacles within world bounds
Overlap Detection	-> Checks if two rectangular obstacles overlap

⚙️ Usage
Getting and Setting Obstacles

List<Obstacle> currentObstacles = world.getObstacles();
world.setObstacles(newObstacleList);

Displaying Obstacles

Prints a list of all obstacles and their bounding coordinates, or a message if none exist:

world.showObstacles();

Generating Random Obstacles

Creates random obstacles (MountainObstacle, LakesObstacle, or BottomlessPit) within the configured world boundaries, ensuring no overlapping between different obstacle types:

world.generateRandomObstacles(10); // generates up to 10 obstacles randomly

Overlap Checking

Checks if two rectangular obstacles overlap:

boolean doesOverlap = world.overlaps(obstacleA, obstacleB);

⚙️ Implementation Details

    Uses a list of obstacle classes (MountainObstacle, LakesObstacle, BottomlessPit) to randomly pick types during generation.

    Obstacle placement is constrained within the world boundaries defined by the TextWorld class.

    Attempts up to 50 times to place obstacles to avoid infinite loops.

    Ensures obstacles of different types do not overlap by checking bounding rectangles.

    Throws IllegalArgumentException if an unknown obstacle type is requested.

🚩 Notes

    This class does not manage robots, focusing solely on obstacles.

    Designed to be extended; subclasses may add additional features like robot tracking or world boundaries.

    The coordinate system uses bottom-left and top-right corners for rectangular obstacle positioning.

📚 AbstractWorld

AbstractWorld is an abstract base class that provides foundational functionality for managing a game world populated by obstacles. It serves as a superclass for more specific world implementations such as TextWorld.
📦 Package

package za.co.wethinkcode.robots.world;

🧩 Purpose

    Holds and manages a list of obstacles within the world.

    Provides methods to generate random obstacles of different types.

    Supports visualization and checking of obstacle overlaps.

    Designed for extension by concrete world classes.

🔑 Key Features

Feature	Description
Obstacle Storage	Stores obstacles in a List<Obstacle>
Obstacle Retrieval	Provides getter and setter for the obstacle list
Obstacle Visualization	Displays all obstacles with their coordinates
Random Obstacle Generation	Creates random obstacles within world bounds
Overlap Detection	Checks if two rectangular obstacles overlap
⚙️ Usage
Getting and Setting Obstacles

List<Obstacle> currentObstacles = world.getObstacles();
world.setObstacles(newObstacleList);

Displaying Obstacles

Prints a list of all obstacles and their bounding coordinates, or a message if none exist:

world.showObstacles();

Generating Random Obstacles

Creates random obstacles (MountainObstacle, LakesObstacle, or BottomlessPit) within the configured world boundaries, ensuring no overlapping between different obstacle types:

world.generateRandomObstacles(10); // generates up to 10 obstacles randomly

Overlap Checking

Checks if two rectangular obstacles overlap:

boolean doesOverlap = world.overlaps(obstacleA, obstacleB);

⚙️ Implementation Details

    Uses a list of obstacle classes (MountainObstacle, LakesObstacle, BottomlessPit) to randomly pick types during generation.

    Obstacle placement is constrained within the world boundaries defined by the TextWorld class.

    Attempts up to 50 times to place obstacles to avoid infinite loops.

    Ensures obstacles of different types do not overlap by checking bounding rectangles.

    Throws IllegalArgumentException if an unknown obstacle type is requested.

🚩 Notes

    This class does not manage robots, focusing solely on obstacles.

    Designed to be extended; subclasses may add additional features like robot tracking or world boundaries.

    The coordinate system uses bottom-left and top-right corners for rectangular obstacle positioning.



🌍 WorldConfig

WorldConfig defines the configuration parameters for the robot world, including the boundaries and maximum obstacles allowed.
📦 Package

package za.co.wethinkcode.robots.world;

🔑 Purpose

    Encapsulates the size and limits of the world.

    Defines the top-left and bottom-right coordinates of the world boundary.

    Sets the maximum number of obstacles that can exist in the world.

🧱 Fields

Field	Type	Description
topLeft	Position	Coordinates of the world’s top-left corner
bottomRight	Position	Coordinates of the world’s bottom-right corner
maxObstacles	int	Maximum number of obstacles allowed in the world
⚙️ Constructor

public WorldConfig(int length, int height, int maxObstacles)

    Constructs a WorldConfig instance based on the provided length and height.

    Calculates the topLeft and bottomRight positions centered around (0,0).

    Sets the maximum number of obstacles.

Parameters

    length — Total width of the world (X-axis).

    height — Total height of the world (Y-axis).

    maxObstacles — The maximum count of obstacles to generate.

⚙️ Example Usage

    WorldConfig config = new WorldConfig(40, 20, 10);

    System.out.println("Top Left: " + config.topLeft);       // e.g. (-20, 20)
    System.out.println("Bottom Right: " + config.bottomRight); // e.g. (10, -10)
    System.out.println("Max Obstacles: " + config.maxObstacles); // 10

🧠 Notes

    The world coordinates are centered on (0,0) by splitting length and height evenly.

    topLeft and bottomRight define a rectangular boundary used to place robots and obstacles.

📄 ConfigReader

The ConfigReader class is a utility responsible for loading and parsing world configuration settings from an external config.properties file.
📦 Package

package za.co.wethinkcode.robots.world;

🎯 Purpose

    Loads the simulation's world size and obstacle limits from a .properties file.

    Provides a single static method to generate a WorldConfig object based on the file content.

🧩 How It Works

    Reads config.properties from the classpath.

    Extracts and parses values:

        world.length

        world.height

        max.obstacles

    Uses those values to instantiate and return a WorldConfig.

⚙️ Method Summary

public static WorldConfig loadConfig()

    Loads properties from config.properties.

    Converts the read string values into integers.

    Returns a configured WorldConfig object.

    Throws a RuntimeException if:

        The file is missing.

        Any property is missing or not an integer.

        There is an I/O error.

🔧 Expected config.properties Format

    world.length=40
    world.height=20
    max.obstacles=10

Ensure the file is placed under src/main/resources/ or is available in the runtime classpath.
💥 Error Handling

Throws:

    RuntimeException if:

        File is not found.

        Required keys are missing.

        Values cannot be parsed to integers.

✅ Example Usage

    WorldConfig config = ConfigReader.loadConfig();
    System.out.println("World size: " + config.topLeft + " to " + config.bottomRight);
    System.out.println("Max Obstacles: " + config.maxObstacles);

📁 Placement

Ensure your project structure includes:

    src/
    └── main/
    └── resources/
    └── config.properties

