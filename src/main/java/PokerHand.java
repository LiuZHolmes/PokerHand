import java.util.ArrayList;
import java.util.List;

public class PokerHand {
    static String game(List<String> cards) {
        List<String> HandOfPlayer1 = new ArrayList<String>();
        List<String> HandOfPlayer2 = new ArrayList<String>();
        for (int i = 0; i < cards.size(); i++) {
            if (i < 5)
                HandOfPlayer1.add(cards.get(i));
            else HandOfPlayer2.add(cards.get(i));
        }
        int result = HandOfPlayer1.get(HandOfPlayer1.size() - 1).charAt(0) >
                HandOfPlayer2.get(HandOfPlayer1.size() - 1).charAt(0) ? 1 : 2;
        return "Player " + result + " wins";
    }

    public static Card getCardByString(String given) {
        String number = given.substring(0, 1);
        String type = given.substring(1, 2);
        return new Card(getCardTypeByString(type),getCardNumberByString(number));
    }

    public static CardNumber getCardNumberByString(String given) {
        if ("2".equals(given)) return CardNumber.TWO;
        if ("3".equals(given)) return CardNumber.THREE;
        if ("4".equals(given)) return CardNumber.FOUR;
        if ("5".equals(given)) return CardNumber.FIVE;
        if ("6".equals(given)) return CardNumber.SIX;
        if ("7".equals(given)) return CardNumber.SEVEN;
        if ("8".equals(given)) return CardNumber.EIGHT;
        if ("9".equals(given)) return CardNumber.NINE;
        if ("T".equals(given)) return CardNumber.TEN;
        if ("J".equals(given)) return CardNumber.JACK;
        if ("Q".equals(given)) return CardNumber.QUEEN;
        if ("K".equals(given)) return CardNumber.KING;
        if ("A".equals(given)) return CardNumber.ACE;
        return null;
    }

    public static CardType getCardTypeByString(String given) {
        if ("D".equals(given)) return CardType.DIAMOND;
        if ("S".equals(given)) return CardType.SPAED;
        if ("H".equals(given)) return CardType.HEART;
        if ("C".equals(given)) return CardType.CLUB;
        return null;
    }
}
