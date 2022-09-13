package my.engine.MyClasses;

public class Transaction {
    private int yaz;
    private double amount;
    private char action;//Income-'+' Expenditure-'-'
    private double balanceBefore;
    private double balanceAfter;

    public Transaction(int yaz, double amount, char action, double balanceBefore, double balanceAfter) {
        this.yaz = yaz;
        this.amount = amount;
        this.action = action;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
    }

    public int getYaz() {
        return yaz;
    }

    public void setYaz(int yaz) {
        this.yaz = yaz;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public char getAction() {
        return action;
    }

    public void setAction(char action) {
        this.action = action;
    }

    public double getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(int balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(int balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    @Override
    public String toString() {
        return "Transaction { " +
                "yaz = " + yaz +
                ", amount = " + amount +
                ", action = " + action +
                ", balance before = " + balanceBefore +
                ", balance after = " + balanceAfter +
                '}';
    }
}
