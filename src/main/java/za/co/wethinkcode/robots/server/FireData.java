package za.co.wethinkcode.robots.server;

/**
 * Represents data related to a firing event, including a message and the number of shots fired.
 */
public class FireData {
    private String message; // Messsage describing the firing event or its result.
    private int shotsFired; // The number of shots fired in the event.

    /**
     * Constructs a new FireData object with the specified message and shots fired count.
     *
     * @param message a description or result of the firing event
     * @param shotsFired the number of shots fired
     */
    public FireData(String message, int shotsFired) {
        this.message = message;
        this.shotsFired = shotsFired;
    }

    /**
     * Returns the message describing the firing event.
     *
     * @return the message string
     */
    public String getMessage() { return message; }

    /**
     * Returns the number of shots fired.
     *
     * @return the shots fired count
     */
    public int getShotsFired() { return shotsFired; }
}
