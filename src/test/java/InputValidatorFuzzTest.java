import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;

public class InputValidatorFuzzTest {

    // Define critical stopping conditions
    private static final String CRITICAL_EMAIL = "admin@example.com";
    private static final String CRITICAL_PASSWORD = "password123!";
    private static final String CRITICAL_INPUT = "DROP TABLE users";
    private static final String STOPPING_PASSWORD = "CriticalPass123!";
    private static final int STOPPING_LENGTH = 4096;
    private static final String CRITICAL_PATTERN = "CriticalLongPassword123!";

    // Fuzz test for general input validation with a stopping condition
    @FuzzTest
    public void fuzzerTestInputValidation(FuzzedDataProvider data) {
        try {
            // Generate a random input string
            String input = data.consumeString(100);

            // Stop fuzzing if input matches a critical pattern
            if (input.contains(CRITICAL_INPUT)) {
                throw new Error("Stopping condition met: Critical input detected - " + input);
            }

            // Call the method to test
            InputValidator.processInput(input);
        } catch (IllegalArgumentException e) {
            // Expected behavior for invalid inputs
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

    // Fuzz test for email validation with a stopping condition
    @FuzzTest
    public void fuzzerTestEmailValidation(FuzzedDataProvider data) {
        try {
            String email = data.consumeString(50);

            // Stop fuzzing if email matches a critical pattern
            if (email.equalsIgnoreCase(CRITICAL_EMAIL)) {
                throw new Error("Stopping condition met: Critical email detected - " + email);
            }

            InputValidator.validateEmail(email);
        } catch (Exception e) {
            throw e;
        }
    }

    // Fuzz test specifically for password validation with a stopping condition
    @FuzzTest
    public void fuzzTestPasswordValidation(FuzzedDataProvider data) {
        String password = data.consumeRemainingAsString();
        try {
            // Stop fuzzing if password matches a critical pattern
            if (password.equals(CRITICAL_PASSWORD)) {
                throw new Error("Stopping condition met: Critical password detected - " + password);
            }

            InputValidator.validatePassword(password);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid password input: " + password);
        }
    }

    @FuzzTest
    public void fuzzTestPasswordEdgeCases(FuzzedDataProvider data) {
        String password = data.consumeRemainingAsString();

        // Stop fuzzing if password matches a predefined stopping condition
        if (password.equals(STOPPING_PASSWORD)) {
            System.err.println("Stopping condition met: Critical password detected - " + password);
            throw new Error("Stopping condition met: Critical password found.");
        }

        // Boundary condition: Short passwords
        if (password.length() < 8) {
            try {
                InputValidator.validatePassword(password);
            } catch (IllegalArgumentException e) {
                // Log only once per type of issue to avoid flooding output
                System.err.println("Short password detected: " + password);
            }
        } else {
            try {
                // Process and validate long passwords
                InputValidator.validatePassword(password);
            } catch (Exception e) {
                // Critical unexpected behavior
                throw new RuntimeException("Unexpected exception for valid password: " + password, e);
            }
        }
    }

    // Fuzz test for special characters in passwords with a stopping condition
    @FuzzTest
    public void fuzzTestPasswordSpecialCharacters(FuzzedDataProvider data) {
        String password = data.consumeString(15);

        if (password.contains("!@#$%^&*")) {
            throw new Error("Stopping condition met: Special character sequence detected - " + password);
        }

        try {
            InputValidator.validatePassword(password);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid password with special characters: " + password);
        }
    }


    // Test case for very long password inputs with a stopping condition
    @FuzzTest
    public void fuzzTestLongPasswords(FuzzedDataProvider data) {
        // Generate a long string (up to 4096 characters)
        String password = data.consumeString(STOPPING_LENGTH);

        // Stop fuzzing if a critical input pattern is detected
        if (password.equals(CRITICAL_PATTERN)) {
            System.err.println("Stopping condition met: Critical long password detected - " + password);
            throw new Error("Stopping condition met: Critical pattern found.");
        }

        // Stop fuzzing if password length exceeds 4000
        if (password.length() > 4000) {
            System.err.println("Stopping condition met: Very long password detected - Length: " + password.length());
            throw new Error("Stopping condition met: Password length exceeded threshold.");
        }

        try {
            // Validate the password
            InputValidator.validatePassword(password);
        } catch (IllegalArgumentException e) {
            // Log only for invalid inputs
            System.err.println("Invalid long password (validation failed): Length: " + password.length());
        } catch (Exception e) {
            // Handle unexpected errors for debugging
            throw new RuntimeException("Unexpected exception for password input: " + password, e);
        }
    }
}
