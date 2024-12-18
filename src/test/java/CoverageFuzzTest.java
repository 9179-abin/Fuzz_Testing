import com.code_intelligence.jazzer.junit.FuzzTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.provider.ValueSource;

public class CoverageFuzzTest {
    private static long invocations = 0;

    // fuzz target is invoked with "emptyInput" (value "0"), "ValueSource" seeds (values "1", "2",
    // "3")
    @ValueSource(longs = {1, 2, 3})
    @FuzzTest(maxDuration = "5s")
    public void coverage(long input) {
        invocations++;
        if (input < 0 || input > 5) {
            throw new IllegalStateException("Unexpected input value provided");
        }
    }

    @AfterAll
    public static void checkInvocations() {
        if (invocations != 6) {
            throw new IllegalStateException("Invalid number of fuzz target invocations: " + invocations);
        }
    }
}