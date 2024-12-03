import java.util.Date;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegularEmployee extends Employee
{
    private static Database database;

    public RegularEmployee(int employeeID, String username, String password, String role, String name, String surname, String phoneNo, String email, Date dateOfBirth, Date dateOfStart, Boolean newUser)
    {
        super(employeeID, username, password, role, name, surname, phoneNo, email, dateOfBirth, dateOfStart, newUser);
        database = new Database(employeeID, username, password, role, name, surname, phoneNo, email, dateOfBirth, dateOfStart, newUser);
    }

    @Override
    public void updateProfile(Scanner scanner)
    {
        InputValidation inputValidation = new InputValidation();
        int presentEmployeeID = getEmployeeID();

        do
        {
            System.out.println("ATTENTION!\n");
            System.out.println("There are some fields in your profile section that you cannot edit:");
            System.out.println("Your Employee ID is the ID assigned to you specifically by the system. You cannot change this ID.");
            System.out.println("Only employees with the Manager role can change your username.");
            System.out.println("Your role cannot be changed by you.");
            System.out.println("Only one Manager can change your name and surname in the system.");
            System.out.println("Only one Manager can change your name and surname in the system.");
            System.out.println("Your date of birth and start date are inputs provided by a Manager when registering in the system. Therefore, you cannot change this data either. ");


            System.out.println("\nThere are some input validation rules for the data you want to change:");
            System.out.println("Your password must contain at least 1 uppercase letter, 1 lowercase letter, 1 digit and 1 special character. And it cannot exceed 50 characters.");
            System.out.println("Your phone number cannot contain letters or special characters. Inputs with spaces are also not accepted. Please provide input by identifying your country code without using + character. And it cannot exceed 40 characters.");
            System.out.println("Your email input must be a valid email. Only emails ending with .com will be accepted into the system. And it cannot exceed 100 characters.");

            System.out.println("\nIF THE ABOVE RESTRICTIONS ARE NOT RESPECTED, THE SYSTEM GIVES AN ERROR!\n");


            System.out.println("\nYOUR PROFILE\n");
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
                else
                {
                    database.updateEmployeeInfo(choice, presentEmployeeID, scanner);
                }
            }
        } while(true);
    }

    public void regularEmployeeMenu(Employee employee, Scanner scanner)
    {
        InputValidation inputValidation = new InputValidation();
        Main.clearTheTerminal();

        if(getNewUser())
        {
            do
            {
                System.out.println("PASSWORD CHANGE REQUIRED");
                System.out.println("\nNew employees should set their own secure passwords.");
                System.out.println("The password must contain at least 1 uppercase letter, 1 lowercase letter, 1 digit and 1 special character.");
                System.out.println("The password must not contain spaces!.\n");

                System.out.print("Enter your new password: ");
                String newPassword = scanner.nextLine();

                if(inputValidation.passwordValidation(newPassword))
                {
                    String UPDATE_QUERY = "UPDATE firmms.employees SET password = ?, new_user = ? WHERE employee_id = ?";

                    if(database.updatePassword(UPDATE_QUERY, newPassword, getEmployeeID()))
                    {
                        setPassword(newPassword);
                        System.out.println("\nDatabase is being updated...\n");
                        Main.delay();
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
            System.out.println("Welcome, "+ employee.getName() + " " + employee.getSurname());
            System.out.println("Your Role:  "+ employee.getRole());

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
                        System.out.println("\nYOUR PROFILE\n");
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
