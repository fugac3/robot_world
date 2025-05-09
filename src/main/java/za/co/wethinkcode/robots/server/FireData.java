package za.co.wethinkcode.robots.server;

public class FireData {
    private String message;
    private int shotsFired;

    public FireData(String message, int shotsFired) {
        this.message = message;
        this.shotsFired = shotsFired;
    }

    public String getMessage() { return message; }
    public int getShotsFired() { return shotsFired; }
}
