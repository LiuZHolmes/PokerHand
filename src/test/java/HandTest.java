import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
        assertEquals(fourthCard.getType(),hand.cards.get(3).getType());
        assertEquals(fourthCard.getNumber(),hand.cards.get(3).getNumber());
    }

    @Test public void should_return_level_0_and_ace_SEVEN_when_given_2D_3D_5S_6H_7S() {
        String given = "2D 3D 5S 6H 7S";
        Hand hand = PokerHand.getHandByString(given).get(0);

        hand.calHandLevelAndAce();

        assertEquals(0,hand.power.level);
        assertEquals(CardNumber.SEVEN,hand.power.ace.getNumber());

    }

    @Test public void should_return_null_when_given_2D_3D_5S_6H_7S() {
        String given = "2D 3D 5S 6H 7S";
        Hand hand = PokerHand.getHandByString(given).get(0);

        Power power = hand.tryPair();

        assertNull(power);
    }

    @Test public void should_return_level_1_and_ace_SEVEN_when_given_2D_2H_5S_6H_7S() {
        String given = "2D 2H 5S 6H 7S";
        Hand hand = PokerHand.getHandByString(given).get(0);

        Power power = hand.tryPair();

        assertEquals(1,power.level);
        assertEquals(CardNumber.TWO,power.ace.getNumber());
    }
}
