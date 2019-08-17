import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class HandTest {
    @Test
    public void should_return_2D_3D_4S_5H_6S_when_given_2D_4S_3D_6S_5H_and_sort() {
        String given = "2D 4S 3D 6S 5H";
        Hand hand = PokerHand.getHandByString(given).get(0);

        hand.sort();

        Card secondCard = new Card(CardType.DIAMOND, CardNumber.THREE);
        Card fourthCard = new Card(CardType.HEART, CardNumber.FIVE);
        assertEquals(secondCard.getType(), hand.cards.get(1).getType());
        assertEquals(secondCard.getNumber(), hand.cards.get(1).getNumber());
        assertEquals(fourthCard.getType(), hand.cards.get(3).getType());
        assertEquals(fourthCard.getNumber(), hand.cards.get(3).getNumber());
    }

    @Test
    public void should_return_level_0_and_ace_SEVEN_when_given_2D_3D_5S_6H_7S_and_cal_hande_level_and_ace() {
        String given = "2D 3D 5S 6H 7S";
        Hand hand = PokerHand.getHandByString(given).get(0);

        hand.calHandLevelAndAce();

        assertEquals(0, hand.getPower().getLevel());
        assertEquals(CardNumber.SEVEN, hand.getPower().getAce().getNumber());
    }

    @Test
    public void should_return_level_0_and_ace_SEVEN_when_given_2D_5S_3D_7S_6H_and_cal_hande_level_and_ace() {
        String given = "2D 5S 3D 7S 6H";
        Hand hand = PokerHand.getHandByString(given).get(0);

        hand.calHandLevelAndAce();

        assertEquals(0, hand.getPower().getLevel());
        assertEquals(CardNumber.SEVEN, hand.getPower().getAce().getNumber());
    }

    @Test
    public void should_return_level_1_and_ace_TWO_when_given_2D_2H_5S_6H_7S_and_cal_hande_level_and_ace() {
        String given = "2D 2H 5S 6H 7S";
        Hand hand = PokerHand.getHandByString(given).get(0);

        hand.calHandLevelAndAce();

        assertEquals(1, hand.getPower().getLevel());
        assertEquals(CardNumber.TWO, hand.getPower().getAce().getNumber());
    }

    @Test
    public void should_return_level_2_and_ace_FIVE_when_given_2D_2H_5S_5H_7S_and_cal_hande_level_and_ace() {
        String given = "2D 2H 5S 5H 7S";
        Hand hand = PokerHand.getHandByString(given).get(0);

        hand.calHandLevelAndAce();

        assertEquals(2, hand.getPower().getLevel());
        assertEquals(CardNumber.FIVE, hand.getPower().getAce().getNumber());
    }

    @Test
    public void should_return_level_3_and_ace_TWO_when_given_2D_2H_2S_5H_7S_and_cal_hande_level_and_ace() {
        String given = "2D 2H 2S 5H 7S";
        Hand hand = PokerHand.getHandByString(given).get(0);

        hand.calHandLevelAndAce();

        assertEquals(3, hand.getPower().getLevel());
        assertEquals(CardNumber.TWO, hand.getPower().getAce().getNumber());
    }

    @Test
    public void should_return_null_when_given_2D_3D_5S_6H_7S_and_try_pair() {
        String given = "2D 3D 5S 6H 7S";
        Hand hand = PokerHand.getHandByString(given).get(0);

        Power power = hand.tryPair();

        assertNull(power);
    }

    @Test
    public void should_return_level_1_and_ace_TWO_when_given_2D_2H_5S_6H_7S_and_try_pair() {
        String given = "2D 2H 5S 6H 7S";
        Hand hand = PokerHand.getHandByString(given).get(0);

        Power power = hand.tryPair();

        assertEquals(1, power.getLevel());
        assertEquals(CardNumber.TWO, power.getAce().getNumber());
    }

    @Test
    public void should_return_null_when_given_2D_3D_5S_6H_7S_and_try_two_pairs() {
        String given = "2D 3D 5S 6H 7S";
        Hand hand = PokerHand.getHandByString(given).get(0);

        Power power = hand.tryTwoPairs();

        assertNull(power);
    }

    @Test
    public void should_return_level_2_and_ace_FIVE_when_given_2D_2H_5S_5H_7S_and_try_two_pairs() {
        String given = "2D 2H 5S 5H 7S";
        Hand hand = PokerHand.getHandByString(given).get(0);

        Power power = hand.tryTwoPairs();

        assertEquals(2, power.getLevel());
        assertEquals(CardNumber.FIVE, power.getAce().getNumber());
    }

    @Test
    public void should_return_null_when_given_2D_3D_5S_6H_7S_and_try_three_of_a_kind() {
        String given = "2D 3D 5S 6H 7S";
        Hand hand = PokerHand.getHandByString(given).get(0);

        Power power = hand.tryThreeOfAKind();

        assertNull(power);
    }

    @Test
    public void should_return_level_3_and_ace_TWO_when_given_2D_2H_2S_5H_7S_and_try_three_of_a_kind() {
        String given = "2D 2H 2S 5H 7S";
        Hand hand = PokerHand.getHandByString(given).get(0);

        Power power = hand.tryThreeOfAKind();

        assertEquals(3, power.getLevel());
        assertEquals(CardNumber.TWO, power.getAce().getNumber());
    }

    @Test
    public void should_return_empty_list_when_given_2D_3D_5S_6H_7S_and_cal_remain_hand() {
        String given = "2D 3D 5S 6H 7S";
        Hand hand = PokerHand.getHandByString(given).get(0);

        hand.calRemainHand();

        assertEquals(0, hand.getRemainHand().size());
    }

    @Test
    public void should_return_5S_6H_7S_when_given_2D_2H_5S_6H_7S_and_cal_remain_hand() {
        Hand hand = spy(new Hand(new ArrayList<>()));
        when(hand.getCards()).thenReturn(new ArrayList<>(Arrays.asList(PokerHand.getCardByString("2D"),
                PokerHand.getCardByString("2H"),
                PokerHand.getCardByString("5S"),
                PokerHand.getCardByString("6H"),
                PokerHand.getCardByString("7S"))));
        when(hand.getPower()).thenReturn(new Power(PokerHand.getCardByString("2H"),1));

        hand.calRemainHand();

        assertEquals(3, hand.getRemainHand().size());
        assertEquals(CardNumber.SEVEN, hand.getRemainHand().getCards()
                .get(hand.getRemainHand().size() - 1).getNumber());
    }

    @Test
    public void should_return_7S_when_given_2D_2H_5S_5H_7S_and_cal_remain_hand() {
        Hand hand = spy(new Hand(new ArrayList<>()));
        when(hand.getCards()).thenReturn(new ArrayList<>(Arrays.asList(PokerHand.getCardByString("2D"),
                PokerHand.getCardByString("2H"),
                PokerHand.getCardByString("5S"),
                PokerHand.getCardByString("5H"),
                PokerHand.getCardByString("7S"))));
        when(hand.getPower()).thenReturn(new Power(PokerHand.getCardByString("5H"),2));

        hand.calRemainHand();

        assertEquals(1, hand.getRemainHand().size());
        assertEquals(CardNumber.SEVEN, hand.getRemainHand().getCards()
                .get(hand.getRemainHand().size() - 1).getNumber());
    }
}
