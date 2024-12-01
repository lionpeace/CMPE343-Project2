import java.util.Date;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegularEmployee extends Employee
{
    public RegularEmployee(int employeeID, String username, String password, String role, String name, String surname, String phoneNo, String email, Date dateOfBirth, Date dateOfStart, Boolean newUser)
    {
        super(employeeID, username, password, role, name, surname, phoneNo, email, dateOfBirth, dateOfStart, newUser);
    }

    @Override
    public void updateProfile(Scanner scanner)
    {
        InputValidation inputValidation = new InputValidation();
        int presentEmployeeID = getEmployeeID();

        do
        {
            System.out.println("Your Profile");
            displayProfileFromDatabase(presentEmployeeID);
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
                    updateEmployeeInfoFromChoice(choice, presentEmployeeID ,scanner);
                }
            }
        } while(true);
    }

    public void regularEmployeeMenu(Employee employee, Scanner scanner)
    {
        InputValidation inputValidation = new InputValidation();
        Main.clearTheTerminal();

        System.out.println("Welcome, "+ employee.getName() + " " + employee.getSurname());
        System.out.println("Your Role:  "+ employee.getRole());

        do
        {
            if(getNewUser())
            {
                do {
                    System.out.println("PASSWORD CHANGE REQUIRED");
                    System.out.println("\nNew employees should set their own secure passwords.");
                    System.out.println("The password must contain at least 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character.\n");

                    System.out.print("Enter your new password: ");
                    String newPassword = scanner.nextLine();

                    if(inputValidation.passwordValidation(newPassword))
                    {
                        final String DATABASE_URL = "jdbc:mysql://localhost:3306/firmms?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                        final String UPDATE_QUERY = "UPDATE firmms.employees SET password = ?, new_user = ? WHERE employee_id = ?";

                        try (Connection connection = DriverManager.getConnection(DATABASE_URL, "root", "12345");
                             PreparedStatement updateStatement = connection.prepareStatement(UPDATE_QUERY))
                        {
                            updateStatement.setString(1, newPassword);
                            updateStatement.setBoolean(2, false);
                            updateStatement.setInt(3, getEmployeeID());

                            int rowsUpdated = updateStatement.executeUpdate();

                            if(rowsUpdated > 0)
                            {
                                System.out.println("\nPassword updated successfully!\n");
                                break;
                            }
                            else
                            {
                                System.out.println("\nFailed to update password. Please try again!\n");
                            }
                        }
                        catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                    }
                } while(true);

                break;
            }

            else
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
                    int presentEmployeeID = employee.getEmployeeID();

                    switch(choice){
                        case 1:
                            Main.clearTheTerminal();
                            displayProfileFromDatabase(presentEmployeeID);
                            if(!Main.returnMainMenu(scanner))
                            {
                                break;
                            }
                            else
                            {
                                Main.clearTheTerminal();
                                break;
                            }
                        case 2:
                            Main.clearTheTerminal();
                            updateProfile(scanner);
                            if(!Main.returnMainMenu(scanner))
                            {
                                break;
                            }
                            else
                            {
                                Main.clearTheTerminal();
                                break;
                            }
                        case 3:
                            System.out.println("Logged out!\n");
                            break;
                        default:
                            System.out.println("\nInvalid input!\n");
                    }

                    break;
                }
            }
        } while(true);

    }
}
