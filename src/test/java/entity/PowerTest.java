package entity;

import constant.CardNumber;
import constant.CardType;
import constant.PowerLevel;
import entity.Power;
import java.lang.reflect.Array;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;

public class PowerTest {
  @Rule
  public final Timeout globalTimeout = new Timeout(10000);

  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  // Test written by Diffblue Cover.
  @Test
  public void setRemainCardInputNotNullOutputVoid() {

    // Arrange
    final Power power = new Power();
    final ArrayList<Card> remainCard = new ArrayList<Card>();

    // Act
    power.setRemainCard(remainCard);

    // Assert side effects
    final ArrayList<Card> arrayList = new ArrayList<Card>();
    Assert.assertEquals(arrayList, power.remainCard);

  }

  // Test written by Diffblue Cover.
  @Test
  public void getRemainCardOutputNull() {

    // Arrange
    final Power power = new Power();

    // Act and Assert result
    Assert.assertNull(power.getRemainCard());

  }

  // Test written by Diffblue Cover.
  @Test
  public void setSecondAceInputNotNullOutputVoid() {

    // Arrange
    final Power power = new Power();
    final Card secondAce = new Card(CardType.DIAMOND, CardNumber.ACE);

    // Act
    power.setSecondAce(secondAce);

    // Assert side effects
    Assert.assertNotNull(power.getSecondAce());
    Assert.assertEquals(CardNumber.ACE, power.getSecondAce().getNumber());
    Assert.assertEquals(CardType.DIAMOND, power.getSecondAce().getType());

  }

  // Test written by Diffblue Cover.
  @Test
  public void setAceInputNotNullOutputVoid() {

    // Arrange
    final Power power = new Power();
    final Card ace = new Card(CardType.DIAMOND, CardNumber.ACE);

    // Act
    power.setAce(ace);

    // Assert side effects
    Assert.assertNotNull(power.getAce());
    Assert.assertEquals(CardNumber.ACE, power.getAce().getNumber());
    Assert.assertEquals(CardType.DIAMOND, power.getAce().getType());

  }

  // Test written by Diffblue Cover.
  @Test
  public void setLevelInputStraightOutputVoid() {

    // Arrange
    final Power power = new Power();
    final PowerLevel level = PowerLevel.STRAIGHT;

    // Act
    power.setLevel(level);

    // Assert side effects
    Assert.assertEquals(PowerLevel.STRAIGHT, power.getLevel());

  }

  // Test written by Diffblue Cover.
  @Test
  public void constructorOutputNotNull() {

    // Act, creating object to test constructor
    final Power actual = new Power();

    // Assert result
    Assert.assertNotNull(actual);
    Assert.assertNull(actual.remainCard);
    Assert.assertNull(actual.level);
    Assert.assertNull(actual.secondAce);
    Assert.assertNull(actual.ace);

  }

  // Test written by Diffblue Cover.
  @Test
  public void constructorInputNotNullNotNullStraightOutputNotNull() {

    // Arrange
    final Card ace = new Card(CardType.HEART, CardNumber.ACE);
    final Card secondAce = new Card(CardType.HEART, CardNumber.ACE);
    final PowerLevel level = PowerLevel.STRAIGHT;

    // Act, creating object to test constructor
    final Power actual = new Power(ace, secondAce, level);

    // Assert result
    Assert.assertNotNull(actual);
    final ArrayList<Card> arrayList = new ArrayList<Card>();
    Assert.assertEquals(arrayList, actual.remainCard);
    Assert.assertEquals(PowerLevel.STRAIGHT, actual.level);
    Assert.assertNotNull(actual.secondAce);
    Assert.assertEquals(CardNumber.ACE, actual.secondAce.getNumber());
    Assert.assertEquals(CardType.HEART, actual.secondAce.getType());
    Assert.assertNotNull(actual.ace);
    Assert.assertEquals(CardNumber.ACE, actual.ace.getNumber());
    Assert.assertEquals(CardType.HEART, actual.ace.getType());

  }

  // Test written by Diffblue Cover.
  @Test
  public void constructorInputNotNullStraightOutputNotNull() {

    // Arrange
    final Card ace = new Card(CardType.HEART, CardNumber.ACE);
    final PowerLevel level = PowerLevel.STRAIGHT;

    // Act, creating object to test constructor
    final Power actual = new Power(ace, level);

    // Assert result
    Assert.assertNotNull(actual);
    final ArrayList<Card> arrayList = new ArrayList<Card>();
    Assert.assertEquals(arrayList, actual.remainCard);
    Assert.assertEquals(PowerLevel.STRAIGHT, actual.level);
    Assert.assertNull(actual.secondAce);
    Assert.assertNotNull(actual.ace);
    Assert.assertEquals(CardNumber.ACE, actual.ace.getNumber());
    Assert.assertEquals(CardType.HEART, actual.ace.getType());

  }

  // Test written by Diffblue Cover.
  @Test
  public void getSecondAceOutputNull() {

    // Arrange
    final Power power = new Power();

    // Act and Assert result
    Assert.assertNull(power.getSecondAce());

  }

  // Test written by Diffblue Cover.
  @Test
  public void getLevelOutputNull() {

    // Arrange
    final Power power = new Power();

    // Act and Assert result
    Assert.assertNull(power.getLevel());

  }

  // Test written by Diffblue Cover.
  @Test
  public void getAceOutputNull() {

    // Arrange
    final Power power = new Power();

    // Act and Assert result
    Assert.assertNull(power.getAce());

  }
}