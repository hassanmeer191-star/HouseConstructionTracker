
import java.sql.SQLException;

public class Test_user_search {

public static void main(String[] args) {

Retieve_user_info userDAO = new Retieve_user_info();

try {
User user = userDAO.authenticate(
"supervisor@test.com",
"Test12345",
"Supervisor"
);

if (user != null) {
System.out.println("Login successful!");
System.out.println("Name: " + user.getFullName());
System.out.println("Email: " + user.getEmail());
System.out.println("Role: " + user.getRole());
} else {
System.out.println(
    "Login failed. Check email, password, and role."
);
}

} catch (SQLException e) {
System.out.println("Database error: " + e.getMessage());
}
}
}
