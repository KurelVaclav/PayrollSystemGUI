package utils;

/**
 *
 * @author Václav Kurel
 */
public class IllegalInputFromUser extends Exception {

    String message;

    public IllegalInputFromUser(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "IllegalInputFromUser{" + "message = " + message + '}';
    }

}
