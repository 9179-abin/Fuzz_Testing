import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;

public class FuzzNativLib {

    // Fuzz test for the native 'add' method
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

            // Print the result (optional: for debugging)
            System.out.printf("Fuzzed Input: a = %d, b = %d, Result = %d%n", a, b, result);

            // Optionally, add checks if the behavior is known
            // For example, native add(a, b) is expected to behave like a + b
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
            throw e; // Rethrow to let Jazzer handle it
        }
    }
}
