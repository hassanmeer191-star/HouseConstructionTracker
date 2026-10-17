import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:postgresql://ep-dawn-field-zaak1uip-pooler.c-2.eu-west-2.aws.neon.tech/neondb?user=neondb_owner&password=npg_3UosM1DwNTvj&sslmode=require&channelBinding=require";

    private static final String USER = "neondb_owner";
    private static final String PASSWORD = "npg_3UosM1DwNTvj";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}