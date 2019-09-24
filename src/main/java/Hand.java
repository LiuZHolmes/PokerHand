import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Comparator.comparing;

public class Hand {

    private Power power;

    List<Card> cards;

    private Hand remainHand;

    public Hand(List<Card> cards) {
        power = new Power();
        this.cards = cards;
    }

    int size() {
        return getCards().size();
    }

    void sort() {
        setCards(getCards().stream().sorted(comparing(Card::getNumber)).collect(Collectors.toList()));
    }

    Power getPower() {
        return power;
    }

    private void setPower(Power power) {
        this.power = power;
    }

    Hand getRemainHand() {
        return remainHand;
    }

    private void setRemainHand(Hand remainHand) {
        this.remainHand = remainHand;
    }

    List<Card> getCards() {
        return cards;
    }

    private void setCards(List<Card> cards) {
        this.cards = cards;
    }

    void calHandLevelAndAce() {
        sort();
        if (tryStraight() != null) setPower(tryStraight());
        else if (tryThreeOfAKind() != null) setPower(tryThreeOfAKind());
        else if (tryTwoPairs() != null) setPower(tryTwoPairs());
        else if (tryPair() != null) setPower(tryPair());
        else {
            power.setAce(getCards().get(size() - 1));
            power.setLevel(PowerLevel.HIGHCARD);
        }
    }

    private ArrayList<Map.Entry<CardNumber, Long>> countCards() {
        Map<CardNumber, Long> cardNumberCounts = getCards().stream().map(Card::getNumber)
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        ArrayList<Map.Entry<CardNumber, Long>> list = new ArrayList<>(cardNumberCounts.entrySet());
        list.sort(comparing(Map.Entry::getValue));
        return list;
    }

    Power tryPair() {
        ArrayList<Map.Entry<CardNumber, Long>> list = countCards();
        final Map.Entry<CardNumber, Long> item = list.get(list.size() - 1);
        if (item.getValue() == 2L) {
            return new Power(new Card(CardType.SPAED, item.getKey()), PowerLevel.PAIR);
        }
        return null;
    }

    Power tryTwoPairs() {
        ArrayList<Map.Entry<CardNumber, Long>> list = countCards();
        final Map.Entry<CardNumber, Long> item = list.get(list.size() - 1);
        final Map.Entry<CardNumber, Long> secondItem = list.get(list.size() - 2);
        final Map.Entry<CardNumber, Long> aceItem = item.getKey().compareTo(secondItem.getKey()) < 0 ? secondItem : item;
        final Map.Entry<CardNumber, Long> secondAceItem = item.getKey().compareTo(secondItem.getKey()) < 0 ? item : secondItem;
        if (item.getValue() == 2L && secondItem.getValue() == 2L) {
            return new Power(new Card(CardType.SPAED, aceItem.getKey()),
                    new Card(CardType.SPAED, secondAceItem.getKey()), PowerLevel.TWOPAIRS);
        }
        return null;
    }

    Power tryThreeOfAKind() {
        ArrayList<Map.Entry<CardNumber, Long>> list = countCards();
        final Map.Entry<CardNumber, Long> item = list.get(list.size() - 1);
        if (item.getValue() == 3L) {
            return new Power(new Card(CardType.SPAED, item.getKey()), PowerLevel.THREEOFAKIND);
        }
        return null;
    }

    Power tryStraight() {
        if (IntStream
                .range(1, getCards().size())
                .allMatch(i -> getCards().get(i).getNumber().ordinal() - getCards().get(i - 1).getNumber().ordinal() == 1)) {
            return new Power(getCards().get(size() - 1), PowerLevel.STRAIGHT);
        }
        return null;
    }

    void calRemainHand() {
        Hand remainHand = new Hand(new ArrayList<>());
        final Power power = getPower();
        switch (power.getLevel()) {
            case TWOPAIRS:
                getCards().stream().filter(x -> !x.getNumber().equals(power.getAce().getNumber())
                        && !x.getNumber().equals(power.getSecondAce().getNumber()))
                        .forEach(x -> remainHand.getCards().add(x));
                break;
            default:
                getCards().stream().filter(x -> !x.getNumber().equals(power.getAce().getNumber()))
                        .forEach(x -> remainHand.getCards().add(x));
                break;
        }
        remainHand.sort();
        setRemainHand(remainHand);
    }

    void calHandPower() {
        sort();
        calHandLevelAndAce();
        calRemainHand();
    }
}
