package seng300.testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import seng300.software.MembershipCard;
import seng300.software.exceptions.InvalidMembershipCardNumber;

public class MembershipCardLogicTest {

    private MembershipCard card;
    private MembershipCard card2;

    @Before
    public void setup() {
        card = new MembershipCard("123456789123"); // valid card
        card2 = new MembershipCard("123456212789123"); // invalid card
    }

    @Test
    public void pointsTest() {

        // test at stating
        assertEquals(0, card.getPoints());

        // add 10 points
        card.addPoints(10);
        assertEquals(10, card.getPoints());

    }

    @Test(expected = InvalidMembershipCardNumber.class)
    public void invalidNumberTest() throws InvalidMembershipCardNumber {
        card2.isValidMembership();
    }
}
