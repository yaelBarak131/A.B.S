package data;

public class PrintTransaction {

    private int YazAction;
    private double amount;
    private char type;
    private double balanceBefore;
    private double balanceAfter;


    public PrintTransaction(int YazAction, double amount, char type, double balanceBefore, double balanceAfter){
        this.YazAction = YazAction;
        this.amount = amount;
        this.type = type;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
    }

    public int getYazAction() {
        return YazAction;
    }

    public double getAmount() {
        return amount;
    }

    public char getType() {
        return type;
    }

    public double getBalanceBefore() {
        return balanceBefore;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    @Override
    public String toString() {
        return "PrintTransaction{" +
                "YazAction=" + YazAction +
                ", amount=" + amount +
                ", type=" + type +
                ", balanceBefore=" + balanceBefore +
                ", balanceAfter=" + balanceAfter +
                '}';
    }
}
