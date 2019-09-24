package constant;

import java.util.Arrays;

public enum CardType {
    DIAMOND("D"),
    CLUB("C"),
    HEART("H"),
    SPAED("S");

    String identify;

    CardType(String identify) {
        this.identify = identify;
    }

    public static CardType getByString(String identify) {
        return Arrays.stream(values()).filter(x -> x.identify.equals(identify)).findFirst().orElse(null);
    }

}
