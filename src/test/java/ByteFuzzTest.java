import static org.junit.jupiter.api.Assertions.fail;

import com.code_intelligence.jazzer.junit.FuzzTest;

class ByteFuzzTest {
    @FuzzTest
    void byteFuzz(byte[] data) {
        if (data.length < 1) {
            return;
        }

        if (data[0] % 2 == 0) {
            fail();
        }
    }
}