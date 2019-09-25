package entity;

import constant.CardNumber;
import constant.CardType;
import constant.PowerLevel;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Comparator.comparing;

public class Hand {

    private Power power;

    List<Card> cards;

    private Hand remainHand;

    public Hand(List<Card> cards) {
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
        final List<Supplier<Power>> tryDifferentPower = Arrays.asList(this::tryStraight, this::tryThreeOfAKind, this::tryTwoPairs, this::tryPair, this::tryHighCard);
        setPower(tryDifferentPower.stream().map(Supplier::get).filter(Objects::nonNull).findFirst().orElse(null));
    }

    interface Comparator {
        int compare(Map.Entry<CardNumber, Long> o1, Map.Entry<CardNumber, Long> o2);
    }

    private Comparator compareCount = (Map.Entry<CardNumber, Long> o1, Map.Entry<CardNumber, Long> o2) -> (int) (o1.getValue() - o2.getValue());

    private Comparator compareCardNumber = (Map.Entry<CardNumber, Long> o1, Map.Entry<CardNumber, Long> o2) -> o1.getKey().compareTo(o2.getKey());

    private ArrayList<Map.Entry<CardNumber, Long>> countCards() {
        Map<CardNumber, Long> cardNumberCounts = getCards().stream().map(Card::getNumber)
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        ArrayList<Map.Entry<CardNumber, Long>> list = new ArrayList<>(cardNumberCounts.entrySet());
        list.sort((o1, o2) -> {
            List<Comparator> comparators = Arrays.asList(compareCount, compareCardNumber);
            return comparators.stream().map(x -> x.compare(o1, o2)).filter(x -> x != 0).findFirst().orElse(0);
        });
        return list;
    }

    private Power tryHighCard() {
        return new Power(getCards().get(size() - 1), PowerLevel.HIGHCARD);
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
        final Map.Entry<CardNumber, Long> aceItem = list.get(list.size() - 1);
        final Map.Entry<CardNumber, Long> secondAceItem = list.get(list.size() - 2);
        if (aceItem.getValue() == 2L && secondAceItem.getValue() == 2L) {
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
        Hand hand = new Hand(new ArrayList<>());
        final Power thisPower = getPower();
        switch (thisPower.getLevel()) {
            case TWOPAIRS:
                getCards().stream().filter(x -> !x.getNumber().equals(thisPower.getAce().getNumber())
                        && !x.getNumber().equals(thisPower.getSecondAce().getNumber()))
                        .forEach(x -> hand.getCards().add(x));
                break;
            default:
                getCards().stream().filter(x -> !x.getNumber().equals(thisPower.getAce().getNumber()))
                        .forEach(x -> hand.getCards().add(x));
                break;
        }
        hand.sort();
        setRemainHand(hand);
    }

    void calHandPower() {
        sort();
        calHandLevelAndAce();
        calRemainHand();
    }
}
