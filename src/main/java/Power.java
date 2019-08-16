import java.util.ArrayList;
import java.util.List;

public class Power {
    public Power() {
    }

    public Power(Card ace, int level) {
        this.ace = ace;
        this.level = level;
        this.remainCard = new ArrayList<>();
    }

    public Card ace;
    public int level;
    public List<Card> remainCard;

}
