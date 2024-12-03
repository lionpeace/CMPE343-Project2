// C:\Users\baris\OneDrive\Masaüstü\CMPE343 Project2\Group06\src
// javac Main.java
// java -cp ".;mysql-connector-j-9.1.0.jar;src" Main

import java.io.*;
import java.util.*;

public class Main {

    public static void ColorfulASCIIArt()
    {
        String filePath = "C:\\Users\\baris\\OneDrive\\Masaüstü\\CMPE343 Project2\\Group06\\src\\ascii-art.txt";

        List<String> asciiArtLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                asciiArtLines.add(line);
            }
        }
        catch (IOException e)
        {
            System.out.println("An error occurred when reading the file: " + e.getMessage());
            return;
        }

        for (String line : asciiArtLines) {
            System.out.println(line);
        }
    }

    /**
     * Pauses the execution of the current thread for a specified duration.
     * This method puts the current thread to sleep for 2000 milliseconds (2 seconds).
     * If the thread is interrupted during sleep, it catches the InterruptedException
     * and prints the stack trace.
     */

    public static void delay()
    {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the terminal screen based on the operating system.
     * This method checks the operating system and uses the appropriate command
     * to clear the terminal screen. For Windows, it uses "cls", and for other
     * operating systems (assumed to be Unix-like), it uses "clear". If an
     * exception occurs during this process, the stack trace is printed.
     */

    public static void clearTheTerminal()
    {
        try
        {
            String operatingSystem = System.getProperty("os.name").toLowerCase();

            if (operatingSystem.contains("win"))
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else
            {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Prompts the user to decide whether to return to the main menu or not.
     * Continuously asks the user for input until a valid response ('Y' or 'N') is provided.
     * If the user selects 'Y', the method calls the welcomeToApp() method to return to the main menu
     * and returns true. If the user selects 'N', the method returns false. For any other input,
     * an error message is displayed and the user is prompted again.
     * @param scanner scanner variable from main to receive user input
     * @return boolean value indicating whether the user wants to return to the main menu
     */

    public static boolean returnMainMenu(Scanner scanner)
    {
        InputValidation inputValidation = new InputValidation();

        do
        {
            System.out.print("\nDo you want to return to the main menu (Y-N): ");
            String mainMenuStr = scanner.nextLine();

            if(inputValidation.charValidation(mainMenuStr))
            {
                char mainMenuSelection = mainMenuStr.charAt(0);

                switch(mainMenuSelection)
                {
                    case 'Y':
                    case 'y':
                        return true;
                    case 'N':
                    case 'n':
                        return false;
                    default:
                        System.out.println("\nInvalid input. Please enter a valid character (Y or N).\n");
                }
            }
        } while(true);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        InputValidation inputValidation = new InputValidation();

        Employee employee;
        String username = "";
        String password = "";

        ColorfulASCIIArt();

        System.out.println("\nPLEASE ENTER YOUR USERNAME AND PASSWORD TO ACCESS THE SYSTEM.\n");

        do
        {
            do {
                System.out.print("Username: ");
                username = scanner.nextLine();

            } while (!inputValidation.defaultInputValidation(username));

            do {
                System.out.print("Password: ");
                password = scanner.nextLine();

            } while (!inputValidation.defaultInputValidation(password));

            employee = Authentication.login(username, password);

            if(employee != null)
            {
                System.out.println("\nYou logged in successfully!\n");
                System.out.println("You are redirected to menu...");
                delay();
                clearTheTerminal();

                if(Objects.equals(employee.getRole(), "Manager"))
                {
                    if (employee instanceof Manager manager) // We check if the Employee object is a Manager
                    {
                        manager.managerMenu(employee, scanner);
                        break;
                    }
                    else
                    {
                        System.out.println("\nEmployee is not a Manager.\n");
                    }
                }
                else
                {
                    if(employee instanceof RegularEmployee regularEmployee)
                    {
                        regularEmployee.regularEmployeeMenu(employee, scanner);
                        break;
                    }
                    else
                    {
                        System.out.println("\nEmployee is not a Regular Employee.\n");
                    }
                }

                break;
            }

            else
            {
                System.out.println("\nThe username or password is incorrect!\n");
            }

        } while(employee == null);

        scanner.close();
    }
}
