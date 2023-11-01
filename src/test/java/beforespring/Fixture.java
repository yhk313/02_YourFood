package beforespring;

import java.util.Random;
import net.bytebuddy.utility.RandomString;

public class Fixture {
    static public final Random random = new Random();

    static public String randString() {
        return RandomString.make();
    }


    /**
     * @return 1 ~ 1,000,000 사이의 Long
     */
    static public Long randomPositiveLong() {
        return random.nextLong(0, 1000000);
    }

}
