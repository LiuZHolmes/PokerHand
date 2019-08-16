import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PokerHandTest {

    @Test
    public void should_return_FIVE_when_given_5() {
        String given = "5";

        CardNumber cardNumber = PokerHand.getCardNumberByString(given);

        assertEquals(CardNumber.FIVE,cardNumber);
    }

    @Test
    public void should_return_Diamond_5_when_given_5D() {
        String given = "5D";

        Card card = PokerHand.getCardByString(given);

        assertEquals(new Card(CardType.DIAMOND, CardNumber.FIVE),card);
    }
    @Test
    public void should_return_player_2_wins_when_input_2D_3D_4D_5D_7S_7D_8D_9D_JD_QS() {
        List<String> cards = Arrays.asList("2D","3D","4D","5D","7S",
                                            "7D","8D","9D","JD","QS");

        String result = PokerHand.game(cards);

        assertEquals("Player 2 wins",result);
    }
}
