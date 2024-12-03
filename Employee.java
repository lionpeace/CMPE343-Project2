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
