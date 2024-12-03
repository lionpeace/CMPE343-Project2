import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database extends Employee {

    final String DATABASE_URL = "jdbc:mysql://localhost:3306/firmms?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

/**
     * This method to initialise the Database object with employee details.
     * calls the superclass (Employee) constructor to initialise employee attributes.
     * @param employeeID Unique ID of the employee.
     * @param username The username associated with the employee.
     * @param password The password associated with the employee.
     * @param role The role of the employee.
     * @param name The employee's first name.
     * @param surname Employee's surname.
     * @param phoneNo Employee's phone number.
     * @param email Employee's e-mail address.
     * @param dateOfBirth Employee's date of birth.
     * @param dateOfStart Employee's start date.
     * @param newUser A boolean indicating whether the employee is a new user.
     */

    public Database(int employeeID, String username, String password, String role, String name, String surname, String phoneNo, String email, java.util.Date dateOfBirth, java.util.Date dateOfStart, Boolean newUser)
    {
        super(employeeID, username, password, role, name, surname, phoneNo, email, dateOfBirth, dateOfStart, newUser);
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, "root", "12345");
    }

    
/**
* This method Executes a SQL query and returns the results as a list of string arrays for display purposes.
* Used to display roles and employees by retrieving data from a database.
*Executes the provided SELECT query, processes the result set, and stores each row as a string array.
* The results are returned as a list of string arrays, where each array represents a row of the result set, and each element in the array corresponds to a column in that row.
* Each string array corresponds to a row, and the elements of the array represent the values ​​of the columns.
* @param SELECT_QUERY The SQL SELECT query to execute.
* @return A list of string arrays representing the rows and columns of the query result.

*/
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
/**
* This method checks if a role exists for a given employee ID by executing a SQL query.
* Used to verify if a given employee has a role assigned to them in the database.
* Executes a prepared statement with the given employee ID and checks if the result set contains any records.
* If the result set contains a record with a positive value in the first column, returns true indicating that the role exists for the employee.
* If no record is found, prints a message indicating that the employee ID does not exist and returns false.
* @param CHECK_QUERY SQL query to be executed to check the role associated with the employee ID.
* Should return a value indicating whether the employee exists with the role.
* @param employeeID The ID of the employee whose role is being checked.
* @return true if the employee has a role; false otherwise
*/
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

/**
*This method is used to verify if there are any associated records for a given role ID in the database.
* If the result set contains a record with a positive value in the first column, it returns true, indicating that records exist for the specified role ID.
* If no record is found, it returns false.
* @param CHECK_QUERY SQL query to be executed to check the number of records associated with the role ID.
* It should return a numeric number indicating the existence of associated records.
* @param roleID The ID of the role to check for associated records.
* @return True if there are records for the specified role ID; false otherwise.
*/
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
/**
*This method is used to verify if there are any associated records for a given role ID in the database.
* If the result set contains a record with a positive value in the first column, it returns true, indicating that records exist for the specified role ID.
* If no record is found, it returns false.
* @param CHECK_QUERY SQL query to be executed to check the number of records associated with the role name.
* It should return a numeric number indicating the existence of associated records.
* @param roleName Name of the role to check for associated records.
* @return True if there are records for the specified role ID; false otherwise.
*/
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
/**
* This method tries to add a new role to the `firmms.roles` database table.
* Checks if the role name exists in the `firmms.roles` table.
* If the role name exists, logs a message and returns false without making any changes.
* If the role name does not exist, adds the new role name to the table.</li>
* @param roleName the name of the role to add to the database; cannot be null or empty
* @return true if the role was added successfully, false if the role name already exists or an SQL error occurs
*/
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
/**
* This method deletes a role from the `firmms.roles` database table based on its ID.
* Checks if the roleID value corresponds to the manager role (ID: 1).
* If the roleID value is 1, logs a message and returns false because the manager role cannot be deleted.
* Verifies if the role ID exists in the database using a helper method.
* If the role ID exists, deletes the role from the database.
* If the role ID does not exist, logs a message and returns false.
* Prevents the deletion of roles associated with employees by also handling foreign key constraints.
* @param roleID unique identifier of the role to be deleted; must not be 1 (manager role)
* @return true if the role was successfully deleted, false if the role could not be deleted or an error occurred.
*/
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
/**
* This method adds a new employee to the firmms.employees database table.
* Establishes a connection to the database using the connect method.
* Uses a prepared statement to insert employee details into the employees table.
* Prevents SQL injection by parameterizing the SQL query.
* @param username The unique username of the employee. Cannot be null or null.
* @param password The password of the employee. Cannot be null or null.
* @param name The name of the employee. Cannot be null or null.
* @param surname The surname of the employee. Cannot be null or null.
* @param dateOfBirth The birth date of the employee. Cannot be null.
* @param dateOfStart The start date of the employee. Cannot be null.
* @param role The role ID assigned to the employee. Must be a valid role ID in the database.
* @return True if the employee was added successfully, false if not.
*/

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

/**
* This method deletes an employee from the firmms.employees database table based on the given employee ID.
* Verifies if the employee ID exists in the database.
* Checks if the employee ID corresponds to the current user (if the current user is trying to delete himself).
* If the employee exists and the current user is not trying to delete himself, the employee record is deleted from the database.
* @param employeeID Unique identifier of the employee to be deleted. Must exist in the system.
* @return True if the employee was successfully deleted, false otherwise.
*/
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
/**
* This method displays an employee's profile information from the firmms.employees database.
* Retrieves and displays various details of an employee's profile. (Employee ID, Username, Password, Role name, First name, Last name, Phone number, Email, Date of birth, Start date)
* Profile details are retrieved from the database.
* Sensitive information such as password is masked depending on whether the current employee is viewing their own profile or another employee's profile.
* @param employeeID The ID of the employee whose profile is to be viewed. This ID must exist in the database.
* @param currentEmployeeID The ID of the currently logged in employee. Used to determine whether the profile being viewed is the current employee's own profile.
*/
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
/**
* This method returns the appropriate SQL UPDATE query based on the user's selection.
* Used to select the correct SQL query to update specific employee details in the `firmms.employees` table based on the given option.
* The method uses a switch statement to return the specific query based on the selection parameter.
* Possible options and related updates
* - 2: Update username
* - 3: Update password
* - 4: Update role
* - 5: Update first name
* - 6: Update last name
* - 7: Update phone number
* - 8: Update email
* - 9: Update date of birth
* - 10: Update start date
*
* When the selection does not match any of the conditions, the method returns `null`.
*
* @param choice The selection specifying which employee detail to update.
* A valid selection is an integer between 2 and 10.
* @return The corresponding SQL UPDATE query as a string or `null` if the selection is invalid.
*/
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

/**
* This method updates a specific value in the `firmms.employees` table for an employee.
* It is used to update a specific column in the `firmms.employees` table identified by the provided `employeeID` with the new value passed as the `value` parameter.
* To perform the update, the query is passed as an argument (`UPDATE_QUERY`) which provides flexibility in updating various employee fields such as username, password, etc.
* @param UPDATE_QUERY The SQL query used to update the employee value. It should contain two placeholders:
* one for the value to be updated, and the other for the employee ID.
* @param employeeID The ID of the employee whose value is to be updated.
* @param value The new value to be set for the employee field. It is passed as a string.
* @return `true` if the update was successful, `false` otherwise.
*/

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
/**
* This method updates a specific value in the `firmms.employees` table for a specific employee.
* @param UPDATE_QUERY The SQL query used to update the employee's value. It should contain two placeholders:
* One for the value to be updated, the other for the employee ID.
* @param employeeID The ID of the employee whose value is to be updated. This should correspond to an existing employee in the database.
* @param value The new value to set for the employee's field. It is passed as a string.
* @return `true` if the update was successful, `false` otherwise.
*/
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
/**
* This method updates a specific role ID in the `firmms.employees` table for an employee.
* It is used to update an employee's role, and the new role ID passed as the `roleID` parameter is used.
* @param UPDATE_QUERY The SQL query used to update the employee's role.
* @param employeeID The ID of the employee whose role is to be updated. This must correspond to an existing employee in the database.
* @param roleID The new role ID to be set for the employee. It is passed as an integer.
* @return `true` if the update was successful, `false` otherwise.
*/

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
/**
* This method checks if the employee has an administrator role (role id 1) in the system.
* Checks the role of an employee in the `firmms.employees` table by querying the `role` column for the given `employeeID`.
* If the employee's role is defined as an administrator (role id = 1), the method prints a message and returns `true` to indicate that the employee is an administrator,
* This method ensures that only non-administrator employees can perform certain operations that administrators are restricted from.
* @param employeeID The ID of the employee whose role is to be checked. This must correspond to an existing employee in the database.
* @return `true` if the employee is an administrator (role id 1), it cannot perform the operation, `false` if the employee is not an administrator, so the operation can continue.
*/
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
/**
* This method Updates an employee's information based on the given selection and employee ID.
* This method allows various employee information to be updated.
* If the user has permission, the method prompts the user for the new value, validates the input, and updates the database accordingly.
* The method ensures that the input values ​​meet certain validation criteria (e.g. password length,
* phone number format, etc.) before proceeding with the update.
* After the update, the employee's profile is reloaded from the database to reflect the changes.
* @param choice The option selected by the user that specifies which information to update. * Possible values:
* 2 - Username
* 3 - Password
* 4 - Role
* 5 - First Name
* 6 - Last Name
* 7 - Phone Number
* 8 - Email
* 9 - Date of Birth
* 10 - Start Date
* @param employeeID The ID of the employee whose information is being updated.
* The user must have permission to edit this employee's information.
* @param scanner The Scanner object used to read input from the user.
*/
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
/**
*This method updates the password of an employee in the database.
* Updates the password of the employee specified by `employeeID` in the database.
* Uses the provided `UPDATE_QUERY` to execute the update operation and
* replaces the old password with the new password. If the update is successful (i.e., one or more rows are updated), the method returns `true`; otherwise, it returns `false`.
* The method uses `PreparedStatement` to prevent SQL injection and to ensure proper parameter handling when executing the update query.
* @param UPDATE_QUERY The SQL query used to update the password. The new password should have placeholders for the active state and employee ID.
* @param newPassword The new password to set for the employee.
* @param employeeID The ID of the employee whose password was updated.
* @return `true` if the password update was successful, `false` otherwise.
*/
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
/**
* This method is a placeholder method that does not implement any functionality.
* @param scanner The scanner object used to read input from the user.
*/
    @Override
    public void updateProfile(Scanner scanner) {
        // Empty method
    }
}