package factory.WithFactory;

public class ZaloPayProcessor extends PaymentProcessor {
    private String accountId;

    @Override
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

    public String getAccountId() {
        return accountId;
    }
}

