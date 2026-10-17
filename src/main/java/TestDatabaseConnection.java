import java.sql.Connection;
import java.sql.SQLException;

public class TestDatabaseConnection {

    public static void main(String[] args) {

        try (Connection connection =
                DatabaseConnection.getConnection()) {

            System.out.println("Neon connected successfully!");

        } catch (SQLException e) {
            System.out.println("Connection failed.");
            System.out.println(e.getMessage());
        }
    }
}