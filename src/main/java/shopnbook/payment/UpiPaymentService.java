package shopnbook.payment;

import java.util.Random;

public class UpiPaymentService implements PaymentService {
    private final Random random = new Random();

    @Override
    public PaymentResult pay(double amount, String upiId) {
        // Simulate sending a collect request to UPI app
        try {
            Thread.sleep(800); // small delay to feel realistic
        } catch (InterruptedException ignored) {}

        // High probability of success
        boolean ok = random.nextDouble() < 0.95;
        String txnId = "UPI-" + System.currentTimeMillis() + "-" + (1000 + random.nextInt(9000));
        if (ok) {
            return new PaymentResult(PaymentStatus.SUCCESS, txnId, "Payment successful via UPI");
        } else {
            return new PaymentResult(PaymentStatus.FAILED, txnId, "Payment failed via UPI");
        }
    }
}
