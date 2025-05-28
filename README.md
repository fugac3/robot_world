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

Dependancies:

  <!-- Add this plugin to build an uber JAR with dependencies -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId>
                <version>3.3.0</version>
                <configuration>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                    <archive>
                        <manifest>
                            <mainClass>za.co.wethinkcode.robots.server.Server</mainClass>
                        </manifest>
                    </archive>
                </configuration>
                <executions>
                    <execution>
                        <id>make-assembly</id>
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>


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
 

## 🧠 CommandHandler

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

## 🔌 ConnectionManager

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


## 📬 Request

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

## 📦 Response

The Response class represents a structured reply sent from the server to a client after processing a command. It includes:

    A result (e.g. "OK", "ERROR", "DEAD")

    Data relevant to the response (e.g. a message, obstacle info, etc.)

    The robot’s current state, if applicable

📁 Package

package za.co.wethinkcode.robots.server;

🎯 Purpose

This class is part of the communication protocol between server and clients in the robot world. It:

    Standardizes how server replies are structured

    Includes contextual data

    Optionally includes the state of the robot when needed

🛠️ Constructor

    public Response(String result, Map<String, Object> data, Robot robot)

Constructs a new response.
Parameters:

    result: String — the status of the command (e.g., "OK", "ERROR", "DEAD")

    data: Map<String, Object> — arbitrary data (e.g., messages, error reasons, etc.)

    robot: Robot — if not null, a snapshot of the robot’s state is captured

🔧 Methods

    public String getResult()

    Returns the result string (e.g. "OK", "ERROR").
    public Map<String, Object> getData()

    Returns the data map containing additional response info.
    public Map<String, Object> getState()

    Returns the robot state map (if a robot was provided), containing:

    position: [x, y]

    direction: current direction (e.g. NORTH, EAST)

    Shields: current shield strength

    shots: remaining ammo

    status: current status (e.g. ALIVE, DEAD)

🧱 Internal Helper

    public static Map<String, Object> buildState(Robot robot)

* Constructs the robot state section used in the response. This is used internally when robot != null.

📦 Example Usage

    Map<String, Object> data = Map.of("message", "Move completed.");

    Response response = new Response("OK", data, robot);

Example JSON output (with Gson)

    {
    "result": "OK",
    "data": {
    "message": "Robot moved successfully"
    },
    "state": {
    "position": [5, 10],
    "direction": "NORTH",
    "Shields": 3,
    "shots": 2,
    "status": "ALIVE"
    }
}

🔗 Used In

    ClientHandler: to send structured responses back to the client

    CommandHandler: when forming reply objects after processing commands



## 🌍 TextWorld — Robot World Environment

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

## 📚 AbstractWorld

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

*Prints a list of all obstacles and their bounding coordinates, or a message if none exist:

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

## 📄 ConfigReader

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


✅ Summary of the Robot Class

🎯 Purpose:

Represents a robot in a 2D world. Each robot has:

    A type (RobotType) that defines its capabilities.

    Position and movement logic.

    Shield/health system.

    Repair mechanism.

    Command execution and history.

🔧 Key Attributes:

Field and Description
* position	 -> Current (x, y) position of the robot
* currentDirection	->Direction robot is facing (N, S, E, W)
* ammo, maxAmmo	->Bullet count and cap
* currentShieldStrength	->Defensive barrier before losing health
* robotHealth	-> Number of lives (default = 1)
* repairTime	->Fixed 10s repair duration (blocking action)
* commands	->Keeps command history
* type	-> Robot type (e.g., Tank, Scout)

🧠 Behavioral Logic
Movement

    updatePosition(int steps) respects:

        Obstacles via blocksPath

        Pits via pathContainsPit (causes death)

        World bounds and other robots

Combat

    applyDamage(int damage) depletes shields first, then health

    Death triggers removal from the world

Repair

    Uses a background thread to simulate time delay

    Restores full shield (could consider partial/incremental repair?)

Command Execution

    handleCommand(Command) executes and stores it

    Returns Response for client/server communication

🧪 Suggestions / Fixes
✅ 1. Fix Health Initialization

    this.robotHealth = getRobotHealth();
    
    This line uses getRobotHealth() which returns 1 before assignment. It's redundant and misleading.

👉 Replace it with:

    this.robotHealth = 1;

✅ 2. Cap Shield Repair

    currentShieldStrength += maxShieldStrength;

This allows shield to exceed maxShieldStrength. Better:

    currentShieldStrength = maxShieldStrength;

✅ 3. Improve Error Status Messages

    default:
    status = "ERROR";

The "ERROR" status seems disconnected unless it’s tracked elsewhere. Consider handling error logging more explicitly or throwing an exception.
✅ 4. Thread Safety (Optional)

If your game scales, robot objects may need thread-safe state handling (e.g., for isRepairing, position, status).
📌 Nice Touches

    Good use of toString() for debug output

    lastMoveReason helps UX/debugging

    Differentiated world boundaries and robot collision logic

📘 Example Use

    RobotType type = new TankRobot();
    TextWorld world = TextWorld.getInstance();
    Position pos = world.getRandomFreePosition();
    Robot tank = new Robot("Tanker", world, pos, type);
    
    tank.updatePosition(3);
    tank.applyDamage(2);
    tank.repairing();



## 🤖 BasicRobot

* The BasicRobot class defines a default robot type used in the robot world. It extends the abstract RobotType class and sets the baseline attributes for a generic robot.

📁 Package

    package za.co.wethinkcode.robots.robotTypes;

🎯 Purpose

Represents the basic/default robot in the game, with standard stats for:

    Shields

    Ammo

    Repair capacity

* It is intended to be the simplest and most balanced robot available.

🧱 Constructor
public BasicRobot()

Creates a new BasicRobot instance with the following default values:

    Attribute	Value
    Name	"Basic"
    Max Shields	3
    Ammo Capacity	3
    Repair Capacity	3

These are passed to the parent RobotType constructor:

super("Basic", 3, 3, 3);

🧬 Inheritance

    Extends: RobotType

public class BasicRobot extends RobotType

🗃️ Example Usage

Used during the robot launch process:
    
    RobotType type = new BasicRobot();
    Robot myBot = new Robot("Robo1", world, startPosition, type);

Or dynamically created by the RobotCreator:

RobotType type = RobotCreator.createRobotType("basic");

🔗 Related

    RobotType – the abstract base class

    RobotCreator – for dynamic creation based on type name

    Other subclasses like SniperRobot, TankRobot (if defined)

## 🛡️ HeavyRobot

* The HeavyRobot class defines a durable robot type designed to endure and persist longer in the game world. It inherits from the abstract RobotType class and sets attributes that favor defense and firepower over repair capability.

📁 Package

    package za.co.wethinkcode.robots.robotTypes;

🎯 Purpose

Represents a tank-like robot with increased shield and ammo capacity, but limited repair ability.
🧱 Constructor

    public HeavyRobot()

Initializes a HeavyRobot instance with the following attributes:
    
    Attribute	Value
    Name	"Heavy"
    Max Shields	4
    Ammo Capacity	4
    Repair Capacity	2

This configuration is passed to the parent constructor:

super("Heavy", 4, 4, 2);

🧬 Inheritance

    Extends: RobotType

public class HeavyRobot extends RobotType

⚙️ Characteristics
Feature and	Description
Durability	-> High shield strength means it survives longer in combat.
Firepower	-> High ammo capacity allows more attacks.
Repair	-> Lower repair value — it's not designed to self-maintain frequently.

🗃️ Example Usage

Created manually:

    RobotType type = new HeavyRobot();

Or dynamically via:

RobotType type = RobotCreator.createRobotType("heavy");

🔗 Related Classes

    RobotType – the base class for all robot types.

    RobotCreator – creates RobotType instances from string type names.

    BasicRobot, SniperRobot, etc. – other robot configurations.


🏭 RobotCreator

The RobotCreator class is a factory utility used to instantiate different types of robots based on a string identifier. It centralizes robot creation logic to support easy expansion and consistency across your codebase.
📁 Package

package za.co.wethinkcode.robots.robotTypes;

🎯 Purpose

Creates instances of various RobotType subclasses based on user input (e.g., "scout", "tank"). This allows for flexible and extensible robot launching without tightly coupling logic to specific classes.
⚙️ Method
public static RobotType createRobotType(String robotType)

Description:
Returns a new instance of a subclass of RobotType corresponding to the input string.

Parameters:
Name	Type	Description
robotType	String	The name of the robot type (case-insensitive)

Returns:

    A new instance of a subclass of RobotType, or

    null if the type is unknown or robotType is null.

🤖 Supported Robot Types

Type String	Robot Class	Description

    "basic"	BasicRobot	Balanced robot with default stats.
    "tank"	TankRobot	High defense, low mobility.
    "scout"	ScoutRobot	Fast and agile with low defenses.
    "sniper"	SniperRobot	Long-range attacks, fragile build.
    "heavy"	HeavyRobot	High shields and ammo, low repair.

🧪 Example Usage

RobotType robot = RobotCreator.createRobotType("scout");

    if (robot != null) {
    System.out.println("Created robot of type: " + robot.getTypeName());
    } else {
    System.out.println("Invalid robot type.");
    }

🚫 Handling Unknown Types

If a user enters an unrecognized robot type (e.g. "wizard"), the method returns null, allowing the caller to handle it gracefully.
🔗 Related Classes

    RobotType – Abstract superclass for all robot types.

    BasicRobot, TankRobot, ScoutRobot, SniperRobot, HeavyRobot – Concrete subclasses of RobotType.

    CommandHandler – Uses RobotCreator to instantiate robots during the "launch" command.

## 📦 RobotType

The RobotType class represents the blueprint for different robot categories by encapsulating their core attributes such as shield strength, ammunition, and shooting range.
⚙️ Class Definition

    public class RobotType {
    private final String typeName;
    private final int maxShieldStrength;
    private final int maxShots;
    private final int shootingRange;

    public RobotType(String typeName, int maxShieldStrength, int maxShots, int shootingRange) {
        // constructor implementation
    }

    // Getters...
}

🧩 Attributes
Attribute	Type	Description

    typeName	-> String -> The name/identifier of this robot type (e.g., "Basic", "Heavy").
    maxShieldStrength	->int ->	Maximum shield durability; how many hits the shield can absorb.
    maxShots	->int-> 	Maximum number of shots (ammo) before requiring a reload.
    shootingRange	->int-> 	Maximum distance this robot can shoot.
🔨 Constructor

public RobotType(String typeName, int maxShieldStrength, int maxShots, int shootingRange)

    Initializes the robot type with the provided values for its name, shield strength, ammunition, and range.

🧮 Methods
Method	Returns	Description

    getTypeName()	String	Returns the robot type name.
    getMaxShieldStrength()	int	Returns the max shield strength.
    getMaxShots()	int	Returns the max number of shots.
    getShootingRange()	int	Returns the maximum shooting range.

📋 Usage Example

    RobotType heavy = new RobotType("Heavy", 4, 4, 2);
    
    System.out.println("Robot Type: " + heavy.getTypeName());
    System.out.println("Max Shields: " + heavy.getMaxShieldStrength());
    System.out.println("Max Shots: " + heavy.getMaxShots());
    System.out.println("Shooting Range: " + heavy.getShootingRange());


📦 ScoutRobot

A specialized robot type optimized for scouting, with:

    Lower shield strength (2)

    Higher ammo capacity (4 shots)

    Extended shooting range (5)

⚙️ Class Definition

    public class ScoutRobot extends RobotType {
    public ScoutRobot() {
    super("Scout", 2, 4, 5);
    }
    }

🧩 Characteristics
Attribute	Value	Description

    typeName	"Scout"	Name identifying the robot type
    maxShieldStrength	2	Moderate durability shield
    maxShots	4	More shots before reload
    shootingRange	5	Longer shooting distance

🔨 Usage

Creating a scout robot type instance:

    RobotType scout = new ScoutRobot();
    System.out.println(scout.getTypeName());  // Outputs: Scout


## 📦 SniperRobot

A specialized robot type optimized for precision shooting with:

    Low shield strength (1)

    Single shot capacity before reload (1)

    Long shooting range (5)

⚙️ Class Definition

    public class SniperRobot extends RobotType {
    public SniperRobot() {
    super("Sniper", 1, 1, 5);
    }
    }

🧩 Characteristics

    Attribute	Value	Description
    typeName	"Sniper"	Robot type name
    maxShieldStrength	1	Very low durability shield
    maxShots	1	Only one shot before reloading
    shootingRange	5	Longest shooting distance
🔨 Usage Example

    RobotType sniper = new SniperRobot();
    System.out.println(sniper.getTypeName());  // Outputs: Sniper

## 🛡️ TankRobot — Heavy-Duty Robot Class

The TankRobot class extends the RobotType and defines a robust combat-style robot focused on durability and firepower, but with limited range.
✅ Class Definition

public class TankRobot extends RobotType {

    public TankRobot() {
        super("Tank", 5, 5, 1);
    }
}

🔧 Properties
Property, Value and	Description

    typeName	"Tank"	Robot type identifier
    maxShieldStrength	5	High durability; can take many hits
    maxShots	5	High ammo capacity
    shootingRange	1	Limited to close-range combat
🔍 Use Case

Tank robots are best used for:

    Close-quarters combat

    Soaking damage while attacking repeatedly

    Acting as a frontline unit in a multi-robot environment

💡 Example

    RobotType tank = new TankRobot();
    System.out.println("Type: " + tank.getTypeName());
    System.out.println("Shields: " + tank.getMaxShieldStrength());


Robot Command System

This project is a command framework for controlling robots in a virtual world. It supports different types of robots, each with unique capabilities, and allows execution of various commands such as movement, firing, repairing, and more.
Overview

The system processes textual commands that control robot actions. Commands are parsed from strings, converted into command objects, and executed on robot instances. The robots operate within a shared world that handles collisions, obstacles, and robot states.
Features

    Command Pattern: Each action is encapsulated as a Command object with a standardized execution interface.

    Multiple Robot Types: Robots like Basic, Scout, Heavy, Tank, and Sniper with different stats.

    World Integration: Commands interact with the virtual environment and other robots.

    Command Parsing: Text-based instructions are parsed into commands supporting arguments.

    Robot State Management: Tracks ammo, shields, position, direction, and health.

    Asynchronous Repairing: Shields can be repaired over time in the background.

    Comprehensive Robot Actions:

        Movement (forward, back, turn)

        Combat (fire, reload)

        Status commands (look, state, orientation)

        System commands (launch, quit, repair)

Usage
Creating Commands

Use the static factory method to create commands from user input strings:

    Command command = Command.create("forward 10");

The command can then be executed on a robot:

    Response response = command.execute(robot);

Available Commands

    launch <robotType> <robotName> – Create and launch a new robot

    quit – Exit the game/session

    forward <steps> – Move robot forward

    back <steps> – Move robot backward

    turn <left|right> – Turn robot direction

    fire – Fire a shot if ammo is available

    reload – Reload ammo to maximum capacity

    look – Scan the environment

    state – Show robot status

    orientation – Display current direction

    repair – Repair shields over time

Code Structure

    Command (abstract): Base class for all commands with an execute method.

    Robot: Represents a robot with position, health, shields, ammo, and type.

    RobotType: Defines attributes for each robot type (shield strength, ammo, range).

    Response: Encapsulates the result of command execution.

    Position: Coordinates for robot location and movement checks.

    TextWorld: The game environment containing robots, obstacles, and pits.

Example

    Command cmd = Command.create("turn left");
    Response res = cmd.execute(robot);
    System.out.println(res.getResult());

## ForwardCommand

The ForwardCommand class implements the logic for moving a robot forward in the direction it is currently facing.
Description

    Moves the robot forward by a specified number of steps.

    Checks for obstacles, edges of the world, and pits during movement.

    Updates the robot’s status and returns an appropriate response indicating success, failure, or error.

Usage

Create the command by passing the number of steps as a string:

    Command forward = new ForwardCommand("5");
    Response response = forward.execute(robot);

Response outcomes:

    OK: Robot successfully moved the requested number of steps.

    FAILED: Movement blocked due to an obstacle, edge of the world, or another robot.

    DEAD: Robot fell into a pit and is destroyed.

    ERROR: Provided steps argument was invalid (non-numeric).

Example output:

    {
    "result": "OK",
    "data": {
    "message": "Done"
    },
    "state": {
    "position": [x, y],
    "direction": "NORTH",
    "Shields": 3,
    "shots": 3,
    "status": "NORMAL"
    }
}


## ↓ BackCommand

The BackCommand class moves the robot backward by a specified number of steps, opposite to the direction the robot is currently facing.
Description

    Moves the robot backwards by the given number of steps.

    Checks for obstacles, edges of the world, and pits during movement.

    Updates the robot’s status and returns an appropriate response indicating success, failure, or error.

Usage

Create a command by specifying the steps as a string:

    Command back = new BackCommand("3");
    Response response = back.execute(robot);

Response outcomes:

    OK: Robot successfully moved backward.

    FAILED: Movement blocked by an obstacle, edge, or another robot.

    DEAD: Robot fell into a pit and is destroyed.

    ERROR: Provided steps argument was invalid (non-numeric).

Example response:

    {
    "result": "OK",
    "data": {
    "message": "Done"
    },
    "state": {
    "position": [x, y],
    "direction": "NORTH",
    "Shields": 3,
    "shots": 3,
    "status": "NORMAL"
    }
    }

## 🚦 TurnCommand

The TurnCommand class rotates the robot either left or right based on the provided argument.
📝 Description

    🔄 Rotates the robot’s facing direction.

    🎯 Accepts "left" or "right" as arguments to turn the robot accordingly.

    ✅ Updates the robot’s status and returns a response indicating the outcome.

🚀 Usage

Create a command with the turn direction as an argument:

    Command turnRight = new TurnCommand("right");
    Response response = turnRight.execute(robot);

🔄 Behavior

    "right" ➡️ Turns the robot 90° clockwise.

    "left" ⬅️ Turns the robot 90° counterclockwise.

📬 Response

Returns a response with:

    result: "OK" ✅

    data: Message "Done" 💬

    state: Updated robot state after turning 🤖

## 🚀 LaunchCommand

The LaunchCommand handles the initialization and launching of a robot into the world.
📝 Overview

    Purpose: Launch a new robot by specifying its type and unique name.

    Input: Requires a robot type (e.g., "Scout", "Tank", "Sniper") and a robot name.

    Validation:

        Ensures both the robot type and name are provided.

        Checks that the robot type exists in the system.

    Response:

        Returns "OK" with a success message if valid.

        Returns "ERROR" if missing or invalid robot type or name.

🚀 Usage Example

    Command launch = new LaunchCommand("Scout", "Explorer1");
    Response response = launch.execute(null);

📦 Key Methods

    execute(Robot robot): Validates and attempts to launch the robot; returns a Response with the result.

    getRobotName(): Returns the robot’s name.

    getRobotTypeName(): Returns the robot type string.

## 🧭 CurrentDirectionCommand

The CurrentDirectionCommand reports the current facing direction of the robot.
📝 Overview

    Purpose: Retrieves and returns the robot’s current orientation (e.g., NORTH, SOUTH, EAST, WEST).

    Input: No arguments needed.

    Response: Returns a "OK" response with a message indicating the current direction.

🚀 Usage Example

    Command directionCommand = new CurrentDirectionCommand();
    Response response = directionCommand.execute(robot);
    System.out.println(response.getData().get("message"));

📦 Key Methods

    execute(Robot robot): Gets the robot’s current direction, sets status to "NORMAL", and returns it in the response.


## 🔍 LookCommand

The LookCommand lets your robot scan its surroundings in all four directions within its visibility range.
Overview

    The robot looks ⬆️ North, ➡️ East, ⬇️ South, ⬅️ West up to 10 steps away.

    It detects and reports:

        🏔️ Mountains (block vision — you can't see past them!)

        🌊 Lakes (do not block vision)

        🕳️ Bottomless pits (do not block vision)

        🤖 Other robots in view

        🚧 World edges (boundaries)

    Vision stops when hitting a mountain 🏔️ or edge 🚧.

    If nothing is seen in a direction, it reports Empty.

Details

For each object spotted, the response includes:

    Type: e.g., 🏔️ MOUNTAIN, 🌊 LAKE, 🕳️ BOTTOMLESS_PIT, 🤖 ROBOT, 🚧 EDGE, or 🔲 EMPTY

    Direction: ⬆️ NORTH, ➡️ EAST, ⬇️ SOUTH, or ⬅️ WEST

    Distance: How far it is from the robot (steps)

Usage

Execute look to get a detailed map of your surroundings, helping your robot plan its next move safely and smartly! 🎯🤖

ReloadCommand Class — Overview 🎯

Purpose:
The ReloadCommand class lets a robot reload its ammunition during gameplay. 🔄
What it Does:

    When executed, it attempts to reload the robot’s ammo. 💥

    If the robot is dead ("DEAD" status), it cannot reload, and the command returns a failure response with a message explaining this. ⚠️

    If the robot is alive, it calls the robot’s reload() method to refill ammo. 🔋

    After reloading, it sets the robot’s status back to "NORMAL". ✅

    Returns a response indicating whether the reload was successful or failed:

        Success: "OK" status with a confirmation message. 👍

        Failure: "FAILED" status with an explanation. ❌

Key Methods:

    Constructor:
    ReloadCommand() — Initializes the command named "reload". 🛠️

    execute(Robot robot):
    Runs the reload logic on the robot and returns a Response describing the result. 🎮

Example Usage:

When a robot runs the reload command and is alive, ammo is refilled and success is confirmed.
If the robot is dead, a failure message tells you reloading isn't possible. 💀



## StateCommand ⚙️
Description

The StateCommand checks the shield status of a robot and updates its condition accordingly.
Behavior

    Compares the robot's current shield strength with its maximum shield strength.

    If the current shield is less than the max, it sets the robot’s status to "DAMAGED" 🛡️.

    Returns a response containing the updated robot object.

    No explicit message or response code is provided.

Purpose

This command is useful to monitor the robot’s health and reflect damage in its status.
Example

    @Override
    public Response execute(Robot robot) {
    if (robot.getCurrentShieldStrength() < robot.getMaxShieldStrength()) {
    robot.setStatus("DAMAGED");
    }
    return new Response(null, null, robot);
    }

## VisibleObjectType Enum 🌄🚧🤖
Description

This enum defines the different types of objects that a robot can see in the world.
Enum Values

    MOUNTAIN 🏔️ — Represents a mountain obstacle.

    LAKE 🌊 — Represents a lake obstacle.

    BOTTOMLESS_PIT 🕳️ — Represents a bottomless pit.

    ROBOT 🤖 — Represents another robot.

    EDGE 🚧 — Represents the edge of the world.

    EMPTY 🌌 — Represents empty space or nothing detected.

Usage

Used primarily in visibility and sensor commands (like LookCommand) to classify what the robot detects around it.


Direction Enum 🧭
Description

Defines the four cardinal directions a robot can face or move towards:

    NORTH ⬆️

    EAST ➡️

    SOUTH ⬇️

    WEST ⬅️

Features

    Rotate Right: Turns the direction 90° clockwise.

    Rotate Left: Turns the direction 90° counterclockwise.

Example:

    NORTH.turnRight() → EAST

    WEST.turnRight() → NORTH

    EAST.turnLeft() → NORTH

Usage

Used to manage robot orientation and turning commands smoothly and cyclically.