package entity;

import constant.CardNumber;
import constant.CardType;
import constant.PowerLevel;
import constant.Winner;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PokerHandTest {

    @Test
    public void should_return_FIVE_when_given_5() {
        String given = "5";

        CardNumber cardNumber = PokerHand.getCardNumberByString(given);

        assertEquals(CardNumber.FIVE, cardNumber);
    }

    @Test
    public void should_return_DIAMOND_when_given_D() {
        String given = "D";

        CardType cardType = PokerHand.getCardTypeByString(given);

        assertEquals(CardType.DIAMOND, cardType);
    }

    @Test
    public void should_return_Diamond_5_when_given_5D() {
        String given = "5D";
        Card card = new Card(CardType.DIAMOND, CardNumber.FIVE);

        Card realCard = PokerHand.getCardByString(given);

        assertEquals(card.getNumber(), realCard.getNumber());
        assertEquals(card.getType(), realCard.getType());
    }

    @Test
    public void should_return_hand_when_given_5_cards() {
        List<Card> cards = IntStream.rangeClosed(1, 5).boxed()
                .map(x -> new Card(CardType.SPADE, CardNumber.ACE)).collect(Collectors.toList());

        Hand hand = PokerHand.getAHandBy5Cards(cards);

        assertEquals(5, hand.size());
    }

    @Test
    public void should_return_hands_when_given_2D_3D_4D_5D_7S_7D_8D_9D_JD_QS() {
        String given = "2D 3D 4D 5D 7S 7D 8D 9D JD QS";

        List<Hand> hands = PokerHand.getHandByString(given);

        assertEquals(2, hands.size());
    }

    @Test
    public void should_return_negative_when_given_HIGHCARD_PAIR() {
        final PowerLevel level = PowerLevel.HIGHCARD;
        final PowerLevel secondLevel = PowerLevel.PAIR;

        final int result = PokerHand.compareLevel(level, secondLevel);

        assertTrue(result < 0);
    }

    @Test
    public void should_return_positive_when_given_THREEOFAKIND_PAIR() {
        final PowerLevel level = PowerLevel.THREEOFAKIND;
        final PowerLevel secondLevel = PowerLevel.PAIR;

        final int result = PokerHand.compareLevel(level, secondLevel);

        assertTrue(result > 0);
    }

    @Test
    public void should_return_positive_when_given_5D_and_2S() {
        final Card card = PokerHand.getCardByString("5D");
        final Card secondCard = PokerHand.getCardByString("2S");
        final int result = PokerHand.compareCard(card, secondCard);

        assertTrue(result > 0);
    }

    @Test
    public void should_return_negative_when_given_2D_3D_5S_6H_and_2D_4D_5S_6H() {
        Hand remainHand = PokerHand.getHandByString("2D 3D 5S 6H").get(0);
        Hand secondRemainHand = PokerHand.getHandByString("2D 4D 5S 6H").get(0);

        final int result = PokerHand.compareRemainHand(remainHand, secondRemainHand);

        assertTrue(result < 0);
    }

    @Test
    public void should_return_positive_when_given_2D_4D_5S_6H_and_2D_3D_5S_6H() {
        Hand remainHand = PokerHand.getHandByString("2D 4D 5S 6H").get(0);
        Hand secondRemainHand = PokerHand.getHandByString("2D 3D 5S 6H").get(0);

        final int result = PokerHand.compareRemainHand(remainHand, secondRemainHand);

        assertTrue(result > 0);
    }

    @Test
    public void should_return_negative_when_given_2D_3D_4D_5D_7S_and_7D_8D_9D_JD_QS() {
        Hand hand = PokerHand.getHandByString("2D 3D 4D 5D 7S").get(0);
        Hand secondHand = PokerHand.getHandByString("7D 8D 9D JD QS").get(0);
        hand.calHandPower();
        secondHand.calHandPower();

        final int result = PokerHand.compareHand(hand, secondHand);

        assertTrue(result < 0);
    }

    @Test
    public void should_return_positive_when_given_2D_2H_2S_JD_QS_and_3D_3H_4D_4S_7S() {
        Hand hand = PokerHand.getHandByString("2D 2H 2S JD QS").get(0);
        Hand secondHand = PokerHand.getHandByString("3D 3H 4D 4S 7S").get(0);
        hand.calHandPower();
        secondHand.calHandPower();

        final int result = PokerHand.compareHand(hand, secondHand);

        assertTrue(result > 0);
    }

    @Test
    public void should_return_positive_when_given_2D_2H_3S_3D_QS_and_2S_2C_3C_3H_7S() {
        Hand hand = PokerHand.getHandByString("2D 2H 3S 3D QS").get(0);
        Hand secondHand = PokerHand.getHandByString("2S 2C 3C 3H 7S").get(0);
        hand.calHandPower();
        secondHand.calHandPower();

        final int result = PokerHand.compareHand(hand, secondHand);

        assertTrue(result > 0);
    }

    @Test
    public void should_return_tied_when_given_0() {
        final int given = 0;

        final String result = PokerHand.getWinner(given);

        assertEquals(Winner.TIED, result);
    }

    @Test
    public void should_return_player_1_wins_when_given_postive() {
        final int given = 10;

        final String result = PokerHand.getWinner(given);

        assertEquals(Winner.PLAYER_1_WINS, result);
    }

    @Test
    public void should_return_player_2_wins_when_given_negative() {
        final int given = -10;

        final String result = PokerHand.getWinner(given);

        assertEquals(Winner.PLAYER_2_WINS, result);
    }

    @Test
    public void should_return_player_1_wins_when_given_2D_2H_2S_JD_QS_3D_3H_4D_4S_7S() {
        final String given = "2D 2H 2S JD QS 3D 3H 4D 4S 7S";

        String result = PokerHand.game(given);

        assertEquals(Winner.PLAYER_1_WINS, result);
    }
}
