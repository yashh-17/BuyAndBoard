package shopnbook.ecommerce;

public class User {
    private String name;
    private double walletBalance;

    public User(String name, double walletBalance) {
        this.name = name;
        this.walletBalance = walletBalance;
    }

    public String getName() {
        return name;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void deductBalance(double amount) {
        this.walletBalance -= amount;
        if (this.walletBalance < 0) {
            this.walletBalance = 0;
        }
    }
}


