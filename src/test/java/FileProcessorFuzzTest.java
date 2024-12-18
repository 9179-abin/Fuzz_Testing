import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;

import java.io.IOException;
import java.text.ParseException;

public class FileProcessorFuzzTest {

    private static final String CRITICAL_JSON_INPUT = "STOP_JSON";
    private static final String CRITICAL_FILE_PATH = "critical/path/stop";

    // Fuzz test for JSON serialization with a stopping condition
    @FuzzTest
    public void fuzzerTestJSONParsing(FuzzedDataProvider data) {
        // Generate a random string input
        String input = data.consumeString(100); // Up to 100 characters

        // Stopping condition: Input matches a critical string
        if (CRITICAL_JSON_INPUT.equals(input)) {
            System.err.println("Stopping condition met: Critical JSON input detected - " + input);
            throw new Error("Stopping condition met: Critical JSON input found.");
        }

        try {
            // Test the serialization process
            String jsonOutput = FileProcessor.serializeToJson(input);
            System.out.println("Serialized JSON: " + jsonOutput);
        } catch (RuntimeException e) {
            // Log serialization errors
            System.err.println("Serialization error: " + e.getMessage());
            throw e; // Allow Jazzer to log the crash
        }
    }

    // Fuzz test for file reading with a stopping condition
    @FuzzTest
    public void fuzzerTestReadFile(FuzzedDataProvider data) {
        // Generate a random file path
        String filePath = data.consumeString(150); // Random path string

        // Stopping condition: File path matches a critical string
        if (CRITICAL_FILE_PATH.equals(filePath)) {
            System.err.println("Stopping condition met: Critical file path detected - " + filePath);
            throw new Error("Stopping condition met: Critical file path found.");
        }

        try {
            // Attempt to read the file
            String content = FileProcessor.readFile(filePath);
            System.out.println("File Content: " + content);

            // Optional: Stop fuzzing if file content contains critical data
            if (content.contains("CRITICAL_CONTENT")) {
                System.err.println("Stopping condition met: Critical file content detected.");
                throw new Error("Stopping condition met: Critical content found in file.");
            }
        } catch (IOException e) {
            // Log file read errors, which are expected for invalid paths
            System.err.println("Failed to read file: " + e.getMessage());
        } catch (Exception e) {
            // Log unexpected errors
            System.err.println("Unexpected error: " + e.getMessage());
            throw e; // Allow Jazzer to detect this as a crash
        }
    }
}
