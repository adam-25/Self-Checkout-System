package seng300.software;

import seng300.software.exceptions.InvalidMembershipCardNumber;

/**
 * Membership card will simply hold user points,
 * then user will be able to use their membership points for discount or etc
 * provided by Self checkout system/admin.
 */
public class MembershipCard {

    private String cardNumber;
    private int points;

    /**
     * @param cardNumber provided from GUI or DB
     */
    public MembershipCard(String cardNumber) {
        this.cardNumber = cardNumber;
        this.points = 0;
    }

    /**
     * @param cardNumber provided from GUI or DB
     * @param points read from DB or have specified points
     */
    public MembershipCard(String cardNumber, int points) {
        this.cardNumber = cardNumber;
        this.points = points;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    // if card number not length == 12 throw new InvalidMembershipCardNumber() exception
//    private boolean isValidMembership(String cardNumber) throws InvalidMembershipCardNumber {
//        if (cardNumber.length() == 12){
//            throw new InvalidMembershipCardNumber();
//        }
//        return true;
//    }
    
    public boolean isValidMembership(String cardNumber) throws InvalidMembershipCardNumber {
        if (cardNumber.length() != 12){
            throw new InvalidMembershipCardNumber();
        }
        return true;
    }
}
