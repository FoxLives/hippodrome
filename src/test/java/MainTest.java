import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

class MainTest {

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @Disabled("Timeout отключён.")
    void main() throws Exception {
        Main.main(new String[]{});
    }
}