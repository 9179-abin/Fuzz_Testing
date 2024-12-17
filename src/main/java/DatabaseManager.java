import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager(String dbUrl) throws SQLException {
        connection = DriverManager.getConnection(dbUrl);
        initializeDatabase(); // Ensure the table exists for fuzzing
    }

    private void initializeDatabase() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT, email TEXT)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void insertUser(String name, String email) throws SQLException {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();
        }
    }

    public List<String> fetchData() throws SQLException {
        List<String> users = new ArrayList<>();
        String sql = "SELECT name, email FROM users";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String user = "Name: " + rs.getString("name") +
                        ", Email: " + rs.getString("email");
                users.add(user);
            }
        }
        return users;
    }

    // New method for fuzzing target
    public boolean processFuzzedInput(String name, String email) throws SQLException {
        insertUser(name, email);
        List<String> users = fetchData();
        for (String user : users) {
            if (user.contains("Admin") && user.contains("admin@example.com")) {
                // Stopping condition met
                return true;
            }
        }
        return false;
    }
}
