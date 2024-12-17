import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;

public class CryptoUtilsFuzzTest {

    // Define the stopping condition prefix
    private static final String STOPPING_HASH_PREFIX = "00000";

    @FuzzTest
    public void fuzzerTestSHA256Hashing(FuzzedDataProvider data) {
        // Generate a random input string for fuzzing
        String input = data.consumeString(1024); // Generate up to 1024 characters

        try {
            // Call the CryptoUtils.hashSHA256 method
            String hashOutput = CryptoUtils.hashSHA256(input);

            // Print the input and output for debugging purposes
            System.out.printf("Input: %s, SHA-256 Hash: %s%n", input, hashOutput);

            // Check if the hash starts with the stopping condition prefix
            if (hashOutput.startsWith(STOPPING_HASH_PREFIX)) {
                // Log the input that triggered the stopping condition
                System.err.printf(
                        "Stopping condition met! Input: %s, SHA-256 Hash: %s%n",
                        input, hashOutput
                );
                // Throw an error to halt fuzzing
                throw new Error("Stopping condition met: Hash starts with " + STOPPING_HASH_PREFIX);
            }

            // Optionally, add a basic check to ensure the hash length is always correct
            if (hashOutput.length() != 64) { // SHA-256 hash length in hex is always 64
                throw new AssertionError(
                        "Unexpected hash length: " + hashOutput.length() + " for input: " + input
                );
            }
        } catch (Exception e) {
            // Log unexpected exceptions
            System.err.println("Exception occurred: " + e.getMessage());
            throw e; // Rethrow for Jazzer to detect the crash
        }
    }
}
