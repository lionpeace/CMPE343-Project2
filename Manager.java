import java.sql.*;
import java.util.Scanner;

public class Manager extends Employee
{
    public Manager(int employeeID, String username, String password, String role, String name, String surname, String phoneNo, String email, Date dateOfBirth, Date dateOfStart, Boolean newUser)
    {
        super(employeeID, username, password, role, name, surname, phoneNo, email, dateOfBirth, dateOfStart, newUser);
    }

    @Override
    public void updateProfile(Scanner scanner) // Update MANAGER profile
    {
        Main.clearTheTerminal();
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

    public void displayAllEmployees()
    {
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

    public static boolean isEmployeeIdValid(int employeeID)
    {
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/firmms?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        final String CHECK_QUERY = "SELECT role FROM employees WHERE employee_id = ?";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, "root", "12345");
             PreparedStatement checkRoleStatement = connection.prepareStatement(CHECK_QUERY))
        {

            checkRoleStatement.setInt(1, employeeID);

            try (ResultSet resultSet = checkRoleStatement.executeQuery())
            {
                if (resultSet.next())
                {
                    int role = resultSet.getInt("role");
                    if (role == 1)
                    {
                        System.out.println("\nYou cannot update a manager!\n");
                        return false;
                    }
                    return true;
                }
                else
                {
                    System.out.println("\nEmployee ID does not exist!\n");
                    return false;
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void updateEmployeeProfile(Scanner scanner)
    {
        InputValidation inputValidation = new InputValidation();
        Main.clearTheTerminal();
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
                        displayProfileFromDatabase(employeeChoice);
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
                                updateEmployeeInfoFromChoice(choice, employeeChoice ,scanner);
                            }
                        }
                    } while (true);

                    break;
                }
            }
        } while(true);
    }

    public static void displayAllRoles()
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
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

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
                            System.out.println("\nRole ID " + roleID + " deleted successfully!\n");
                        }
                    }
                    else
                    {
                        System.out.println("\nThere is no such role ID in the system!\n");
                    }
                }
            }
            else
            {
                System.out.println("\nInvalid role ID input!\n");
            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

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

    public void runSortingAlgorithms()
    {
        Main.clearTheTerminal();
        // Implement Radix Sort, Shell Sort, Heap Sort, and Insertion Sort
        System.out.println("Run Sorting Algorithms");
    }

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