import java.sql.*;
import java.util.Objects;

public class Authentication
{
    public static Employee login(String username, String password)
    {
        final String DATABASE_URL = "jdbc:mysql://localhost:3306/firmms?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "12345";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD))
        {
            String sql = "SELECT e.username, e.password, e.name, e.surname, e.phone_no, e.email, e.dateOfBirth, e.dateOfStart, r.role_name " +
                    "FROM employees e " +
                    "JOIN roles r ON e.role = r.role_id " +
                    "WHERE e.username = ? AND e.password = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next())
            {
                username = rs.getString("username");
                password = rs.getString("password");
                String role = rs.getString("role_name");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String phoneNo = rs.getString("phone_no");
                String email = rs.getString("email");
                Date dateOfBirth = rs.getDate("dateOfBirth");
                Date dateOfStart = rs.getDate("dateOfStart");

                if (Objects.equals(role, "Manager"))
                {
                    return new Manager(username, password, role, name, surname, phoneNo, email, dateOfBirth, dateOfStart);
                }
                else
                {
                    return new RegularEmployee(username, password, role, name, surname, phoneNo, email, dateOfBirth, dateOfStart);
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
