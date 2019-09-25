package entity;

import constant.CardNumber;
import constant.CardType;
import constant.PowerLevel;

import java.util.*;
import java.util.function.Supplier;
import java.util.function.ToIntBiFunction;
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

    private ToIntBiFunction<Map.Entry<CardNumber, List<Card>>, Map.Entry<CardNumber, List<Card>>> compareCount =
            (Map.Entry<CardNumber, List<Card>> o1, Map.Entry<CardNumber, List<Card>> o2) -> (int) (o1.getValue().size() - o2.getValue().size());

    private ToIntBiFunction<Map.Entry<CardNumber, List<Card>>, Map.Entry<CardNumber, List<Card>>> compareCardNumber =
            (Map.Entry<CardNumber, List<Card>> o1, Map.Entry<CardNumber, List<Card>> o2) -> o1.getKey().compareTo(o2.getKey());

    private ToIntBiFunction<Card, Card> compareCardType = (Card o1, Card o2) -> o1.getType().compareTo(o2.getType());

    private ArrayList<Map.Entry<CardNumber, List<Card>>> countCards() {
        Map<CardNumber, List<Card>> cardCounts = getCards().stream().collect(Collectors.groupingBy(Card::getNumber));
        cardCounts.forEach((key, value) -> value.sort((o1, o2) -> compareCardType.applyAsInt(o1, o2)));
        ArrayList<Map.Entry<CardNumber, List<Card>>> list = new ArrayList<>(cardCounts.entrySet());
        list.sort((o1, o2) -> {
            List<ToIntBiFunction<Map.Entry<CardNumber, List<Card>>, Map.Entry<CardNumber, List<Card>>>> comparators = Arrays.asList(compareCount, compareCardNumber);
            return comparators.stream().map(x -> x.applyAsInt(o1, o2)).filter(x -> x != 0).findFirst().orElse(0);
        });
        return list;
    }

    private Power tryHighCard() {
        return new Power(getCards().get(size() - 1), PowerLevel.HIGHCARD);
    }

    Power tryPair() {
        ArrayList<Map.Entry<CardNumber, List<Card>>> list = countCards();
        final Map.Entry<CardNumber, List<Card>> item = list.get(list.size() - 1);
        if (item.getValue().size() == 2) {
            return new Power(new Card(CardType.SPAED, item.getKey()), PowerLevel.PAIR);
        }
        return null;
    }

    Power tryTwoPairs() {
        ArrayList<Map.Entry<CardNumber, List<Card>>> list = countCards();
        final Map.Entry<CardNumber, List<Card>> aceItem = list.get(list.size() - 1);
        final Map.Entry<CardNumber, List<Card>> secondAceItem = list.get(list.size() - 2);
        if (aceItem.getValue().size() == 2 && secondAceItem.getValue().size() == 2) {
            return new Power(new Card(CardType.SPAED, aceItem.getKey()),
                    new Card(CardType.SPAED, secondAceItem.getKey()), PowerLevel.TWOPAIRS);
        }
        return null;
    }

    Power tryThreeOfAKind() {
        ArrayList<Map.Entry<CardNumber, List<Card>>> list = countCards();
        final Map.Entry<CardNumber, List<Card>> item = list.get(list.size() - 1);
        if (item.getValue().size() == 3) {
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
