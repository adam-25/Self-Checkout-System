package seng300.testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import seng300.software.MembershipCard;
import seng300.software.exceptions.InvalidMembershipCardNumber;

public class MembershipCardLogicTest {

    private MembershipCard Validcard;
    private MembershipCard Invalidcard;

    @Before
    public void setup() {
        Validcard = new MembershipCard("123456789123"); // valid card
        Invalidcard = new MembershipCard("123456212789123"); // invalid card
    }

    @Test
    public void pointsTest() {

        // test at stating
        assertEquals(0, Validcard.getPoints());

        // add 10 points
       // card.addPoints(10);
        Validcard.setPoints(10);
        assertEquals(10, Validcard.getPoints());

    }

    @Test(expected = InvalidMembershipCardNumber.class)
    public void invalidNumberTest() throws InvalidMembershipCardNumber {
    	Invalidcard.isValidMembership(Invalidcard.getCardNumber());
    }
}
