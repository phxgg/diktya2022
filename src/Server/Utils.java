package Server;

import java.util.Random;

public class Utils {
    public static Integer get6DigitRandomInteger() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return Integer.valueOf(String.format("%06d", number));
    }
}
