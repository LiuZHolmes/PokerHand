import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class PokerHandTest {
    @Test
    public void should_return_player_2_wins_when_input_2D_3D_4D_5D_7S_7D_8D_9D_JD_QS() {
        List<String> cards = Arrays.asList("2D","3D","4D","5D","7S",
                                            "7D","8D","9D","JD","QS");

        String result = PokerHand.game(cards);

        Assert.assertEquals("Player 2 wins",result);
    }
}
