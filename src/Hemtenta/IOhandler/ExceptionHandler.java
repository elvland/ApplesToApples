package Hemtenta.IOhandler;

import java.io.IOException;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
/**
* Handling some of the occuring exceptions during runtime
*/
public class ExceptionHandler {

    public static void handler(Exception error) {
        if (error instanceof SocketException) {
            System.err.println("Server error. Lobby closed");
        } else if (error instanceof IOException) {

            if (error instanceof ClosedChannelException) {
                System.err.println("Game is over. Game lobby closed:");
            } else {
                System.err.println("Error reading data: " + error.getMessage());
            }
        } else if (error instanceof NumberFormatException) {
            System.out.println(error);
        } else if (error instanceof  NullPointerException){
            System.out.println("Error. Lobby closed");
        }else {
            // Handle other exceptions
            System.err.println("Lobby closed. An error occurred: " + error.getMessage());
        }
    }
}
