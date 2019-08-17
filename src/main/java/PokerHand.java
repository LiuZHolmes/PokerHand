import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PokerHand {
    static String game(String cards) {
        List<Hand> hands = getHandByString(cards);
        hands.forEach(Hand::calHandPower);
        return getWinner(compareHand(hands.get(0), hands.get(1)));
    }

    public static Card getCardByString(String given) {
        String number = given.substring(0, 1);
        String type = given.substring(1, 2);
        return new Card(getCardTypeByString(type), getCardNumberByString(number));
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

    public static Hand getAHandBy5Cards(List<Card> cards) {
        if (cards.size() == 5)
            return new Hand(cards);
        return null;
    }

    public static List<Hand> getHandByString(String given) {
        List<Card> cards = Arrays.stream(given.split(" "))
                .map(PokerHand::getCardByString).collect(Collectors.toList());
        List<Hand> hands = new ArrayList<>();
        int begin = 0;
        while (begin < cards.size()) {
            final int diff = cards.size() - begin;
            hands.add(new Hand(cards.subList(begin, diff > 5 ? begin + 5 : begin + diff)));
            begin += 5;
        }
        return hands;
    }

    public static int compareLevel(PowerLevel level, PowerLevel secondLevel) {
        return level.compareTo(secondLevel);
    }

    public static int compareAce(Card card, Card secondCard) {
        return compareCard(card, secondCard);
    }

    public static int compareCard(Card card, Card secondCard) {
        return card.getNumber().compareTo(secondCard.getNumber());
    }

    public static int compareRemainHand(Hand remainHand, Hand secondRemainHand) {
        for (int i = remainHand.size() - 1; i >= 0; i--) {
            final int result = compareCard(remainHand.getCards().get(i), secondRemainHand.getCards().get(i));
            if (result != 0) return result;
        }
        return 0;
    }

    public static int compareHand(Hand hand, Hand secondHand) {
        final int levelResult = compareLevel(hand.getPower().getLevel(), secondHand.getPower().getLevel());
        if (levelResult != 0) return levelResult;
        final int aceResult = compareAce(hand.getPower().getAce(), secondHand.getPower().getAce());
        if (aceResult != 0) return aceResult;
        if (hand.getPower().getLevel() == PowerLevel.TWOPAIRS) {
            final int secondAceResult = compareAce(hand.getPower().getSecondAce(), secondHand.getPower().getSecondAce());
            if (secondAceResult != 0) return secondAceResult;
        }
        return compareRemainHand(hand.getRemainHand(), secondHand.getRemainHand());
    }

    public static String getWinner(int given) {
        if (given > 0) return Winner.PLAYER_1_WINS;
        else if (given < 0) return Winner.PLAYER_2_WINS;
        return Winner.TIED;
    }
}
