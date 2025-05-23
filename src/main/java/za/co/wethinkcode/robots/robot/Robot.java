package za.co.wethinkcode.robots.robot;

import za.co.wethinkcode.robots.commands.Command;
import za.co.wethinkcode.robots.commands.Direction;
import za.co.wethinkcode.robots.server.Response;
import za.co.wethinkcode.robots.world.Bullet;
import za.co.wethinkcode.robots.world.TextWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Robot {
    public static final Position CENTRE = new Position(0,0);

    //    private Position position;
    private Direction currentDirection = Direction.NORTH;

    private final String name;
    private final TextWorld world;
    private Position position;
    private String status;
    private String lastMoveReason ;
    private int ammo = 5; // starting ammo
    private int shotsFired = 0;
    private static final int bulletMaxDistance = 3;
    private List<Bullet> bullets = new ArrayList<>();
    private int defence = 2;
    private boolean isRepairing;
    private int maxShieldStrength = 100; // Or some other meaningful value
    private int currentShieldStrength = 100; // Or some other starting value
    private int repairTime = 5;  // Set a default repair time (in seconds)




    private final List<String> commands;

    public Robot(String name,TextWorld world,Position position) {
        this.name = name;
        this.position = position;
        this.commands = new ArrayList<>();
        this.world = world;
        this.maxShieldStrength = maxShieldStrength;
        this.currentShieldStrength = currentShieldStrength;
        this.isRepairing = isRepairing;
//        this.position = new Position(0, 0); // start at center
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }



    public Response fire() {
        if (ammo <= 0) {
            status = "NORMAL";
            return new Response("ERROR", Map.of("message", "No ammo"), this);
        }

        ammo--; // consuming a bullet
        shotsFired++;

        // Create a new bullet travelling in the current direction
        Bullet bullet = new Bullet(position, currentDirection, bulletMaxDistance, this);
        boolean hit = world.addBulletAndCheckHit(bullet);

        status = "NORMAL";
        String message = hit ? "Hit" : "Miss";

        return new Response("OK", Map.of("message", message, "shotsFired", shotsFired), this);
    }



    public void updateBullets() {
        List<Bullet> activeBullets = new ArrayList<>();

        for (Bullet bullet : bullets) {
            if (bullet.move()) {
                activeBullets.add(bullet); // Keep only moving bullets
            }
        }
        bullets = activeBullets; // Remove bullets that have stopped moving
    }

    public int getShotsFired() {
        return shotsFired;
    }

    public boolean reload() {
        ammo = 5; // reset to full ammo
        status = "RELOAD";
        return true;
    }

    public int getAmmo() {
        return ammo;
    }

    public int getDefence(){
        return defence;
    }

    public boolean getIsRepairing(){
        return isRepairing;
    }

    public int getMaxShieldStrength(){
        return maxShieldStrength;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public int getCurrentShieldStrength() {
        return currentShieldStrength;
    }


    public boolean repairing() {
        if (isRepairing || currentShieldStrength == maxShieldStrength) {
            return false; // Already repairing or fully repaired
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

        return true; // Repair started
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



    @Override
    public String toString() {
        return "[" + this.position.getX() + "," + this.position.getY() + "] "
                + this.name + "> " + this.status;
    }

    public String getName() {
        return name;
    }
}
