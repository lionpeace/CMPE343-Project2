import java.sql.*;
import java.util.Scanner;


/**
* The Manager class is a subclass of the Employee class and represents a manager in the system.
* This method handles manager-specific operations such as updating profiles, hiring or firing employees, and other management-related tasks.
* Constructs a Manager object with the specified details.
* The manager is initialized with a username, password, role, name, surname, phone number, email, date of birth, and date of start.
* This constructor calls the constructor (Employee) to initialize the common properties.
* @param username the username of the manager.
* @param password the password of the manager.
* @param role the role of the manager.
* @param name the name of the manager.
* @param surname the surname of the manager.
* @param phoneNo the phone number of the manager.
* @param email the email of the manager.
* @param dateOfBirth the date of birth of the manager.
* @param dateOfStart the date the manager started at the company.
*/

public class Manager extends Employee
{
    public Manager(String username, String password, String role, String name, String surname, String phoneNo, String email, Date dateOfBirth, Date dateOfStart)
    {
        super(username, password, role, name, surname, phoneNo, email, dateOfBirth, dateOfStart);
    }
/**
* This method allows the administrator to update profile information.
* Clears the terminal screen and prints a message indicating that the profile update operation can be performed here.
*/
    @Override
    public void updateProfile()
    {
        Main.clearTheTerminal();
        System.out.println("Update Profile");
    }

/**
* This method retrieves all employees from the database and displays them.
* It connects to the MySQL database. It executes a JOIN query to join the employee details with their roles. The retrieved data is Employee ID, Username, First Name, Last Name, Phone Number, Date of Birth, Start Date, Email, Role Name.
* The method outputs the data in a tabular format on the terminal.
* It establishes a connection to the database using JDBC.
* It executes a SQL SELECT query with a JOIN between the `employees` and `roles` tables.
* It retrieves and formats the results using `ResultSet` and `ResultSetMetaData`.
* If an SQLException occurs during the operation, the stack trace is printed.
*/
    public void displayAllEmployees()
    {
        Main.clearTheTerminal();

        // Display all employees
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/firmms?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        final String SELECT_QUERY = "SELECT e.employee_id, e.username, e.name, e.surname, e.phone_no, e.dateofbirth, e.dateofstart, e.email, r.role_name FROM firmms.employees e JOIN firmms.roles r ON e.role = r.role_id";

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

/**
* This method allows an employee's profile information to be updated.
* It acts as a placeholder for the profile update function.
* Fields such as first name, last name, and other non-sensitive details can be changed.
*/

    public void updateEmployeeProfile()
    {
        Main.clearTheTerminal();
        // Update non-profile fields (name, surname, etc.)
        System.out.println("Update Employee Profile");
    }


/**
* This method retrieves and displays all roles from the database.
* It queries the `roles` table to get the role details and displays them in a table in the console.
* The information displayed is the Role ID and Role Name.
* This method is designed to provide a clear overview of all the roles available in the system.
*/
    public void displayAllRoles()
    {
        Main.clearTheTerminal();

        // Display all roles
        // Display all employees
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/firmms?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        final String SELECT_QUERY = "SELECT * FROM firmms.roles";

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, "root", "12345");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY);

            // get ResultSet's meta data
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            System.out.print("List of Roles\n\n");

            // display the names of the columns in the ResultSet

            System.out.print("Role ID\tRole Name\n\n");

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

/**
 * This method allows the user to add a new role to the system.
 * It first prompts the user to enter a role name, validates the input.
 *Checks if the role name already exists in the database.
*  If not, inserts the new role into the `roles` table.
 * @param scanner the scanner object used to read user input from the console.
 */

    public void addNewRole(Scanner scanner)
    {
        Main.clearTheTerminal();
        InputValidation inputValidation = new InputValidation();

        final String DATABASE_URL = "jdbc:mysql://localhost:3306/firmms?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        final String CHECK_QUERY = "SELECT COUNT(*) FROM firmms.roles WHERE role_name = ?";
        final String INSERT_QUERY = "INSERT INTO firmms.roles (role_name) VALUES (?)";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, "root", "12345");
             PreparedStatement checkStatement = connection.prepareStatement(CHECK_QUERY);
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUERY))
        {
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

                    // Check if role name already exists
                    checkStatement.setString(1, newRoleName);
                    try (ResultSet checkResultSet = checkStatement.executeQuery())
                    {
                        if (checkResultSet.next() && checkResultSet.getInt(1) > 0)
                        {
                            System.out.println("Role name already exists! Please enter a different role name.");
                            continue;
                        }
                    }

                    // Insert new role
                    insertStatement.setString(1, newRoleName);

                    int rowsInserted = insertStatement.executeUpdate();
                    if (rowsInserted > 0)
                    {
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
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

/**
* This method allows the user to delete a role from the system.
* After displaying all roles, the user is prompted to enter the role ID that they want to delete.
* The method verifies that the entered role ID is valid and exists in the system.
* If the role is valid and not an administrator role (role ID 1), the role is deleted.
* Otherwise, an appropriate message is displayed.
*
* @param scanner The scanner object used to read user input from the console.
*/
    public void deleteRole(Scanner scanner)
    {
        displayAllRoles();

        final String DATABASE_URL = "jdbc:mysql://localhost:3306/firmms?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        final String CHECK_QUERY = "SELECT COUNT(*) FROM firmms.roles WHERE role_id = ?";
        final String DELETE_QUERY = "DELETE FROM firmms.roles WHERE role_id = ?";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, "root", "12345");
             PreparedStatement checkStatement = connection.prepareStatement(CHECK_QUERY);
             PreparedStatement deleteStatement = connection.prepareStatement(DELETE_QUERY))
        {
            System.out.print("Enter the ID of the role you want to delete: ");
            String roleIDStr = scanner.nextLine();
            InputValidation inputValidation = new InputValidation();

            if (inputValidation.integerValidation(roleIDStr))
            {
                int roleID = Integer.parseInt(roleIDStr);

                if(roleID == 1)
                {
                    System.out.println("You cannot delete the manager role!");
                }

                // Role ID kontrolü
                checkStatement.setInt(1, roleID);
                try (ResultSet checkResultSet = checkStatement.executeQuery())
                {
                    if (checkResultSet.next() && checkResultSet.getInt(1) > 0)
                    {
                        // Rol silme işlemi
                        deleteStatement.setInt(1, roleID);
                        int rowsAffected = deleteStatement.executeUpdate();
                        if (rowsAffected > 0)
                        {
                            System.out.println("\nRole ID " + roleID + " deleted successfully!");
                        }
                    }
                    else
                    {
                        System.out.println("There is no such role ID in the system!");
                    }
                }
            }
            else
            {
                System.out.println("Invalid role ID input!");
            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

/**
* This method checks if the requested role ID exists in the database.
* Executes a query to check if a role with the requested ID exists in the `roles` table.
* Returns true if the role ID is valid and exists in the system, otherwise false.
*
* @param roleId the role ID to check.
* @return returns true if the role ID exists in the system, otherwise false.
*/
    public static boolean isRoleIdValid(int roleId)
    {
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/firmms?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        final String CHECK_QUERY = "SELECT COUNT(*) FROM roles WHERE role_id = ?";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, "root", "12345");
             PreparedStatement checkRoleStatement = connection.prepareStatement(CHECK_QUERY))
        {
            checkRoleStatement.setInt(1, roleId);
            try (ResultSet resultSet = checkRoleStatement.executeQuery())
            {
                if (resultSet.next())
                {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

/**
* This method adds a new employee to the system.
* It asks the user to enter various details about the employee (username, first name, last name, date of birth, start date, and role),
* After validating the inputs, it inserts the data into the `employees` table in the database.
* The method performs input validation using the `InputValidation` class to ensure that the inputs are correct,
* If the inputs are valid, a new employee record is created in the database.
* @param scanner The scanner object used to read the user input.
*/

    public void hireEmployee(Scanner scanner)
    {
        Main.clearTheTerminal();

        InputValidation inputValidation = new InputValidation();

        final String DATABASE_URL = "jdbc:mysql://localhost:3306/firmms?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        final String INSERT_QUERY = "INSERT INTO `firmms`.`employees`" +
        "(`username`,`password`,`name`,`surname`,`dateofbirth`,`dateofstart`,`role`) VALUES (?,?,?,?,?,?,?);";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, "root", "12345");
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUERY))
        {
            while (true)
            {
                do
                {
                    System.out.print("Enter the username of the employee: ");
                    String username = scanner.nextLine();

                    // Validate role name
                    if (inputValidation.defaultInputValidation(username))
                    {
                        insertStatement.setString(1, username);
                        break;
                    }
                } while (true);

                String password = "khas1234";
                insertStatement.setString(2, password);

                do
                {
                    System.out.print("Enter the name of the employee: ");
                    String name = scanner.nextLine();

                    if (inputValidation.noNumberValidation(name))
                    {
                        insertStatement.setString(3, name);
                        break;
                    }
                } while (true);

                do
                {
                    System.out.print("Enter the surname of the employee: ");
                    String surname = scanner.nextLine();

                    if (inputValidation.noNumberValidation(surname))
                    {
                        insertStatement.setString(4, surname);
                        break;
                    }
                } while (true);

                do
                {
                    System.out.print("Enter the date of birth of the employee (YYYY-MM-DD): ");
                    String dateOfBirth = scanner.nextLine();

                    if (inputValidation.dateValidation(dateOfBirth))
                    {
                        Date dob = Date.valueOf(dateOfBirth);
                        insertStatement.setDate(5, dob);
                        break;
                    }
                } while (true);


                do
                {
                    System.out.print("Enter the date of start of the employee: ");
                    String dateOfStart = scanner.nextLine();
                    Date dos = Date.valueOf(dateOfStart);

                    if (inputValidation.dateValidation(dateOfStart))
                    {
                        insertStatement.setDate(6, dos);
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
                        int role = Integer.parseInt(roleStr);

                        if(isRoleIdValid(role))
                        {
                            insertStatement.setInt(7, role);
                            break;
                        }
                        else
                        {
                            System.out.println("There is no such role ID in the system!");
                        }
                    }
                } while (true);

                int rowsInserted = insertStatement.executeUpdate();
                if (rowsInserted > 0)
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
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

/**
* This method allows an employee to be deleted from the system.
* Prompts the user to enter the employee ID and verifies that the employee exists in the database.
* Also checks if the employee is a manager (role ID = 1).
* If the employee is not a manager, the employee is deleted from the `employees` table in the database.
* The method first verifies the employee ID entered and checks if the employee exists in the system.
* Checks if the employee has an administrator role. If the employee is a manager, it cannot be deleted.
* If the employee is not a manager, the record is deleted from the database.
*
* @param scanner the scanner object used to read the user input.
*/

    public void fireEmployee(Scanner scanner)
    {
        Main.clearTheTerminal();

        final String DATABASE_URL = "jdbc:mysql://localhost:3306/firmms?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        final String CHECK_QUERY1 = "SELECT COUNT(*) FROM firmms.employees WHERE employee_id = ?";
        final String CHECK_QUERY2 = "SELECT COUNT(*) FROM firmms.employees WHERE employee_id = ? AND role = 1";
        final String DELETE_QUERY = "DELETE FROM firmms.employees WHERE employee_id = ?";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, "root", "12345");
             PreparedStatement checkStatement1 = connection.prepareStatement(CHECK_QUERY1);
             PreparedStatement checkStatement2 = connection.prepareStatement(CHECK_QUERY2);
             PreparedStatement deleteStatement = connection.prepareStatement(DELETE_QUERY))
        {
            while (true)
            {
                displayAllEmployees();

                System.out.print("\nEnter the ID of the employee you want to delete: ");
                String employeeIDStr = scanner.nextLine();
                InputValidation inputValidation = new InputValidation();

                if (inputValidation.integerValidation(employeeIDStr))
                {
                    int employeeID = Integer.parseInt(employeeIDStr);

                    checkStatement1.setInt(1, employeeID);
                    try (ResultSet checkResultSet = checkStatement1.executeQuery())
                    {
                        if (checkResultSet.next() && checkResultSet.getInt(1) > 0)
                        {
                            checkStatement2.setInt(1, employeeID);
                            try(ResultSet checkResultSet2 = checkStatement2.executeQuery())
                            {
                                if(checkResultSet2.next() && checkResultSet2.getInt(1) > 0)
                                {
                                    System.out.println("You cannot delete a manager!");
                                }
                                else
                                {
                                    deleteStatement.setInt(1, employeeID);
                                    int rowsAffected = deleteStatement.executeUpdate();
                                    if (rowsAffected > 0)
                                    {
                                        displayAllEmployees();
                                        System.out.println("\nEmployee with " + employeeID + " employee ID deleted successfully!");
                                    }
                                    break;
                                }
                            }
                        }
                        else
                        {
                            System.out.println("There is no such employee ID in the system!");
                        }
                    }
                }
            }

        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
/**
 * This method serves as a placeholder for running sorting algorithms.
* It currently prints a message indicating the execution of sorting algorithms.
* The method clears the terminal screen
* Prepares the system for implementing various sorting algorithms, such as Radix Sort, Shell Sort, Heap Sort, and Insertion Sort.
 */
    public void runSortingAlgorithms()
    {
        Main.clearTheTerminal();
        // Implement Radix Sort, Shell Sort, Heap Sort, and Insertion Sort
        System.out.println("Run Sorting Algorithms");
    }

    /**
* This method displays the administrator menu, allowing the administrator to perform various operations.
* It provides access to features such as updating profile, viewing employees, managing roles, hiring and firing employees, and running ranking algorithms.
* The method retrieves the administrator's details (first name, last name, and role) and displays them in the menu.
* It goes to the relevant action based on the administrator's selection.
* If an invalid selection is entered, the menu prompts the administrator again until a valid selection is made.
*
* @param employee the employee object representing the logged-in administrator.
* @param scanner the scanner object used to read user input.
*/

    public void managerMenu(Employee employee, Scanner scanner)
    {
        InputValidation inputValidation = new InputValidation();

        System.out.println("Welcome, "+ employee.getName() + " " + employee.getSurname());
        System.out.println("Your Role:  "+ employee.getRole()+ "\n");

        System.out.println("From this menu you can access some Manager operations.\n");

        do
        {
            System.out.println("1 - Update Your Profile");
            System.out.println("2 - Display All Employees");
            System.out.println("3 - Update Employee Non-Profile Fields");
            System.out.println("4 - Display All Roles");
            System.out.println("5 - Add a new Role");
            System.out.println("6 - Delete a Role");
            System.out.println("7 - Hire Employee");
            System.out.println("8 - Fire Employee");
            System.out.println("9 - Algorithms");
            System.out.println("10 - Logout");

            System.out.print("\nEnter your choice: ");
            String managerMenuChoice = scanner.nextLine();

            if(inputValidation.integerValidation(managerMenuChoice))
            {
                int choice = Integer.parseInt(managerMenuChoice);

                switch (choice) {
                    case 1:
                        updateProfile();
                        if(!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            continue;
                        }
                    case 2:
                        displayAllEmployees();
                        if(!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            continue;
                        }
                    case 3:
                        updateEmployeeProfile();
                        if(!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            continue;
                        }
                    case 4:
                        displayAllRoles();
                        if(!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            continue;
                        }
                    case 5:
                        addNewRole(scanner);
                        if(!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            continue;
                        }
                    case 6:
                        deleteRole(scanner);
                        if(!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            continue;
                        }
                    case 7:
                        hireEmployee(scanner);
                        if(!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            continue;
                        }
                    case 8:
                        fireEmployee(scanner);
                        if(!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
                            continue;
                        }
                    case 9:
                        runSortingAlgorithms();
                        if(!Main.returnMainMenu(scanner))
                        {
                            break;
                        }
                        else
                        {
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