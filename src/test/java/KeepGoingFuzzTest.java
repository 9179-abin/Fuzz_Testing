import com.code_intelligence.jazzer.junit.FuzzTest;

public class KeepGoingFuzzTest {
    private static int counter = 0;

    @FuzzTest
    public void keepGoingFuzzTest(byte[] ignored) {
        counter++;
        if (counter == 1) {
            throw new IllegalArgumentException("error1");
        }
        if (counter == 2) {
            throw new IllegalArgumentException("error2");
        }
    }
}