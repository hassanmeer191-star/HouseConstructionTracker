import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Retieve_user_info {

public User authenticate(String email, String password,
String selectedRole) throws SQLException {

String sql = "SELECT user_id, full_name, email, role, password_hash "
+ "FROM users "
+ "WHERE email = ? AND is_active = TRUE";

try (Connection connection = DatabaseConnection.getConnection();
PreparedStatement statement =
connection.prepareStatement(sql)) {

statement.setString(1, email);

try (ResultSet result = statement.executeQuery()) {

if (result.next()) {

String savedHash = result.getString("password_hash");
String savedRole = result.getString("role");

boolean passwordMatches =
PasswordUtil.checkPassword(password, savedHash);

boolean roleMatches =
selectedRole.equalsIgnoreCase(savedRole);

if (passwordMatches && roleMatches) {
return new User(
result.getInt("user_id"),
result.getString("full_name"),
result.getString("email"),
savedRole
);
}
}
}
}

return null;
}
}
