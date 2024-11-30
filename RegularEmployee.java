import java.util.Date;
import java.util.Scanner;

public class RegularEmployee extends Employee
{
    public RegularEmployee(int employeeID, String username, String password, String role, String name, String surname, String phoneNo, String email, Date dateOfBirth, Date dateOfStart, Boolean newUser)
    {
        super(employeeID, username, password, role, name, surname, phoneNo, email, dateOfBirth, dateOfStart, newUser);
    }

    @Override
    public void updateProfile(Scanner scanner)
    {

    }

    public void regularEmployeeMenu(Employee employee)
    {
        System.out.println("Welcome, "+ employee.getName() + " " + employee.getSurname());
        System.out.println("Your Role:  "+ employee.getRole());
    }
}
