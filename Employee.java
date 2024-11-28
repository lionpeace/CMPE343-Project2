import java.util.*;

public abstract class Employee {
    private String username;
    private String password;
    private int role;
    private String name;
    private String surname;
    private String phoneNo;
    private String email;
    private Date dateOfBirth;
    private Date dateOfStart;

    public abstract boolean login(String username, String password);
    public abstract void updateProfile();
    public void displayProfile()
    {

    }
    public void updateProfile(String password, String phoneNo, String email)
    {

    }
}
