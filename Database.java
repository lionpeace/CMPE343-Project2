import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database extends Employee {

    final String DATABASE_URL = "jdbc:mysql://localhost:3306/firmms?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public Database(int employeeID, String username, String password, String role, String name, String surname, String phoneNo, String email, java.util.Date dateOfBirth, java.util.Date dateOfStart, Boolean newUser)
    {
        super(employeeID, username, password, role, name, surname, phoneNo, email, dateOfBirth, dateOfStart, newUser);
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, "root", "12345");
    }

    public List<String[]> returnStrArrayForDisplay(String SELECT_QUERY) // For displayRoles & displayAllEmployees
    {
        List<String[]> results = new ArrayList<>();
        try(Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_QUERY))
        {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            while(resultSet.next())
            {
                String[] row = new String[numberOfColumns];
                for(int i = 1; i <= numberOfColumns; i++)
                {
                    row[i - 1] = resultSet.getString(i);
                }
                results.add(row);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return results;
    }

    public boolean selectRoleFromEmployeeID(String CHECK_QUERY, int employeeID)
    {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_QUERY))
        {

            preparedStatement.setInt(1, employeeID);

            try (ResultSet resultSet = preparedStatement.executeQuery())
            {
                if (resultSet.next() && resultSet.getInt(1) > 0)
                {
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

    public boolean selectCountFromRoleID(String CHECK_QUERY, int roleID)
    {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_QUERY))
        {
            preparedStatement.setInt(1, roleID);

            try (ResultSet resultSet = preparedStatement.executeQuery())
            {
                if (resultSet.next() && resultSet.getInt(1) > 0)
                {
                    return true;
                }
                else
                {
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

    public boolean selectCountFromRoleName(String CHECK_QUERY, String roleName)
    {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_QUERY))
        {

            preparedStatement.setString(1, roleName);

            try (ResultSet resultSet = preparedStatement.executeQuery())
            {
                if (resultSet.next() && resultSet.getInt(1) > 0)
                {
                    return true;
                }
                else
                {
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

    public boolean insertRole(String roleName)
    {
        String CHECK_QUERY = "SELECT COUNT(*) FROM firmms.roles WHERE role_name = ?";
        String INSERT_QUERY = "INSERT INTO firmms.roles (role_name) VALUES (?)";

        try (Connection connection = connect();
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUERY))
        {
            if (selectCountFromRoleName(CHECK_QUERY, roleName))
            {
                System.out.println("\nRole name already exists! Please enter a different role name.\n");
                return false;
            }

            // Insert new role
            insertStatement.setString(1, roleName);
            int rowsInserted = insertStatement.executeUpdate();
            return rowsInserted > 0;

        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return false;
    }

    public boolean deleteRole(int roleID)
    {
        if(roleID == 1)
        {
            System.out.println("\nYou cannot delete the manager role!");
            return false;
        }
        else
        {
            String CHECK_QUERY = "SELECT COUNT(*) FROM firmms.roles WHERE role_id = ?";
            String DELETE_QUERY = "DELETE FROM firmms.roles WHERE role_id = ?";

            try (Connection connection = connect();
                 PreparedStatement deleteStatement = connection.prepareStatement(DELETE_QUERY))
            {
                if (selectCountFromRoleID(CHECK_QUERY, roleID))
                {
                    deleteStatement.setInt(1, roleID);
                    int rowsAffected = deleteStatement.executeUpdate();
                    return rowsAffected > 0;
                }
                else
                {
                    System.out.println("\nThere is no such role ID in the system!\n");
                    return false;
                }

            }
            catch (SQLIntegrityConstraintViolationException e)
            {
                System.out.println("\nFirst, you have to delete the employee that have this role!\n");
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
            return false;
        }
    }

    public boolean insertEmployee(String username, String password, String name, String surname, Date dateOfBirth, Date dateOfStart, int role)
    {
        String INSERT_QUERY = "INSERT INTO `firmms`.`employees` (`username`, `password`, `name`, `surname`, `dateofbirth`, `dateofstart`, `role`) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = connect();
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUERY)) {

            insertStatement.setString(1, username);
            insertStatement.setString(2, password);
            insertStatement.setString(3, name);
            insertStatement.setString(4, surname);
            insertStatement.setDate(5, dateOfBirth);
            insertStatement.setDate(6, dateOfStart);
            insertStatement.setInt(7, role);

            int rowsInserted = insertStatement.executeUpdate();
            return rowsInserted > 0;

        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return false;
    }

    public boolean deleteEmployee(int employeeID)
    {
        String CHECK_QUERY = "SELECT role FROM firmms.employees WHERE employee_id = ?";
        String DELETE_QUERY = "DELETE FROM firmms.employees WHERE employee_id = ?";

        try (Connection connection = connect();
             PreparedStatement checkStatement = connection.prepareStatement(CHECK_QUERY);
             PreparedStatement deleteStatement = connection.prepareStatement(DELETE_QUERY))
        {
            checkStatement.setInt(1, employeeID);
            try (ResultSet checkResultSet = checkStatement.executeQuery())
            {
                if (checkResultSet.next() && checkResultSet.getInt(1) > 0)
                {
                    if(employeeID == getEmployeeID())
                    {
                        System.out.println("\nYou cannot fire yourself!\n");
                    }
                    else
                    {
                        deleteStatement.setInt(1, employeeID);
                        int rowsAffected = deleteStatement.executeUpdate();
                        if (rowsAffected > 0)
                        {
                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    }
                }
                else
                {
                    System.out.println("\nThere is no such employee ID in the system!\n");
                    return false;
                }
            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return false;
    }

    public void displayProfileFromDatabase(int employeeID, int currentEmployeeID)
    {
        final String SELECT_QUERY = "SELECT e.employee_id, e.username, e.password, e.name, e.surname, e.phone_no, e.dateofbirth, e.dateofstart, e.email, r.role_name FROM firmms.employees e JOIN firmms.roles r ON e.role = r.role_id WHERE e.employee_id = ?";

        try (Connection connection = connect();
             PreparedStatement selectStatement = connection.prepareStatement(SELECT_QUERY))
        {
            selectStatement.setInt(1, employeeID);

            try (ResultSet resultSet = selectStatement.executeQuery())
            {
                if (resultSet.next())
                {
                    String password = resultSet.getString("password");
                    StringBuilder stars = new StringBuilder();
                    String passwordWithStars;
                    int length = password.length();

                    if (resultSet.getInt(1) != currentEmployeeID)
                    {
                        for (int i = 0; i < length; i++)
                        {
                            stars.append("*");
                        }
                        passwordWithStars = stars.toString();
                    }
                    else
                    {
                        for (int i = 0; i < length - 2; i++)
                        {
                            stars.append("*");
                        }
                        passwordWithStars = password.substring(0, 2) + stars.toString();
                    }

                    System.out.println("\n1 - Employee ID: " + resultSet.getInt(1)); // Nobody can edit
                    System.out.println("\n2 - Username: " + resultSet.getString("username")); // Only manager can edit
                    System.out.println("\n3 - Password: " + passwordWithStars); // Only manager can edit
                    System.out.println("\n4 - Role: " + resultSet.getString("role_name")); // Only manager can edit
                    System.out.println("\n5 - Name: " + resultSet.getString("name")); // Only manager can edit
                    System.out.println("\n6 - Surname: " + resultSet.getString("surname")); // Only manager can edit
                    System.out.println("\n7 - Phone Number: " + resultSet.getString("phone_no")); // Everyone can edit
                    System.out.println("\n8 - E-mail: " + resultSet.getString("email")); // Everyone can edit
                    System.out.println("\n9 - Date of Birth: " + resultSet.getString("dateofbirth")); // Only manager can edit
                    System.out.println("\n10 - Date of Start: " + resultSet.getString("dateofstart")); // Only manager cann  edit
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private String getUpdateQuery(int choice)
    {
        switch (choice)
        {
            case 2:
                return "UPDATE firmms.employees SET username = ? WHERE employee_id = ?"; // String
            case 3:
                return "UPDATE firmms.employees SET password = ? WHERE employee_id = ?"; // String
            case 4:
                return "UPDATE firmms.employees SET role = ? WHERE employee_id = ?"; // int
            case 5:
                return "UPDATE firmms.employees SET name = ? WHERE employee_id = ?"; // String
            case 6:
                return "UPDATE firmms.employees SET surname = ? WHERE employee_id = ?"; // String
            case 7:
                return "UPDATE firmms.employees SET phone_no = ? WHERE employee_id = ?"; // String
            case 8:
                return "UPDATE firmms.employees SET email = ? WHERE employee_id = ?"; // String
            case 9:
                return "UPDATE firmms.employees SET dateofbirth = ? WHERE employee_id = ?"; // Date
            case 10:
                return "UPDATE firmms.employees SET dateofstart = ? WHERE employee_id = ?"; // Date
            default:
                return null;
        }
    }

    public boolean updateValue(String UPDATE_QUERY, int employeeID, String value)
    {
        try (Connection connection = connect();
             PreparedStatement updateStatement = connection.prepareStatement(UPDATE_QUERY))
        {
            updateStatement.setString(1, value);
            updateStatement.setInt(2, employeeID);

            int rowsUpdated = updateStatement.executeUpdate();

            if(rowsUpdated > 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateValue(String UPDATE_QUERY, int employeeID, Date value)
    {
        try (Connection connection = connect();
             PreparedStatement updateStatement = connection.prepareStatement(UPDATE_QUERY))
        {
            updateStatement.setDate(1, value);
            updateStatement.setInt(2, employeeID);

            int rowsUpdated = updateStatement.executeUpdate();

            if(rowsUpdated > 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateValue(String UPDATE_QUERY, int employeeID, int roleID)
    {
        try (Connection connection = connect();
             PreparedStatement updateStatement = connection.prepareStatement(UPDATE_QUERY))
        {
            updateStatement.setInt(1, roleID);
            updateStatement.setInt(2, employeeID);

            int rowsUpdated = updateStatement.executeUpdate();

            if(rowsUpdated > 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean checkForManager(int employeeID)
    {
        String MANAGER_CHECK_QUERY = "SELECT role FROM firmms.employees WHERE employee_id = ?";

        try (Connection connection = connect();
             PreparedStatement managerCheckStatement = connection.prepareStatement(MANAGER_CHECK_QUERY))
        {
            managerCheckStatement.setInt(1, employeeID);

            try(ResultSet managerCheckResultSet = managerCheckStatement.executeQuery())
            {
                if (managerCheckResultSet.next() && managerCheckResultSet.getInt(1) > 0)
                {
                    int chosenEmployeesRole = managerCheckResultSet.getInt("role");

                    if (chosenEmployeesRole == 1)
                    {
                        System.out.println("\nYou have no authority to make this change!\n");
                        return true;
                    }

                    else
                    {
                        return false;
                    }
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public void updateEmployeeInfo(int choice, int employeeID , Scanner scanner)
    {
        InputValidation inputValidation = new InputValidation();
        String UPDATE_QUERY = getUpdateQuery(choice);

        if(choice == 2) // Username update, Only manager can edit
        {
            String presentRole = getRole();

            if(presentRole.equals("Manager"))
            {
                do
                {
                    System.out.print("Please enter the new username: ");
                    String username = scanner.nextLine();

                    if(inputValidation.defaultInputValidation(username))
                    {
                        if(username.length() > 150)
                        {
                            System.out.print("\nYou cannot enter more than 150 characters!\n");
                            continue;
                        }

                        if(updateValue(UPDATE_QUERY, employeeID, username))
                        {
                            if(employeeID == getEmployeeID())
                            {
                                setUsername(username);
                            }
                            System.out.println("\nDatabase is being updated...\n");
                            Main.delay();
                            displayProfileFromDatabase(employeeID, getEmployeeID());
                            System.out.println("\nUsername changed successfully!\n");
                            break;
                        }
                    }
                } while(true);
            }
            else
            {
                System.out.println("\nYou have no authority to make this change!\n");
            }
        }
        else if(choice == 3) // Password update, only present employee can edit
        {
            if(employeeID != getEmployeeID())
            {
                System.out.println("\nYou have no authority to make this change!\n");
            }

            else
            {
                do
                {
                    System.out.print("Please enter the new password: ");
                    String password = scanner.nextLine();

                    if(inputValidation.passwordValidation(password))
                    {
                        if(password.length() > 150)
                        {
                            System.out.print("\nYou cannot enter more than 150 characters!\n");
                            continue;
                        }

                        if(updateValue(UPDATE_QUERY, employeeID, password))
                        {
                            if(employeeID == getEmployeeID())
                            {
                                setPassword(password);
                            }
                            System.out.println("\nDatabase is being updated...\n");
                            Main.delay();
                            displayProfileFromDatabase(employeeID, getEmployeeID());
                            System.out.println("\nPassword changed successfully!\n");
                            break;
                        }
                    }
                } while(true);
            }
        }
        else if(choice == 4) // Role update, only manager can edit (Manager cannot edit for himself or any other manager)
        {
            String presentRole = getRole();

            if(!presentRole.equals("Manager"))
            {
                System.out.println("\nYou have no authority to make this change!\n");
            }

            else
            {
                int presentEmployeeID = getEmployeeID();

                if(presentEmployeeID != employeeID) // If the ID of the editor is not equal to the ID of the edited employee
                {
                    if(!checkForManager(employeeID))
                    {
                        String CHECK_QUERY = "SELECT COUNT(*) FROM firmms.roles WHERE role_id = ?";

                        do
                        {
                            Manager.displayAllRoles();

                            System.out.print("Please enter the new role: ");
                            String roleStr = scanner.nextLine();

                            if(inputValidation.integerValidation(roleStr))
                            {
                                int roleID = Integer.parseInt(roleStr);

                                if (selectCountFromRoleID(CHECK_QUERY, roleID))
                                {
                                    if(updateValue(UPDATE_QUERY, employeeID, roleID))
                                    {
                                        System.out.println("\nDatabase is being updated...\n");
                                        Main.delay();
                                        displayProfileFromDatabase(employeeID, getEmployeeID());
                                        System.out.println("\nRole changed successfully!\n");
                                        break;
                                    }
                                }
                                else
                                {
                                    System.out.println("\nThere is no such role ID in the system!\n");
                                }

                            }
                        } while(true);

                    }
                }
                else
                {
                    System.out.println("\nYou are manager, you cannot change your role!\n");
                }
            }
        }
        else if(choice == 5) // Name update, only manager can edit
        {
            String presentRole = getRole();

            if(presentRole.equals("Manager"))
            {
                do
                {
                    System.out.print("Please enter the new name: ");
                    String name = scanner.nextLine();

                    if(inputValidation.noNumberValidation(name))
                    {
                        if(name.length() > 150)
                        {
                            System.out.print("\nYou cannot enter more than 150 characters!\n");
                            continue;
                        }

                        if(updateValue(UPDATE_QUERY, employeeID, name))
                        {
                            if(employeeID == getEmployeeID())
                            {
                                setName(name);
                            }
                            System.out.println("\nDatabase is being updated...\n");
                            Main.delay();
                            displayProfileFromDatabase(employeeID, getEmployeeID());
                            System.out.println("\nName changed successfully!\n");
                            break;
                        }
                    }
                } while(true);
            }
            else
            {
                System.out.println("\nYou have no authority to make this change!\n");
            }
        }
        else if(choice == 6) // Surname update, only manager can edit
        {
            String presentRole = getRole();

            if(presentRole.equals("Manager"))
            {
                do
                {
                    System.out.print("Please enter the new surname: ");
                    String surname = scanner.nextLine();

                    if(inputValidation.noNumberValidation(surname))
                    {
                        if(surname.length() > 150)
                        {
                            System.out.print("\nYou cannot enter more than 150 characters!\n");
                            continue;
                        }

                        if(updateValue(UPDATE_QUERY, employeeID, surname))
                        {
                            if(employeeID == getEmployeeID())
                            {
                                setSurname(surname);
                            }
                            System.out.println("\nDatabase is being updated...\n");
                            Main.delay();
                            displayProfileFromDatabase(employeeID, getEmployeeID());
                            System.out.println("\nSurname changed successfully!\n");
                            break;
                        }
                    }
                } while(true);
            }
            else
            {
                System.out.println("\nYou have no authority to make this change!\n");
            }
        }
        else if(choice == 7) // Phone Number update, only present employee can edit
        {
            int presentEmployeeID = getEmployeeID();

            if(presentEmployeeID != employeeID)
            {
                System.out.println("\nYou have no authority to make this change!\n");
            }
            else
            {
                do
                {
                    System.out.print("Please enter the new phone number: ");
                    String phoneNo = scanner.nextLine();

                    if(inputValidation.phoneNoValidation(phoneNo))
                    {
                        if(phoneNo.length() > 40)
                        {
                            System.out.print("\nYou cannot enter more than 40 characters!\n");
                            continue;
                        }

                        if(updateValue(UPDATE_QUERY, employeeID, phoneNo))
                        {
                            if(employeeID == getEmployeeID())
                            {
                                setPhoneNo(phoneNo);
                            }
                            System.out.println("\nDatabase is being updated...\n");
                            Main.delay();
                            displayProfileFromDatabase(employeeID, getEmployeeID());
                            System.out.println("\nPhone number changed successfully!\n");
                            break;
                        }
                    }
                } while(true);
            }

        }
        else if(choice == 8) // E-mail update, only present employee can edit
        {
            int presentEmployeeID = getEmployeeID();

            if(presentEmployeeID != employeeID)
            {
                System.out.println("\nYou have no authority to make this change!\n");
            }
            else
            {

                do
                {
                    System.out.print("Please enter the new email: ");
                    String email = scanner.nextLine();

                    if(inputValidation.emailValidation(email))
                    {
                        if(email.length() > 100)
                        {
                            System.out.print("\nYou cannot enter more than 100 characters!\n");
                            continue;
                        }

                        if(updateValue(UPDATE_QUERY, employeeID, email))
                        {
                            if(employeeID == getEmployeeID())
                            {
                                setEmail(email);
                            }
                            System.out.println("\nDatabase is being updated...\n");
                            Main.delay();
                            displayProfileFromDatabase(employeeID, getEmployeeID());
                            System.out.println("\nName changed successfully!\n");
                            break;
                        }
                    }
                } while(true);
            }
        }

        else if(choice == 9) // Birth Date update, only manager can edit
        {
            String presentRole = getRole();

            if(presentRole.equals("Manager"))
            {
                do
                {
                    System.out.print("Please enter the new birth date: ");
                    String dateOfBirthStr = scanner.nextLine();

                    if(inputValidation.dateValidation(dateOfBirthStr))
                    {
                        java.sql.Date dob = java.sql.Date.valueOf(dateOfBirthStr);

                        if(updateValue(UPDATE_QUERY, employeeID, dob))
                        {
                            if(employeeID == getEmployeeID())
                            {
                                setDateOfBirth(dob);
                            }
                            System.out.println("\nDatabase is being updated...\n");
                            Main.delay();
                            displayProfileFromDatabase(employeeID, getEmployeeID());
                            System.out.println("\nDate of birth changed successfully!\n");
                            break;
                        }
                    }
                } while(true);
            }
            else
            {
                System.out.println("\nYou have no authority to make this change!\n");
            }
        }

        else if(choice == 10) // Start date update, only manager can edit
        {
            String presentRole = getRole();

            if(presentRole.equals("Manager"))
            {
                do
                {
                    System.out.print("Please enter the new birth date: ");
                    String dateOfStartStr = scanner.nextLine();

                    if(inputValidation.dateValidation(dateOfStartStr))
                    {
                        java.sql.Date dos = java.sql.Date.valueOf(dateOfStartStr);

                        if(updateValue(UPDATE_QUERY, employeeID, dos))
                        {
                            if(employeeID == getEmployeeID())
                            {
                                setDateOfStart(dos);
                            }
                            System.out.println("\nDatabase is being updated...\n");
                            Main.delay();
                            displayProfileFromDatabase(employeeID, getEmployeeID());
                            System.out.println("\nDate of start changed successfully!\n");
                            break;
                        }
                    }
                } while(true);
            }
            else
            {
                System.out.println("\nYou have no authority to make this change!\n");
            }
        }
    }

    public boolean updatePassword(String UPDATE_QUERY, String newPassword, int employeeID)
    {
        try (Connection connection = connect();
             PreparedStatement updateStatement = connection.prepareStatement(UPDATE_QUERY))
        {
            updateStatement.setString(1, newPassword);
            updateStatement.setBoolean(2, false);
            updateStatement.setInt(3, employeeID);

            int rowsUpdated = updateStatement.executeUpdate();

            if(rowsUpdated > 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void updateProfile(Scanner scanner) {
        // Empty method
    }
}