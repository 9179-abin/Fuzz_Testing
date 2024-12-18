import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;

public class NativLibFuzzTest {

    private static final int CRITICAL_SUM = 9999; // Define the critical stopping condition result

    // Fuzz test for the native 'add' method with stopping condition
    @FuzzTest
    public void fuzzerTestAdd(FuzzedDataProvider data) {
        // Create an instance of NativLib
        NativLib lib = new NativLib();

        // Generate two random integers for fuzzing
        int a = data.consumeInt(); // Random integer input
        int b = data.consumeInt();

        try {
            // Call the native 'add' method with fuzzed inputs
            int result = lib.add(a, b);

            // Print the fuzzed inputs and result (for debugging purposes)
            System.out.printf("Fuzzed Input: a = %d, b = %d, Result = %d%n", a, b, result);

            // Stop fuzzing if the result matches the critical stopping condition
            if (result == CRITICAL_SUM) {
                System.err.printf("Stopping condition met: Critical sum detected (%d + %d = %d)%n", a, b, result);
                throw new Error("Stopping condition met: Critical result detected.");
            }

            // Optionally add a check to validate expected behavior
            int expected = a + b;
            if (result != expected) {
                System.err.printf("Mismatch: Expected %d but got %d%n", expected, result);
                throw new AssertionError("Native add method returned an unexpected result.");
            }

        } catch (UnsatisfiedLinkError e) {
            // Handle the case where the native library is not linked properly
            System.err.println("Native library not found: " + e.getMessage());
        } catch (Exception e) {
            // Catch any unexpected exceptions
            System.err.println("Exception occurred: " + e.getMessage());
            throw e; // Rethrow to let Jazzer log and detect the crash
        }
    }
}
