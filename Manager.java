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

        List<String[]> employees = database.executeSelectQuery(SELECT_QUERY);

        System.out.print("List of Employees\n\n");
        // display the names of the columns in the ResultSet
        System.out.print("Employee ID\tUsername\tName\tSurname\tPhone Number\tDate of Birth\tDate of Start\tE-mail\tRole\n\n");

        for(String[] employee : employees)
        {
            for(String emp : employee)
            {
                System.out.printf("%-8s\t", emp);
            }
            System.out.println();
        }
    }

    // Auxiliary Method for Update Employee Profile
    public static boolean isEmployeeIdValid(int employeeID)
    {
        String CHECK_QUERY = "SELECT role FROM employees WHERE employee_id = ?";

        if(database.checkQueryForEmployeeID(CHECK_QUERY, employeeID))
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
                    do
                    {
                        database.displayProfileFromDatabase(employeeChoice, getEmployeeID());
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
        // Display all roles
        // Display all employees
        String SELECT_QUERY = "SELECT * FROM firmms.roles";
        List<String[]> roles = database.executeSelectQuery(SELECT_QUERY);

        System.out.print("List of Roles\n\n");

        // display the names of the columns in the ResultSet

        System.out.print("Role ID\tRole Name\n\n");

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

                if (database.insertQueryForRoleName(newRoleName))
                {
                    displayAllRoles();
                    System.out.println("\nNew role '" + newRoleName + "' added to the system successfully!\n");
                    break;
                }
                else
                {
                    System.out.println("Failed to add the new role. Please try again.");
                }
            }
        }
    }

    // Delete Role Method
    public void deleteRole(Scanner scanner)
    {
        displayAllRoles();

        do
        {
            System.out.print("Enter the ID of the role you want to delete: ");
            String roleIDStr = scanner.nextLine();
            InputValidation inputValidation = new InputValidation();

            if (inputValidation.integerValidation(roleIDStr))
            {
                int roleID = Integer.parseInt(roleIDStr);

                if(database.deleteQueryForRoleName(roleID))
                {
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

        if(database.checkQueryForRoleID(CHECK_QUERY, roleId))
        {
            return true;
        }
        return false;
    }

    // Hire Employee Method
    public void hireEmployee(Scanner scanner)
    {
        InputValidation inputValidation = new InputValidation();

        while (true)
        {
            String username;
            String password = "khas1234";
            String name;
            String surname;
            Date dob;
            Date dos;
            int role;

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

            if (database.insertQueryForHireEmployee(username, password, name, surname, dob, dos, role))
            {
                System.out.println("\nThe new employee added to system succesfully!\n");
                break;
            }
            else
            {
                System.out.println("Failed to add the new employee. Please try again.");
            }
        }
    }

    // Fire Employee Method
    public void fireEmployee(Scanner scanner)
    {
        do
        {
            displayAllEmployees();

            System.out.print("\nEnter the ID of the employee you want to delete: ");
            String employeeIDStr = scanner.nextLine();
            InputValidation inputValidation = new InputValidation();

            if (inputValidation.integerValidation(employeeIDStr))
            {
                int employeeID = Integer.parseInt(employeeIDStr);

                if (database.deleteQueryForEmployee(employeeID))
                {
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

        System.out.println("Welcome, "+ employee.getName() + " " + employee.getSurname());
        System.out.println("Your Role:  "+ employee.getRole()+ "\n");

        System.out.println("From this menu you can access some Manager operations.\n");

        do
        {
            System.out.println("1 - Update Your Profile");
            System.out.println("2 - Display All Employees"); // kk
            System.out.println("3 - Update Employee Non-Profile Fields");
            System.out.println("4 - Display All Roles"); // kk
            System.out.println("5 - Add a new Role"); // kk
            System.out.println("6 - Delete a Role"); // kk
            System.out.println("7 - Hire Employee"); // kk
            System.out.println("8 - Fire Employee"); // kk
            System.out.println("9 - Algorithms");
            System.out.println("10 - Logout"); // kk

            System.out.print("\nEnter your choice: ");
            String managerMenuChoice = scanner.nextLine();

            if(inputValidation.integerValidation(managerMenuChoice))
            {
                int choice = Integer.parseInt(managerMenuChoice);

                switch (choice) {
                    case 1:
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
                    case 2:
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
                    case 3:
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
                    case 4:
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
                    case 5:
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
                    case 6:
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
                    case 7:
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
                    case 8:
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
                    case 9:
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
                    case 10:
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