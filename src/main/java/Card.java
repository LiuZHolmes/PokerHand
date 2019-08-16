public class Card {

    private CardType type;
    private CardNumber number;

    public Card(CardType type, CardNumber number) {
        this.type = type;
        this.number = number;
    }

    public CardType getType() {
        return type;
    }

    public CardNumber getNumber() {
        return number;
    }
}
