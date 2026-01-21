package factory.WithoutFactory;

public class ZaloPayProcessor {
    private String accountId;
    private double amount;

    public void processPayment(double amount, String accountId) {
        this.amount = amount;
        this.accountId = accountId;
        authenticateAccount();
        transferMoney();
    }

    private void authenticateAccount() {
    }

    private void transferMoney() {
    }

    public double getAmount() {
        return amount;
    }

    public String getAccountId() {
        return accountId;
    }
}
