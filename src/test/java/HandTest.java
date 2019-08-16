import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HandTest {
    @Test
    public void should_return_2D_3D_4S_5H_6S_when_given_2D_4S_3D_6S_5H() {
        String given = "2D 4S 3D 6S 5H";
        Hand hand = PokerHand.getHandByString(given).get(0);

        hand.sort();

        Card secondCard = new Card(CardType.DIAMOND, CardNumber.THREE);
        Card fourthCard = new Card(CardType.HEART, CardNumber.FIVE);
        assertEquals(secondCard.getType(),hand.cards.get(1).getType());
        assertEquals(secondCard.getNumber(),hand.cards.get(1).getNumber());
        assertEquals(secondCard.getType(),hand.cards.get(3).getType());
        assertEquals(secondCard.getNumber(),hand.cards.get(3).getNumber());
    }
}
