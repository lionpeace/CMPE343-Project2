import java.sql.*;
import java.util.*;
import java.util.Date;

public abstract class Employee
{
    private String username;
    private String password;
    private String role;
    private String name;
    private String surname;
    private String phoneNo;
    private String email;
    private Date dateOfBirth;
    private Date dateOfStart;

    public Employee(String username, String password, String role, String name, String surname, String phoneNo, String email, Date dateOfBirth, Date dateOfStart)
    {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.phoneNo = phoneNo;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.dateOfStart = dateOfStart;
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

    public abstract void updateProfile();

    public void displayProfile()
    {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Role: " + role);
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("PhoneNo: " + phoneNo);
        System.out.println("Email: " + email);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Date of Start: " + dateOfStart);
    }

    public void updateProfile(String password, String phoneNo, String email)
    {
        //
    }
}
