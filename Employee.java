import java.sql.*;
import java.util.*;
import java.util.Date;

public abstract class Employee
{
    private int employeeID;
    private String username;
    private String password;
    private String role;
    private String name;
    private String surname;
    private String phoneNo;
    private String email;
    private Date dateOfBirth;
    private Date dateOfStart;
    public Boolean newUser;

    public Employee(int employeeID, String username, String password, String role, String name, String surname, String phoneNo, String email, Date dateOfBirth, Date dateOfStart, Boolean newUser)
    {
        this.employeeID = employeeID;
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.phoneNo = phoneNo;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.dateOfStart = dateOfStart;
        this.newUser = newUser;
    }

    public int getEmployeeID(){
        return employeeID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getDateOfStart() {
        return dateOfStart;
    }

    public Boolean getNewUser() {
        return newUser;
    }

    public void setEmployeeID(int employeeID){
        this.employeeID = employeeID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfStart(Date dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public void setNewUser(Boolean newUser) {
        this.newUser = newUser;
    }

    public abstract void updateProfile(Scanner scanner);

    public void displayProfileFromDatabase(int employeeID)
    {
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/firmms?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        final String SELECT_QUERY = "SELECT e.employee_id, e.username, e.password, e.name, e.surname, e.phone_no, e.dateofbirth, e.dateofstart, e.email, r.role_name FROM firmms.employees e JOIN firmms.roles r ON e.role = r.role_id WHERE e.employee_id = ?";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, "root", "12345");
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

                    if (resultSet.getInt(1) != getEmployeeID())
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

                    System.out.println("1 - Employee ID: " + resultSet.getInt(1)); // Nobody can edit
                    System.out.println("2 - Username: " + resultSet.getString("username")); // Only manager can edit
                    System.out.println("3 - Password: " + passwordWithStars); // Only manager can edit
                    System.out.println("4 - Role: " + resultSet.getString("role_name")); // Only manager can edit
                    System.out.println("5 - Name: " + resultSet.getString("name")); // Only manager can edit
                    System.out.println("6 - Surname: " + resultSet.getString("surname")); // Only manager can edit
                    System.out.println("7 - Phone Number: " + resultSet.getString("phone_no")); // Everyone can edit
                    System.out.println("8 - E-mail: " + resultSet.getString("email")); // Everyone can edit
                    System.out.println("9 - Date of Birth: " + resultSet.getString("dateofbirth")); // Only manager can edit
                    System.out.println("10 - Date of Start: " + resultSet.getString("dateofstart")); // Only manager cann  edit
                    System.out.println("11 - Exit This Operation" );
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    /*
    public void displayProfile(int presentEmployeeID)
    {
        System.out.println("1 - Employee ID: " + employeeID); // Nobody can edit
        System.out.println("2 - Username: " + username); // Only manager can edit

        if(presentEmployeeID == employeeID)
        {
            int length = password.length();
            StringBuilder stars = new StringBuilder();
            for(int i = 0; i < length - 2; i++)
            {
                stars.append("*");
            }
            System.out.println("3 - Password: " + password.charAt(0) + password.charAt(1) + stars.toString()); // Only present employee can edit
        }
        else
        {
            int length = password.length();
            StringBuilder stars = new StringBuilder();
            for(int i = 0; i < length; i++)
            {
                stars.append("*");
            }
            System.out.println("3 - Password: " + stars.toString()); // Only present employee can edit
        }

        System.out.println("4 - Role: " + role); // Only manager can edit
        System.out.println("5 - Name: " + name); // Only manager can edit
        System.out.println("6 - Surname: " + surname); // Only manager can edit
        System.out.println("7 - Phone Number: " + phoneNo); // Everyone can edit
        System.out.println("8 - E-mail: " + email); // Everyone can edit
        System.out.println("9 - Date of Birth: " + dateOfBirth); // Only manager can edit
        System.out.println("10 - Date of Start: " + dateOfStart); // Only manager cann  edit
        System.out.println("11 - Exit This Operation" );
    }
    */
    public void updateEmployeeInfoFromChoice(int choice, int employeeID , Scanner scanner)
    {
        InputValidation inputValidation = new InputValidation();
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/firmms?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String UPDATE_QUERY = null;

        if(choice == 2)
        {
            UPDATE_QUERY = "UPDATE firmms.employees SET username = ? WHERE employee_id = ?";
        }
        else if(choice == 3)
        {
            UPDATE_QUERY = "UPDATE firmms.employees SET password = ? WHERE employee_id = ?";
        }
        else if(choice == 4)
        {
            UPDATE_QUERY = "UPDATE firmms.employees SET role = ? WHERE employee_id = ?";
        }
        else if(choice == 5)
        {
            UPDATE_QUERY = "UPDATE firmms.employees SET name = ? WHERE employee_id = ?";
        }
        else if(choice == 6)
        {
            UPDATE_QUERY = "UPDATE firmms.employees SET surname = ? WHERE employee_id = ?";
        }
        else if(choice == 7)
        {
            UPDATE_QUERY = "UPDATE firmms.employees SET phone_no = ? WHERE employee_id = ?";
        }
        else if(choice == 8)
        {
            UPDATE_QUERY = "UPDATE firmms.employees SET email = ? WHERE employee_id = ?";
        }
        else if(choice == 9)
        {
            UPDATE_QUERY = "UPDATE firmms.employees SET dateofbirth = ? WHERE employee_id = ?";
        }
        else if(choice == 10)
        {
            UPDATE_QUERY = "UPDATE firmms.employees SET dateofstart = ? WHERE employee_id = ?";
        }

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, "root", "12345");
             PreparedStatement updateStatement = connection.prepareStatement(UPDATE_QUERY))
        {
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

                            updateStatement.setString(1, username);
                            updateStatement.setInt(2, employeeID);

                            int rowsUpdated = updateStatement.executeUpdate();

                            if(rowsUpdated > 0)
                            {
                                if(employeeID == getEmployeeID())
                                {
                                    setUsername(username);
                                }
                                displayProfileFromDatabase(employeeID);
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

                            updateStatement.setString(1, password);
                            updateStatement.setInt(2, employeeID);

                            int rowsUpdated = updateStatement.executeUpdate();

                            if(rowsUpdated > 0)
                            {
                                if(employeeID == getEmployeeID())
                                {
                                    setPassword(password);
                                }
                                displayProfileFromDatabase(employeeID);
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

                    if(presentEmployeeID != employeeID)
                    {
                        final String MANAGER_CHECK_QUERY = "SELECT role FROM firmms.employees WHERE employee_id = ?";

                        try(PreparedStatement managerCheckStatement = connection.prepareStatement(MANAGER_CHECK_QUERY))
                        {
                            managerCheckStatement.setInt(1, employeeID);

                            try(ResultSet managerCheckResultSet = managerCheckStatement.executeQuery())
                            {
                                if (managerCheckResultSet.next() && managerCheckResultSet.getInt(1) > 0)
                                {
                                    int chosenEmployeesRole = managerCheckResultSet.getInt("role");

                                    if(chosenEmployeesRole == 1)
                                    {
                                        System.out.println("\nYou have no authority to make this change!\n");
                                    }
                                    else
                                    {
                                        final String CHECK_QUERY = "SELECT COUNT(*) FROM firmms.roles WHERE role_id = ?";

                                        try(PreparedStatement checkStatement = connection.prepareStatement(CHECK_QUERY))
                                        {
                                            do
                                            {
                                                Manager.displayAllRoles();

                                                System.out.print("Please enter the new role: ");
                                                String roleStr = scanner.nextLine();

                                                if(inputValidation.integerValidation(roleStr))
                                                {
                                                    int role = Integer.parseInt(roleStr);

                                                    checkStatement.setInt(1, role);

                                                    try (ResultSet checkResultSet = checkStatement.executeQuery())
                                                    {
                                                        if (checkResultSet.next() && checkResultSet.getInt(1) > 0)
                                                        {
                                                            updateStatement.setInt(1, role);
                                                            updateStatement.setInt(2, employeeID);

                                                            int rowsUpdated = updateStatement.executeUpdate();

                                                            if(rowsUpdated > 0)
                                                            {
                                                                if(employeeID == getEmployeeID())
                                                                {
                                                                    final String ROLE_NAME_QUERY = "SELECT role_name FROM firmms.roles WHERE role_id = ?";

                                                                    try(PreparedStatement roleNameStatement = connection.prepareStatement(ROLE_NAME_QUERY))
                                                                    {
                                                                        roleNameStatement.setInt(1, role);
                                                                        try(ResultSet roleNameResultSet = roleNameStatement.executeQuery())
                                                                        {
                                                                            if(roleNameResultSet.next())
                                                                            {
                                                                                String roleName = roleNameResultSet.getString("role_name"); // ERROR IN HERE
                                                                                setRole(roleName);
                                                                                displayProfileFromDatabase(employeeID);
                                                                                System.out.println("\nRole changed successfully!\n");
                                                                                break;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                else
                                                                {
                                                                    System.out.println("\nRole changed successfully!\n");
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                        else
                                                        {
                                                            System.out.println("\nThere is no such role ID in the system!\n");
                                                        }
                                                    }
                                                }
                                            } while(true);
                                        }
                                    }
                                }
                            }
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

                            updateStatement.setString(1, name);
                            updateStatement.setInt(2, employeeID);

                            int rowsUpdated = updateStatement.executeUpdate();

                            if(rowsUpdated > 0)
                            {
                                if(employeeID == getEmployeeID())
                                {
                                    setName(name);
                                }
                                displayProfileFromDatabase(employeeID);
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

                            updateStatement.setString(1, surname);
                            updateStatement.setInt(2, employeeID);

                            int rowsUpdated = updateStatement.executeUpdate();

                            if(rowsUpdated > 0)
                            {
                                if(employeeID == getEmployeeID())
                                {
                                    setSurname(surname);
                                }
                                displayProfileFromDatabase(employeeID);
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
            else if(choice == 7) // Phone Number update, everybody can edit
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

                        updateStatement.setString(1, phoneNo);
                        updateStatement.setInt(2, employeeID);

                        int rowsUpdated = updateStatement.executeUpdate();

                        if(rowsUpdated > 0)
                        {
                            if(employeeID == getEmployeeID())
                            {
                                setPhoneNo(phoneNo);
                            }
                            displayProfileFromDatabase(employeeID);
                            System.out.println("\nName changed successfully!\n");
                            break;
                        }
                    }
                } while(true);
            }
            else if(choice == 8) // E-mail update, everybody can edit
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

                        updateStatement.setString(1, email);
                        updateStatement.setInt(2, employeeID);

                        int rowsUpdated = updateStatement.executeUpdate();

                        if(rowsUpdated > 0)
                        {
                            if(employeeID == getEmployeeID())
                            {
                                setEmail(email);
                            }
                            displayProfileFromDatabase(employeeID);
                            System.out.println("\nName changed successfully!\n");
                            break;
                        }
                    }
                } while(true);
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
                            updateStatement.setDate(1, dob);
                            updateStatement.setInt(2, employeeID);

                            int rowsUpdated = updateStatement.executeUpdate();

                            if(rowsUpdated > 0)
                            {
                                if(employeeID == getEmployeeID())
                                {
                                    setDateOfBirth(dob);
                                }
                                displayProfileFromDatabase(employeeID);
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
                            updateStatement.setDate(1, dos);
                            updateStatement.setInt(2, employeeID);

                            int rowsUpdated = updateStatement.executeUpdate();

                            if(rowsUpdated > 0)
                            {
                                if(employeeID == getEmployeeID())
                                {
                                    setDateOfStart(dos);
                                }
                                displayProfileFromDatabase(employeeID);
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
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
