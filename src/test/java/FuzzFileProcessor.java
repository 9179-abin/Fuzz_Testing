import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;

import java.io.IOException;

public class FuzzFileProcessor {

    @FuzzTest
    public void fuzzerTestJSONParsing(FuzzedDataProvider data) {
        // Generate a random string to simulate input
        String randomString = data.consumeString(50);

        try {
            // Simulate calling FileProcessor's method
            String jsonOutput = FileProcessor.serializeToJson(randomString);
            System.out.println("Serialized JSON: " + jsonOutput);
        } catch (RuntimeException e) {
            // Catch exceptions like SerializationError
            System.err.println("Exception during serialization: " + e.getMessage());
            throw e; // Allow Jazzer to log the crash
        }
    }

    @FuzzTest
    public void fuzzerTestReadFile(FuzzedDataProvider data) {
        // Generate a random file path
        String randomPath = data.consumeString(100); // Random path string

        try {
            // Simulate calling FileProcessor's readFile
            String content = FileProcessor.readFile(randomPath);
            System.out.println("File Content: " + content);
        } catch (IOException e) {
            // Handle expected exception for invalid files
            System.err.println("Failed to read file: " + e.getMessage());
        } catch (Exception e) {
            // Any unexpected errors
            System.err.println("Unexpected error: " + e.getMessage());
            throw e;
        }
    }
}
