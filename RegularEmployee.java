import java.util.Date;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Created to represent a regular employee in the system.
 * This class extends the Employee class and provides additional functionality specific to regular employees.
 */
public class RegularEmployee extends Employee
{
    private static Database database;
    /**
     * Creates a new RegularEmployee object with the given details.
     * Initializes the associated database connection for the employee.
     *
     * @param employeeID unique ID of the employee
     * @param username employee's username
     * @param password employee's password
     * @param role employee's role in the company
     * @param name employee name
     * @param surname last name of the employee
     * @param phoneNo employee's phone number
     * @param email employee's email address
     * @param dateOfBirth employee's date of birth
     * @param dateOfStart employee start date
     * @param newUser indicates whether the employee is a new user (true/false)
     */
    public RegularEmployee(int employeeID, String username, String password, String role, String name, String surname, String phoneNo, String email, Date dateOfBirth, Date dateOfStart, Boolean newUser)
    {
        super(employeeID, username, password, role, name, surname, phoneNo, email, dateOfBirth, dateOfStart, newUser);
        database = new Database(employeeID, username, password, role, name, surname, phoneNo, email, dateOfBirth, dateOfStart, newUser);
    }
    /**
     * A method that allows a regular employee to update profile information.
     * Displays the current profile and prompts the user to select the field they want to update.
     * Performs validation of all inputs.
     *
     * @param scanner gives the Scanner object used for user input.
     */
    @Override
    public void updateProfile(Scanner scanner)
    {
        InputValidation inputValidation = new InputValidation();
        int presentEmployeeID = getEmployeeID();

        do
        {
            System.out.println("Your Profile");
            database.displayProfileFromDatabase(presentEmployeeID, getEmployeeID());
            System.out.print("Which profile element do you want to update: ");
            String choiceStr = scanner.nextLine();

            if(inputValidation.integerValidation(choiceStr))
            {
                int choice = Integer.parseInt(choiceStr);

                if(choice < 1 || choice > 11)
                {
                    System.out.println("\nInvalid choice!\n");
                    continue;
                }
                if(choice == 1)
                {
                    System.out.println("\nYou cannot edit the employee ID!\n");
                }
                else if(choice == 11)
                {
                    break;
                }
            }
        } while(true);
    }
    /**
     * Method that displays the menu and performs menu operations for normal employees.
     * Includes options to view/update the profile and logout.
     *
     * @param employee returns the current employee object
     * @param scanner gives the Scanner object used for user input.
     */
    public void regularEmployeeMenu(Employee employee, Scanner scanner)
    {
        InputValidation inputValidation = new InputValidation();
        Main.clearTheTerminal();

        System.out.println("Welcome, "+ employee.getName() + " " + employee.getSurname());
        System.out.println("Your Role:  "+ employee.getRole());

        if(getNewUser())
        {
            do
            {
                System.out.println("PASSWORD CHANGE REQUIRED");
                System.out.println("\nNew employees should set their own secure passwords.");
                System.out.println("The password must contain at least 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character.\n");

                System.out.print("Enter your new password: ");
                String newPassword = scanner.nextLine();

                if(inputValidation.passwordValidation(newPassword))
                {
                    String UPDATE_QUERY = "UPDATE firmms.employees SET password = ?, new_user = ? WHERE employee_id = ?";

                    if(database.updatePassword(UPDATE_QUERY, newPassword, getEmployeeID()))
                    {
                        setPassword(newPassword);
                        System.out.println("\nPassword updated successfully!\n");
                        break;
                    }
                    else
                    {
                        System.out.println("\nFailed to update password. Please try again!\n");
                    }
                }
            } while(true);
        }

        do
        {
            System.out.println("From this menu you can access some operations.\n");

            System.out.println("1 - Display Your Profile");
            System.out.println("2 - Update Your Profile");
            System.out.println("3 - Logout");

            System.out.print("Enter your choice: ");
            String regularEmployeeMenuChoice = scanner.nextLine();

            if(inputValidation.integerValidation(regularEmployeeMenuChoice))
            {
                int choice = Integer.parseInt(regularEmployeeMenuChoice);
                int presentEmployeeID = getEmployeeID();

                switch (choice)
                {
                    case 1:
                        Main.clearTheTerminal();
                        database.displayProfileFromDatabase(presentEmployeeID, getEmployeeID());
                        if (!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            Main.clearTheTerminal();
                            continue;
                        }
                    case 2:
                        Main.clearTheTerminal();
                        updateProfile(scanner);
                        if (!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            Main.clearTheTerminal();
                            continue;
                        }
                    case 3:
                        System.out.println("\nLogged out!");
                        break;
                    default:
                        System.out.println("\nInvalid input!\n");
                        continue;
                }
                break;
            }
        } while (true);
    }
}
