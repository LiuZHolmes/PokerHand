import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

public class Hand {

    public Power power;

    List<Card> cards;

    Hand remainHand;

    public Hand(List<Card> cards) {
        power = new Power();
        this.cards = cards;
    }

    public int size() {
        return getCards().size();
    }

    public void sort() {
        setCards(getCards().stream().sorted(comparing(Card::getNumber)).collect(Collectors.toList()));
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    public Hand getRemainHand() {
        return remainHand;
    }

    public void setRemainHand(Hand remainHand) {
        this.remainHand = remainHand;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void calHandLevelAndAce() {
        sort();
        if (tryThreeOfAKind() != null) setPower(tryThreeOfAKind());
        else if (tryTwoPairs() != null) setPower(tryTwoPairs());
        else if (tryPair() != null) setPower(tryPair());
        else {
            power.setAce(getCards().get(size() - 1));
            power.setLevel(0);
        }
    }

    private ArrayList<Map.Entry<CardNumber, Long>> countCards() {
        Map<CardNumber, Long> cardNumberCounts = getCards().stream().map(Card::getNumber)
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        ArrayList<Map.Entry<CardNumber, Long>> list = new ArrayList<>(cardNumberCounts.entrySet());
        list.sort(comparing(Map.Entry::getValue));
        return list;
    }

    public Power tryPair() {
        ArrayList<Map.Entry<CardNumber, Long>> list = countCards();
        final Map.Entry<CardNumber, Long> item = list.get(list.size() - 1);
        if (item.getValue() == 2L) {
            return new Power(new Card(CardType.SPAED, item.getKey()), 1);
        }
        return null;
    }

    public Power tryTwoPairs() {
        ArrayList<Map.Entry<CardNumber, Long>> list = countCards();
        final Map.Entry<CardNumber, Long> item = list.get(list.size() - 1);
        final Map.Entry<CardNumber, Long> secondItem = list.get(list.size() - 2);
        final Map.Entry<CardNumber, Long> aceItem = item.getKey().compareTo(secondItem.getKey()) < 0 ? secondItem : item;
        final Map.Entry<CardNumber, Long> secondAceItem = item.getKey().compareTo(secondItem.getKey()) < 0 ? item : secondItem;
        if (item.getValue() == 2L && secondItem.getValue() == 2L) {
            return new Power(new Card(CardType.SPAED, aceItem.getKey()),
                    new Card(CardType.SPAED, secondAceItem.getKey()), 2);
        }
        return null;
    }

    public Power tryThreeOfAKind() {
        ArrayList<Map.Entry<CardNumber, Long>> list = countCards();
        final Map.Entry<CardNumber, Long> item = list.get(list.size() - 1);
        if (item.getValue() == 3L) {
            return new Power(new Card(CardType.SPAED, item.getKey()), 3);
        }
        return null;
    }

    public void calRemainHand() {
        Hand remainHand = new Hand(new ArrayList<>());
        final Power power = getPower();
        switch (power.getLevel()) {
            case 0:
                break;
            case 2:
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
}
