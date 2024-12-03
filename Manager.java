import java.sql.*;
import java.util.Scanner;
import java.util.List;

public class Manager extends Employee
{
    private static Database database;

    // Constructor Method
    public Manager(int employeeID, String username, String password, String role, String name, String surname, String phoneNo, String email, Date dateOfBirth, Date dateOfStart, Boolean newUser)
    {
        super(employeeID, username, password, role, name, surname, phoneNo, email, dateOfBirth, dateOfStart, newUser);
        database = new Database(employeeID, username, password, role, name, surname, phoneNo, email, dateOfBirth, dateOfStart, newUser);
    }

    // Update Your Profile Method
    @Override
    public void updateProfile(Scanner scanner) // Update MANAGER profile
    {
        InputValidation inputValidation = new InputValidation();

        int presentEmployeeID = getEmployeeID();

        do
        {
            System.out.println("ATTENTION!\n");
            System.out.println("There are some fields in your profile section that you cannot edit:");
            System.out.println("Your Employee ID is the ID assigned to you specifically by the system. You cannot change this ID.");
            System.out.println("Your Manager role is a special role assigned to you. It is not possible for you to change this role. Only another manager can change your role.");

            System.out.println("\nThere are some input validation rules for the data you want to change:");
            System.out.println("Your username cannot contain any space. And it cannot exceed 100 characters.");
            System.out.println("Your password must contain at least 1 uppercase letter, 1 lowercase letter, 1 digit and 1 special character. And it cannot exceed 50 characters.");
            System.out.println("Your name cannot contain any numbers or special characters. And it cannot exceed 150 characters.");
            System.out.println("Your surname cannot contain any numbers or special characters. And it cannot exceed 150 characters.");
            System.out.println("Your phone number cannot contain letters or special characters. Inputs with spaces are also not accepted. Please provide input by identifying your country code without using + character. And it cannot exceed 40 characters.");
            System.out.println("Your email input must be a valid email. Only emails ending with .com will be accepted into the system. And it cannot exceed 100 characters.");
            System.out.println("Your date of birth must be in YYYY-MM-DD format. Entries in other formats are not accepted.");
            System.out.println("Your date of start must be in YYYY-MM-DD format. Entries in other formats are not accepted.");

            System.out.println("\nIF THE ABOVE RESTRICTIONS ARE NOT RESPECTED, THE SYSTEM GIVES AN ERROR!\n");

            System.out.println("\nYOUR PROFILE\n");
            database.displayProfileFromDatabase(presentEmployeeID, getEmployeeID());
            System.out.println("\nIf you want to quit this operation please enter 11.\n");

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
                    database.updateEmployeeInfo(choice, getEmployeeID(), scanner);
                }
            }
        } while(true);
    }

    /*
    // Display All Employees Method
    public void displayAllEmployees()
    {
        // Display all employees
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/firmms?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String SELECT_QUERY = "SELECT e.employee_id, e.username, e.name, e.surname, e.phone_no, e.dateofbirth, e.dateofstart, e.email, r.role_name FROM firmms.employees e JOIN firmms.roles r ON e.role = r.role_id";

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, "root", "12345");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);

            // get ResultSet's meta data
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            System.out.print("List of Employees\n\n");

            // display the names of the columns in the ResultSet

            System.out.print("Employee ID\tUsername\tName\tSurname\tPhone Number\tDate of Birth\tDate of Start\tE-mail\tRole\n\n");

            // display query results
            while (resultSet.next())
            {
                for (int i = 1; i <= numberOfColumns; i++)
                {
                    System.out.printf("%-8s\t", resultSet.getObject(i));
                }
                System.out.println();
            }

            connection.close();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    */

    public void displayAllEmployees()
    {
        // Display all employees
        String SELECT_QUERY = "SELECT e.employee_id, e.username, e.name, e.surname, e.phone_no, e.dateofbirth, e.dateofstart, e.email, r.role_name FROM firmms.employees e JOIN firmms.roles r ON e.role = r.role_id";

        List<String[]> employees = database.returnStrArrayForDisplay(SELECT_QUERY);

        System.out.print("LIST OF EMPLOYEES\n\n");

        String RESET = "\u001B[0m";
        String GREEN = "\u001B[32m";

        String[] columns = {"Employee ID: ", "Username: ", "Name: ", "Surname: ", "Phone Number: ", "Date of Birth: ", "Date of Start: ", "Email: ", "Role: "};
        int i = 0;

        for(String[] employee : employees)
        {
            for(String emp : employee)
            {
                if(emp == null)
                {
                    if(i == 8)
                    {
                        System.out.printf(GREEN + columns[i] + "- ", emp + RESET);
                    }
                    else
                    {
                        System.out.printf(GREEN + columns[i] + "- | ", emp + RESET);
                    }
                }
                else
                {
                    if(i == 8)
                    {
                        System.out.printf(GREEN + columns[i] + "%s ", emp + RESET);

                    }
                    else
                    {
                        System.out.printf(GREEN + columns[i] + "%s | ", emp + RESET);
                    }
                }
                i++;

                if(i == 9)
                {
                    i = 0;
                }
            }
            System.out.println("\n");
        }
    }

    // Auxiliary Method for Update Employee Profile
    public static boolean isEmployeeIdValid(int employeeID)
    {
        String CHECK_QUERY = "SELECT role FROM employees WHERE employee_id = ?";

        if(database.selectRoleFromEmployeeID(CHECK_QUERY, employeeID))
        {
            return true;
        }
        return false;
    }

    // Update Employee Profile Method
    public void updateEmployeeProfile(Scanner scanner)
    {
        InputValidation inputValidation = new InputValidation();
        // Update non-profile fields (name, surname, etc.)

        System.out.println("ATTENTION!\n");
        System.out.println("The employee's data that you can edit is restricted:");
        System.out.println("You cannot see/edit the employee's password.");
        System.out.println("Only the employee can update his/her email. You are not authorized to edit it.");
        System.out.println("You can't edit your employee phone number. Only the employee can make this edit.");
        System.out.println("If you select yourself from the Employee list, this will be the same operation as updating your own profile.");


        System.out.println("\nThere are some input validation rules for the data you want to change:");
        System.out.println("The username cannot contain any space. And it cannot exceed 100 characters.");
        System.out.println("The password must contain at least 1 uppercase letter, 1 lowercase letter, 1 digit and 1 special character. And it cannot exceed 50 characters.");
        System.out.println("The name cannot contain any numbers or special characters. And it cannot exceed 150 characters.");
        System.out.println("The surname cannot contain any numbers or special characters. And it cannot exceed 150 characters.");
        System.out.println("The phone number cannot contain letters or special characters. Inputs with spaces are also not accepted. Please provide input by identifying your country code without using + character. And it cannot exceed 40 characters.");
        System.out.println("The email input must be a valid email. Only emails ending with .com will be accepted into the system. And it cannot exceed 100 characters.");
        System.out.println("The date of birth must be in YYYY-MM-DD format. Entries in other formats are not accepted.");
        System.out.println("The date of start must be in YYYY-MM-DD format. Entries in other formats are not accepted.");

        System.out.println("\nIF THE ABOVE RESTRICTIONS ARE NOT RESPECTED, THE SYSTEM GIVES AN ERROR!\n");

        do
        {
            displayAllEmployees();
            System.out.print("Enter Employee ID that you want to update: ");
            String employeeChoiceStr = scanner.nextLine();

            if(inputValidation.integerValidation(employeeChoiceStr))
            {
                int employeeChoice = Integer.parseInt(employeeChoiceStr);

                if(isEmployeeIdValid(employeeChoice))
                {
                    Main.clearTheTerminal();

                    do
                    {
                        System.out.println("\nTHE EMPLOYEE PROFILE\n");
                        database.displayProfileFromDatabase(employeeChoice, getEmployeeID());
                        System.out.println("\nIf you want to quit this operation please enter 11.\n");
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
                                database.updateEmployeeInfo(choice, employeeChoice ,scanner);
                            }
                        }
                    } while (true);

                    break;
                }
            }
        } while(true);
    }

    // Display All Roles Method
    public static void displayAllRoles()
    {
        String SELECT_QUERY = "SELECT * FROM firmms.roles";
        List<String[]> roles = database.returnStrArrayForDisplay(SELECT_QUERY);

        System.out.print("LIST OF ROLES\n\n");

        System.out.print("Role ID\t\tRole Name\n\n");

        for(String[] role : roles)
        {
            for(String r: role)
            {
                System.out.printf("%-8s\t", r);
            }
            System.out.println();
        }
    }

    // Add New Role Method
    public void addNewRole(Scanner scanner)
    {
        InputValidation inputValidation = new InputValidation();

        while (true)
        {
            System.out.println("ATTENTION!\n");
            System.out.println("As a Manager, you can add a new role to the system.");

            System.out.println("\nThere are some input validation rules for adding a new role:");
            System.out.println("The new role cannot contain any numbers or special characters. And it cannot exceed 150 characters.");
            System.out.println("If there is a role in the system in the role you are trying to create, your request will be invalidated.");

            System.out.println("\nIF THE ABOVE RESTRICTIONS ARE NOT RESPECTED, THE SYSTEM GIVES AN ERROR!\n");

            System.out.print("Enter the new role name: ");
            String newRoleName = scanner.nextLine();

            // Validate role name
            if (inputValidation.noNumberValidation(newRoleName))
            {
                if (newRoleName.length() > 150)
                {
                    System.out.println("Role name cannot be longer than 150 characters!");
                    continue;
                }

                if (database.insertRole(newRoleName))
                {
                    System.out.println("\nDatabase is being updated...\n");
                    Main.delay();
                    displayAllRoles();
                    System.out.println("\nNew role '" + newRoleName + "' added to the system successfully!\n");
                    break;
                }
            }
        }
    }

    // Delete Role Method
    public void deleteRole(Scanner scanner)
    {
        do
        {
            System.out.println("ATTENTION!\n");
            System.out.println("As a Manager, you can delete a role from the system.");
            System.out.println("But there are some things to be aware of:");
            System.out.println("It is not possible to delete the Manager role. The Manager role is a special role created for system control.");
            System.out.println("If you want to delete a role, you must first delete the user with that role or change the role of that user. ");
            System.out.println("You will delete roles with the IDs of the roles in the system.");

            System.out.println("\nIF YOU ENTER A ROLE THAT IS NOT IN THE SYSTEM, YOU WILL GET AN ERROR!\n");

            displayAllRoles();

            System.out.print("\nEnter the ID of the role you want to delete: ");
            String roleIDStr = scanner.nextLine();
            InputValidation inputValidation = new InputValidation();

            if (inputValidation.integerValidation(roleIDStr))
            {
                int roleID = Integer.parseInt(roleIDStr);

                if(database.deleteRole(roleID))
                {
                    System.out.println("\nDatabase is being updated...\n");
                    Main.delay();
                    displayAllRoles();
                    System.out.println("\nRole ID " + roleID + " deleted successfully!\n");
                    break;
                }
            }
            else
            {
                System.out.println("Invalid role ID input!\n");
            }
        } while (true);

    }

    // Auxiliary Method for Hire Employee
    public static boolean isRoleIdValid(int roleId)
    {
        String CHECK_QUERY = "SELECT COUNT(*) FROM roles WHERE role_id = ?";

        if(database.selectCountFromRoleID(CHECK_QUERY, roleId))
        {
            return true;
        }
        return false;
    }

    // Hire Employee Method
    public void hireEmployee(Scanner scanner)
    {
        InputValidation inputValidation = new InputValidation();

        do
        {
            String username;
            String password = "khas1234";
            String name;
            String surname;
            Date dob;
            Date dos;
            int role;

            System.out.println("ATTENTION!\n");
            System.out.println("You can hire a new employee as a manager.");
            System.out.println("The system asks you to enter the username, first name, last name, date of birth, start date and role of the new employee.");
            System.out.println("The new employee's password is automatically set equal to the default password by the system.");
            System.out.println("Employee's information such as password, email and phone number cannot be entered by the manager.");
            System.out.println("When the employee logs into the system, he/she will enter this information into the system if he/she wishes.");

            System.out.println("\nThere are some input validation rules for the data you want to add:");
            System.out.println("The username cannot contain any space. And it cannot exceed 100 characters.");
            System.out.println("The name cannot contain any numbers or special characters. And it cannot exceed 150 characters.");
            System.out.println("The surname cannot contain any numbers or special characters. And it cannot exceed 150 characters.");
            System.out.println("The date of birth must be in YYYY-MM-DD format. Entries in other formats are not accepted.");
            System.out.println("The date of start must be in YYYY-MM-DD format. Entries in other formats are not accepted.");

            System.out.println("\nIF THE ABOVE RESTRICTIONS ARE NOT RESPECTED, THE SYSTEM GIVES AN ERROR!\n");


            do
            {
                System.out.print("Enter the username of the employee: ");
                username = scanner.nextLine();

                // Validate role name
                if (inputValidation.defaultInputValidation(username))
                {
                    break;
                }
            } while (true);

            do
            {
                System.out.print("Enter the name of the employee: ");
                name = scanner.nextLine();

                if (inputValidation.noNumberValidation(name))
                {
                    break;
                }
            } while (true);

            do
            {
                System.out.print("Enter the surname of the employee: ");
                surname = scanner.nextLine();

                if (inputValidation.noNumberValidation(surname))
                {
                    break;
                }
            } while (true);

            do
            {
                System.out.print("Enter the date of birth of the employee (YYYY-MM-DD): ");
                String dateOfBirth = scanner.nextLine();

                if (inputValidation.dateValidation(dateOfBirth))
                {
                    dob = Date.valueOf(dateOfBirth);
                    break;
                }
            } while (true);


            do
            {
                System.out.print("Enter the date of start of the employee: ");
                String dateOfStart = scanner.nextLine();

                if (inputValidation.dateValidation(dateOfStart))
                {
                    dos = Date.valueOf(dateOfStart);
                    break;
                }
            } while (true);

            do
            {
                displayAllRoles();

                System.out.print("Enter the role of the employee: ");
                String roleStr = scanner.nextLine();

                if (inputValidation.integerValidation(roleStr))
                {
                    role = Integer.parseInt(roleStr);

                    if(isRoleIdValid(role))
                    {
                        break;
                    }
                    else
                    {
                        System.out.println("There is no such role ID in the system!");
                    }
                }
            } while (true);

            if (database.insertEmployee(username, password, name, surname, dob, dos, role))
            {
                System.out.println("\nDatabase is being updated...\n");
                Main.delay();
                displayAllEmployees();
                System.out.println("\nThe new employee added to system successfully!\n");
                break;
            }
            else
            {
                System.out.println("Failed to add the new employee. Please try again.");
            }
        } while(true);
    }

    // Fire Employee Method
    public void fireEmployee(Scanner scanner)
    {
        do
        {
            System.out.println("ATTENTION!\n");
            System.out.println("You can fire an employee as a manager.");
            System.out.println("The system asks you which employee with which ID you want to delete.");
            System.out.println("Enter the ID of the user you want to delete and the user will be deleted from the system.");
            System.out.println("Authorizes system managers to delete another manager. But be careful when deleting another manager!");

            System.out.println("\nIF YOU ENTER A EMPLOYEE THAT IS NOT IN THE SYSTEM, YOU WILL GET AN ERROR!\n");

            displayAllEmployees();

            System.out.print("\nEnter the ID of the employee you want to delete: ");
            String employeeIDStr = scanner.nextLine();
            InputValidation inputValidation = new InputValidation();

            if (inputValidation.integerValidation(employeeIDStr))
            {
                int employeeID = Integer.parseInt(employeeIDStr);

                if (database.deleteEmployee(employeeID))
                {
                    System.out.println("\nDatabase is being updated...\n");
                    Main.delay();
                    displayAllEmployees();
                    System.out.println("\nEmployee with " + employeeID + " employee ID deleted successfully!");
                    break;
                }
            }
        } while (true);
    }

    // Run Sorting Algorithm Method
    public void runSortingAlgorithms()
    {
        Main.clearTheTerminal();
        // Implement Radix Sort, Shell Sort, Heap Sort, and Insertion Sort
        System.out.println("Run Sorting Algorithms");
    }

    // Manager Menu Method
    public void managerMenu(Employee employee, Scanner scanner)
    {
        InputValidation inputValidation = new InputValidation();

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
            System.out.println("Your Role:  "+ employee.getRole()+ "\n");

            System.out.println("FROM THIS MENU YOU CAN ACCESS SOME MANAGER OPERATIONS.");
            System.out.println("How the operations work is explained in the operation menus.\n");

            System.out.println("1 - Display Your Profile");
            System.out.println("2 - Update Your Profile");
            System.out.println("3 - Display All Employees");
            System.out.println("4 - Update Employee");
            System.out.println("5 - Display All Roles");
            System.out.println("6 - Add a new Role");
            System.out.println("7 - Delete a Role");
            System.out.println("8 - Hire Employee");
            System.out.println("9 - Fire Employee");
            System.out.println("10 - Algorithms");
            System.out.println("11 - Logout");

            System.out.print("\nEnter your choice: ");
            String managerMenuChoice = scanner.nextLine();

            if(inputValidation.integerValidation(managerMenuChoice))
            {
                int choice = Integer.parseInt(managerMenuChoice);

                switch (choice) {
                    case 1:
                        Main.clearTheTerminal();
                        System.out.println("\nYOUR PROFILE\n");
                        database.displayProfileFromDatabase(getEmployeeID(), getEmployeeID());
                        if(!Main.returnMainMenu(scanner))
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
                        if(!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            Main.clearTheTerminal();
                            continue;
                        }
                    case 3:
                        Main.clearTheTerminal();
                        displayAllEmployees();
                        if(!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            Main.clearTheTerminal();
                            continue;
                        }
                    case 4:
                        Main.clearTheTerminal();
                        updateEmployeeProfile(scanner);
                        if(!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            Main.clearTheTerminal();
                            continue;
                        }
                    case 5:
                        Main.clearTheTerminal();
                        displayAllRoles();
                        if(!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            Main.clearTheTerminal();
                            continue;
                        }
                    case 6:
                        Main.clearTheTerminal();
                        addNewRole(scanner);
                        if(!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            Main.clearTheTerminal();
                            continue;
                        }
                    case 7:
                        Main.clearTheTerminal();
                        deleteRole(scanner);
                        if(!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            Main.clearTheTerminal();
                            continue;
                        }
                    case 8:
                        Main.clearTheTerminal();
                        hireEmployee(scanner);
                        if(!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            Main.clearTheTerminal();
                            continue;
                        }
                    case 9:
                        Main.clearTheTerminal();
                        fireEmployee(scanner);
                        if(!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            Main.clearTheTerminal();
                            continue;
                        }
                    case 10:
                        Main.clearTheTerminal();
                        runSortingAlgorithms();
                        if(!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            Main.clearTheTerminal();
                            continue;
                        }
                    case 11:
                        System.out.println("\nLogged out.\n");
                        break;
                    default:
                        System.out.println("\nInvalid choice!\n");
                        continue;
                }
                break;
            }
        } while (true);
    }
}