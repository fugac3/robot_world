package za.co.wethinkcode.robots.robot;

import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.commands.Command;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.ArrayList;
import java.util.List;

public class Robot {
    private Direction currentDirection = Direction.NORTH;
    private final String name;
    private final TextWorld world;
    private Position position;
    private String status;
    private String lastMoveReason;
    private int ammo; // current ammo
    private final int maxAmmo; //starting/max ammo robot has
    private int currentShieldStrength; //current shield strength
    private final int maxShieldStrength; //max shield based off type of robot
    private final int shootingRange; //how far robot can fire bullets
    private final RobotType type;
    private boolean isRepairing = false;
    private final int repairTime = 10;
    private int robotHealth = 1;

    private final List<String> commands;
    private final String typeName;

    public Robot(String name,TextWorld world,Position position, RobotType type) {
        this.robotHealth = getRobotHealth();
        this.name = name;
        this.position = position;
        this.commands = new ArrayList<>();
        this.world = world;
        this.type = type;
        this.status = "NORMAL"; //initialized status
        this.maxAmmo = type.getMaxShots();
        this.ammo = maxAmmo;
        this.maxShieldStrength = type.getMaxShieldStrength();
        this.currentShieldStrength = maxShieldStrength;
        this.shootingRange = type.getShootingRange();
        this.typeName = type.getTypeName();
    }

    public String getTypeName() {
        return this.typeName;
    }

    public int getRobotHealth() {
        return this.robotHealth;
    }

    public void setRobotHealth(int robotHealth) {
        this.robotHealth = robotHealth;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean reload() {
        ammo = maxAmmo; // reset to full ammo
        status = "RELOAD";
        return true;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammoShot) {
        this.ammo = ammo - ammoShot;
    }

    public boolean getIsRepairing() {
        return isRepairing;
    }

    public int getCurrentShieldStrength() {
        return currentShieldStrength;
    }

    public void applyDamage(int damage) {
        this.currentShieldStrength -= damage;
        if(this.currentShieldStrength < 0) {
            this.currentShieldStrength = 0;
        // If shields are gone, subtract from health{
            this.robotHealth -= 1;
            // Check if robot is dead
            if (this.robotHealth <= 0) {
                this.status = "DEAD";
                robotDeath(); // handle removal from world
            }
        }
    }

    public void robotDeath() {
        if (world != null) {
            world.removeRobot(this);
        }
    }

    public boolean repairing() {
        if (isRepairing || currentShieldStrength == maxShieldStrength) {
            return false;
        }
        isRepairing = true;
        new Thread(() -> {
            try {
                Thread.sleep(repairTime * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                currentShieldStrength = maxShieldStrength;
                isRepairing = false;
                System.out.println("Shields repaired to maximum strength.");
            }
        }).start();
        return true;
    }

    public boolean updatePosition(int nrSteps){

        int newY = this.position.getY();
        int newX = this.position.getX();

        switch (currentDirection){
            case NORTH:
                newY += nrSteps;
                break;
            case SOUTH:
                newY -= nrSteps;
                break;
            case EAST:
                newX += nrSteps;
                break;
            case WEST:
                newX -= nrSteps;
                break;
            default:
                status = "ERROR" ;
        }

        Position newPosition = new Position(newX,  newY);

        if (world.blocksPath(this.position, newPosition)) {
            lastMoveReason = "Obstructed";
            return false;
        }

        if (!newPosition.isIn(TextWorld.TOP_LEFT,TextWorld.BOTTOM_RIGHT)){
            lastMoveReason = "Edge of world";
            return false;
        }

        for (Robot robot : TextWorld.getInstance().getAllRobots()){ //making sure robots don't share position
            if (robot.getPosition().equals(newPosition)){
                lastMoveReason = "Robot in the way";
                return false;
            }
        }

        this.position = newPosition;
        return true;
    }

    public String getLastMoveReason() {
        return lastMoveReason;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    public TextWorld getWorld() {
        return this.world;
    }

    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    public Response handleCommand(Command command) {
        Response response = command.execute(this);
        addCommand(command.getName() + " " + command.getArgument().trim());
        return response;
    }

    public void addCommand(String command) {
        commands.add(command);
    }

    public List<String> getCommands() {
        return commands;
    }

    public void turnRight() {
        this.currentDirection = this.currentDirection.turnRight();
    }

    public void turnLeft() {
        this.currentDirection = this.currentDirection.turnLeft();
    }

    public String getName() {
        return name;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public int getMaxShieldStrength() {
        return maxShieldStrength;
    }

    public int getShootingRange() {
        return shootingRange;
    }

    @Override
    public String toString() {
        return "[" + this.position.getX() + "," + this.position.getY() + "] "
                + this.name + " (" + this.type.getTypeName() + ")> "  + this.status;
    }
}
