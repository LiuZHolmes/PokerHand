import java.util.ArrayList;
import java.util.List;

public class Power {
    public Power() {
    }

    public Power(Card ace, PowerLevel level) {
        this.ace = ace;
        this.level = level;
        this.remainCard = new ArrayList<>();
    }

    public Power(Card ace, Card secondAce, PowerLevel level) {
        this.ace = ace;
        this.secondAce = secondAce;
        this.level = level;
        this.remainCard = new ArrayList<>();
    }

    public Card getAce() {
        return ace;
    }

    public void setAce(Card ace) {
        this.ace = ace;
    }

    public Card getSecondAce() {
        return secondAce;
    }

    public void setSecondAce(Card secondAce) {
        this.secondAce = secondAce;
    }

    public PowerLevel getLevel() {
        return level;
    }

    public void setLevel(PowerLevel level) {
        this.level = level;
    }

    public List<Card> getRemainCard() {
        return remainCard;
    }

    public void setRemainCard(List<Card> remainCard) {
        this.remainCard = remainCard;
    }

    public Card ace;
    public Card secondAce;
    public PowerLevel level;
    public List<Card> remainCard;

}
