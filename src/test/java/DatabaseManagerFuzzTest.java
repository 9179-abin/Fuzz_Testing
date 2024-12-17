import com.code_intelligence.jazzer.junit.FuzzTest;

public class DatabaseManagerFuzzTest {
    private static final String DB_URL = "jdbc:sqlite::memory:"; // In-memory SQLite database for fuzzing

    @FuzzTest
    void fuzzProcessFuzzedInput(byte[] data) throws Exception {
        // Convert fuzzed data into a string and split into name and email
        String input = new String(data);
        String[] parts = input.split(",", 2);
        if (parts.length < 2) return; // Ensure valid input for name and email

        String name = parts[0].trim();
        String email = parts[1].trim();

        // Initialize the database manager
        DatabaseManager dbManager = new DatabaseManager(DB_URL);
//        System.out.println("Name: " + name);
//        System.out.println("Email: " + email);
        // Process fuzzed input and check for stopping condition
        if (dbManager.processFuzzedInput(name, email)) {
            throw new Error("Admin user found! Stopping fuzzing.");
        }
    }
}
