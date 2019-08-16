import java.util.List;

public class Hand {

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    List<Card> cards;

    public int size() {
        return cards.size();
    }

    public void sort() {
    }
}
