package seng300.software;

import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.CardReaderObserver;
import seng300.software.exceptions.NotEnoughFoundInGiftCard;

import java.math.BigDecimal;

/**
 * This class able to scan and hold gift card information
 */
public class PayWithGiftCard implements CardReaderObserver {

    public static String TYPE = "gift"; // gift card type
    private Card card;
    private String pin;

    /**
     * for simplicity lets consider each gift card have 100$
     */
    private BigDecimal giftAmount = new BigDecimal(100);
    private BigDecimal totalAmountUsed = new BigDecimal(0);

    // card status
    private boolean cardTapped = false;
    private boolean cardSwiped = false;
    private boolean cardInsert = false;
    
    public String cardNumber;

    // default constructor
    public PayWithGiftCard(Card card, String pin) {
        // validate card type
        this.card = card;
        this.pin = pin;
    }
    
//    public PayWithGiftCard(String cardNumber) {
//        this.cardNumber = cardNumber;
//    }

    /**
     * @param amount total invoice amount that need to pay
     * @return remain amount that need to pay using other payment method
     */
    public BigDecimal pay(BigDecimal amount) {
        // pay amount should < total amount
    	giftAmount = amount;
//        if (giftAmount.compareTo(amount) < 0) {
//
//            // update total paid amount from gift card
//            totalAmountUsed = giftAmount;
//
//            // removed full amount from gift card
//            giftAmount = new BigDecimal(0);
//
//            // return remain amount
//            return amount.subtract(giftAmount);
//        }

        // update total paid amount from gift card
        totalAmountUsed = totalAmountUsed.add(amount);

        // removed amount from gift card
        giftAmount = giftAmount.subtract(amount);

        // return 0 to indicate full paid
        return new BigDecimal(0);
    }

    @Override
    public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
        //device.enable();
    }

    @Override
    public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
       // device.disable();
    }

    @Override
    public void cardInserted(CardReader reader) {
        cardInsert = true;
    }

    @Override
    public void cardTapped(CardReader reader) {
        cardTapped = true;
    }

    @Override
    public void cardSwiped(CardReader reader) {
        cardSwiped = true;
    }

    @Override
    public void cardRemoved(CardReader reader) {
        // remove the card from device, for this imagine in the
        // reader we can read single card at a time
        //reader.remove();

        // after remove update all value,
        // so we can process another card using same object
//	        cardTapped = false;
//	        cardSwiped = false;
//	        cardInsert = false;
    }

    @Override
    public void cardDataRead(CardReader reader, Card.CardData data) {
    }

    public boolean isCardTapped() {
        return cardTapped;
    }

    public boolean isCardSwiped() {
        return cardSwiped;
    }

    public boolean isCardInsert() {
        return cardInsert;
    }
}
