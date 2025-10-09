package shopnbook.payment;

public interface PaymentService {
    PaymentResult pay(double amount, String payerRef);
}
