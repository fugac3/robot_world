package za.co.wethinkcode.robots.robot;

import za.co.wethinkcode.robots.robotTypes.RobotType;
import za.co.wethinkcode.robots.commands.Command;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Robot {
    private Direction currentDirection = Direction.NORTH;
    private final String name;
    private final TextWorld world;
    private Position position;
    private String status;
    private String lastMoveReason=null;
    private int currentAmmo; // current ammo
    private final int maxAmmo; //starting/max ammo robot has
    private final int maxShieldStrength; // starting/max shield strength robot has
    private int currentShieldStrength; //current shield strength
    private final int shootingRange; //how far robot can fire bullets
    private final RobotType type;
    private boolean isRepairing = false;
    private boolean isReloading = false;
    private final int repairTime = 10;
    private final int reloadTime = 4;
    private int robotHealth = 1;
    private final int shieldRepairAmount = 2;

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
        this.currentAmmo = maxAmmo;

        // Get the shield constraint from world config
        int worldMaxShields = world.getConfig().shieldConstraint;
        int typeMaxShields = type.getMaxShieldStrength();

        // Set maxShieldStrength to the smaller of the two values
        this.maxShieldStrength = Math.min(worldMaxShields, typeMaxShields);
        this.currentShieldStrength = this.maxShieldStrength;
        this.currentAmmo = maxAmmo;
        this.shootingRange = type.getShootingRange();
        this.typeName = type.getTypeName();
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public int getRepairTime() {
        return repairTime;
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

    public int getAmmo() {
        return currentAmmo;
    }

    public void setAmmo(int ammoShot) {
        this.currentAmmo = currentAmmo - ammoShot;
    }

    public boolean getIsRepairing() {
        return isRepairing;
    }

    public boolean getIsReloading() {
        return isReloading;
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

//    public boolean reload() {
//        currentAmmo = maxAmmo; // reset to full ammo
//        status = "RELOAD";
//        return true;
//    }

    public boolean reloading() {
        if (isReloading || currentAmmo == maxShieldStrength) {
            return false;
        }
        isReloading = true;
        new Thread(() -> {
            try {
                Thread.sleep(reloadTime * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                currentAmmo = maxAmmo;
                isReloading = false;
                System.out.println("Ammo reloaded.");
            }
        }).start();
        return true;
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
                currentShieldStrength += shieldRepairAmount;
                if(currentShieldStrength>maxShieldStrength){
                    currentShieldStrength = maxShieldStrength;
                }
                isRepairing = false;
                System.out.println("Shields repaired.");
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



        if (world.pathContainsPit(this.position, newPosition)) {
            this.setRobotHealth(0);
            System.out.println("Robot fell into a pit at " + newPosition);
            lastMoveReason = "pit";
            return false;
        }


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
