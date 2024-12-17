public class InputValidator {
    public static boolean validateEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        return true;
    }

    public static void processInput(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        if (input.length() > 50) {
            throw new IllegalArgumentException("Input is too long");
        }

        if (!input.matches("^[a-zA-Z0-9 ]*$")) {
            throw new IllegalArgumentException("Input contains invalid characters");
        }

        System.out.println("Processed input: " + input);
    }
}
