package Hemtenta.IOhandler;




import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {

    /**
     * A method to validate binary input (1 or 0) from the user.
     * @return boolean Returns true if user inputs 1 for "Yes", false if user inputs 0 for "No".
     */
    public static boolean binaryInputValidator() {

        Scanner input = new Scanner(System.in);
        try {

            int inputValue = input.nextInt();

            switch (inputValue) {
                case 0 -> {
                    System.out.println("You've selected : 0");
                    return false;
                }
                case 1 -> {
                    System.out.println("You've selected: 1");
                    return true;
                }
                default -> {
                    System.out.println("Your input value has to be values of either 1 or 0.  ");
                    return binaryInputValidator();
                }
            }
        } catch (NumberFormatException | InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return binaryInputValidator();
        }

    }


    public static int getValidInput(int min, int max) {
        Scanner scanner = new Scanner(System.in);
        int input;

        while (true) {
            try {
                input = scanner.nextInt();

                if (input >= min && input <= max) {
                    return input; // Return the input when it's valid.
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");

                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }
}

