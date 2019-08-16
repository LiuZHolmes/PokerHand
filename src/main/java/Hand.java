import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    List<Card> cards;

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
}
