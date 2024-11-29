import java.util.Date;

public class RegularEmployee extends Employee
{
    public RegularEmployee(String username, String password, String role, String name, String surname, String phoneNo, String email, Date dateOfBirth, Date dateOfStart)
    {
        super(username, password, role, name, surname, phoneNo, email, dateOfBirth, dateOfStart);
    }

    public void updateProfile()
    {
        // Allow employees to update phone number, email, and password
    }

    public void regularEmployeeMenu(Employee employee)
    {
        System.out.println("Welcome, "+ employee.getName() + " " + employee.getSurname());
        System.out.println("Your Role:  "+ employee.getRole());
    }
}
