import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        cards = cards.stream().sorted(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return o1.getNumber().compareTo(o2.getNumber());
            }
        }).collect(Collectors.toList());
    }

    public void calHandLevelAndAce() {
        power.ace = cards.get(cards.size() - 1);
        power.level = 0;
    }
}
