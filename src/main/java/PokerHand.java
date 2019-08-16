import java.util.ArrayList;
import java.util.List;

public class PokerHand {
    static String game(List<String> cards) {
        List<String> HandOfPlayer1 = new ArrayList<String>();
        List<String> HandOfPlayer2 = new ArrayList<String>();
        for (int i = 0; i < cards.size(); i++) {
            if (i < 5)
                HandOfPlayer1.add(cards.get(i));
            else HandOfPlayer2.add(cards.get(i));
        }
        int result = HandOfPlayer1.get(HandOfPlayer1.size() - 1).charAt(0) >
                HandOfPlayer2.get(HandOfPlayer1.size() - 1).charAt(0) ? 1 : 2;
        return "Player " + result + " wins";
    }
}
