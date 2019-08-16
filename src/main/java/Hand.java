import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

public class Hand {

    public Power power;

    List<Card> cards;

    public Hand(List<Card> cards) {
        power = new Power();
        this.cards = cards;
    }

    public int size() {
        return cards.size();
    }

    public void sort() {
        cards = cards.stream().sorted(comparing(Card::getNumber)).collect(Collectors.toList());
    }

    public void calHandLevelAndAce() {
        power.ace = cards.get(cards.size() - 1);
        power.level = 0;
    }

    private ArrayList<Map.Entry<CardNumber,Long>> countCards() {
        Map<CardNumber,Long> cardNumberCounts = cards.stream().map(Card::getNumber)
                .collect(Collectors.groupingBy(x -> x,Collectors.counting()));
        ArrayList<Map.Entry<CardNumber,Long>> list = new ArrayList<>(cardNumberCounts.entrySet());
        list.sort(comparing(Map.Entry::getValue));
        return list;
    }
    public Power tryPair() {
        ArrayList<Map.Entry<CardNumber,Long>> list = countCards();
        final Map.Entry<CardNumber,Long> item = list.get(list.size() - 1);
        if (item.getValue() == 2L) {
            return new Power(new Card(CardType.SPAED,item.getKey()),1);
        }
        return null;
    }

    public Power tryTwoPairs() {
        ArrayList<Map.Entry<CardNumber,Long>> list = countCards();
        final Map.Entry<CardNumber,Long> item = list.get(list.size() - 1);
        final Map.Entry<CardNumber,Long> secondItem = list.get(list.size() - 2);
        final Map.Entry<CardNumber,Long> aceItem = item.getKey().compareTo(secondItem.getKey()) < 0 ? secondItem : item;
        if (item.getValue() == 2L && secondItem.getValue() == 2L) {
            return new Power(new Card(CardType.SPAED,aceItem.getKey()),2);
        }
        return null;
    }

    public Power tryThreeOfAKind() {
        return null;
    }
}
