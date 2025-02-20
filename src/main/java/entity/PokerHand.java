package entity;

import constant.CardNumber;
import constant.CardType;
import constant.PowerLevel;
import constant.Winner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.ToIntBiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PokerHand {
    private PokerHand() {
    }

    static String game(String cards) {
        List<Hand> hands = getHandByString(cards);
        hands.forEach(Hand::calHandPower);
        return getWinner(compareHand(hands.get(0), hands.get(1)));
    }

    static Card getCardByString(String given) {
        String number = given.substring(0, 1);
        String type = given.substring(1, 2);
        return new Card(getCardTypeByString(type), getCardNumberByString(number));
    }

    static CardNumber getCardNumberByString(String identify) {
        return CardNumber.getByString(identify);
    }

    static CardType getCardTypeByString(String identify) {
        return CardType.getByString(identify);
    }

    static Hand getAHandBy5Cards(List<Card> cards) {
        if (cards.size() == 5)
            return new Hand(cards);
        return null;
    }

    static List<Hand> getHandByString(String given) {
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

    static int compareLevel(PowerLevel level, PowerLevel secondLevel) {
        return level.compareTo(secondLevel);
    }

    static int compareCard(Card card, Card secondCard) {
        return card.getNumber().compareTo(secondCard.getNumber());
    }

    static int compareRemainHand(Hand remainHand, Hand secondRemainHand) {
        return IntStream.rangeClosed(0, remainHand.size() - 1)
                .boxed()
                .sorted(Collections.reverseOrder())
                .map(x -> compareCard(remainHand.getCards().get(x), secondRemainHand.getCards().get(x)))
                .filter(x -> x != 0)
                .findFirst()
                .orElse(0);
    }

    private static ToIntBiFunction<Hand, Hand> compareHandLevel =
            (Hand hand, Hand secondHand) -> compareLevel(hand.getPower().getLevel(), secondHand.getPower().getLevel());

    private static ToIntBiFunction<Hand, Hand> compareHandAce =
            (Hand hand, Hand secondHand) -> compareCard(hand.getPower().getAce(), secondHand.getPower().getAce());

    private static ToIntBiFunction<Hand, Hand> compareHandSecondAce =
            (Hand hand, Hand secondHand) -> {
                if (hand.getPower().getLevel() == PowerLevel.TWOPAIRS && secondHand.getPower().getLevel() == PowerLevel.TWOPAIRS) {
                    return compareCard(hand.getPower().getSecondAce(), secondHand.getPower().getSecondAce());
                }
                return 0;
            };

    static int compareHand(Hand hand, Hand secondHand) {
        List<ToIntBiFunction<Hand, Hand>> comparators = Arrays.asList(compareHandLevel, compareHandAce, compareHandSecondAce);
        return comparators.stream()
                .map(x -> x.applyAsInt(hand, secondHand))
                .filter(x -> x != 0)
                .findFirst()
                .orElseGet(() -> compareRemainHand(hand.getRemainHand(), secondHand.getRemainHand()));
    }

    static String getWinner(int given) {
        if (given > 0) return Winner.PLAYER_1_WINS;
        else if (given < 0) return Winner.PLAYER_2_WINS;
        return Winner.TIED;
    }
}
