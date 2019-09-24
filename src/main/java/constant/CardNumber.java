package constant;

import java.util.Arrays;

public enum CardNumber {
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("T"),
    JACK("J"),
    QUEEN("Q"),
    KING("K"),
    ACE("A");

    String identify;

    CardNumber(String identify) {
        this.identify = identify;
    }

    public static CardNumber getByString(String identify) {
        return Arrays.stream(values()).filter(x -> x.identify.equals(identify)).findFirst().orElse(null);
    }
}
