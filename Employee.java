import java.sql.*;
import java.util.*;
import java.util.Date;

/**
* This method represents an employee in the System.
* This abstract class acts as a template for creating employee objects.
* It holds basic information about an employee, such as their ID, username, password, role,
* first name, last name, contact information, and employment dates.
* The class also includes a flag to indicate whether the employee is a new user.
* Subclasses can extend this class to represent specific types of employees with attributes.
*/
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
/**
 * Constructs an Employee object with the provided details.
 * 
 * @param employeeID The unique identifier for the employee.
 * @param username The username of the employee, used for login.
 * @param password The password of the employee, used for authentication.
 * @param role The role of the employee (e.g., Manager, Staff).
 * @param name The first name of the employee.
 * @param surname The surname of the employee.
 * @param phoneNo The phone number of the employee.
 * @param email The email address of the employee.
 * @param dateOfBirth The birth date of the employee.
 * @param dateOfStart The start date of the employee's employment.
 * @param newUser A flag indicating whether the employee is a new user.
 */
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
/**
* This method is the unique identifier of the Employee.
* This ID is used to distinguish employees in the system.
*/
    public int getEmployeeID(){
        return employeeID;
    }
/**
 * Gets the username of the employee.
 * @return The username of the employee.
 */
    public String getUsername() {
        return username;
    }
/**
 * Gets the password of the employee.
 * @return The password of the employee.
 */
    public String getPassword() {
        return password;
    }

 /**
 * Gets the role of the employee.
 * @return The role of the employee (e.g., Manager, Staff).
 */
    public String getRole() {
        return role;
    }

/**
 * Gets the first name of the employee.
 * @return The first name of the employee.
 */
    public String getName() {
        return name;
    }

/**
 * Gets the surname of the employee.
 * @return The surname of the employee.
 */
    public String getSurname() {
        return surname;
    }

/**
 * Gets the phone number of the employee.
 * @return The phone number of the employee.
 */
    public String getPhoneNo() {
        return phoneNo;
    }

/**
 * Gets the email address of the employee.
 * @return The email address of the employee.
 */
    public String getEmail() {
        return email;
    }

/**
 * Gets the birth date of the employee.
 * @return The birth date of the employee.
 */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

/**
 * Gets the start date of the employee's employment.
 * 
 * @return The start date of the employee's employment.
 */
    public Date getDateOfStart() {
        return dateOfStart;
    }

/**
 * Gets the flag indicating whether the employee is a new user.
 * 
 * @return True if the employee is a new user, otherwise false.
 */
    public Boolean getNewUser() {
        return newUser;
    }

/**
 * Sets the employee ID.
 * 
 * @param employeeID The unique identifier to set for the employee.
 */
    public void setEmployeeID(int employeeID){
        this.employeeID = employeeID;
    }

/**
 * Sets the username of the employee.
 * 
 * @param username The username to set for the employee.
 */
    public void setUsername(String username) {
        this.username = username;
    }

/**
 * Sets the password of the employee.
 * 
 * @param password The password to set for the employee.
 */
    public void setPassword(String password) {
        this.password = password;
    }

/**
 * Sets the role of the employee.
 * 
 * @param role The role to set for the employee (e.g., Manager, Staff).
 */
    public void setRole(String role) {
        this.role = role;
    }

/**
 * Sets the first name of the employee.
 * 
 * @param name The first name to set for the employee.
 */
    public void setName(String name) {
        this.name = name;
    }

/**
 * Sets the surname of the employee.
 * 
 * @param surname The surname to set for the employee.
 */
    public void setSurname(String surname) {
        this.surname = surname;
    }

/**
 * Sets the phone number of the employee.
 * 
 * @param phoneNo The phone number to set for the employee.
 */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

/**
 * Sets the email address of the employee.
 * 
 * @param email The email address to set for the employee.
 */
    public void setEmail(String email) {
        this.email = email;
    }

/**
 * Sets the birth date of the employee.
 * 
 * @param dateOfBirth The birth date to set for the employee.
 */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

/**
 * Sets the start date of the employee's employment.
 * 
 * @param dateOfStart The start date to set for the employee's employment.
 */
    public void setDateOfStart(Date dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

/**
 * Sets the flag indicating whether the employee is a new user.
 * 
 * @param newUser True if the employee is a new user, otherwise false.
 */
    public void setNewUser(Boolean newUser) {
        this.newUser = newUser;
    }

    public abstract void updateProfile(Scanner scanner);

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
}
