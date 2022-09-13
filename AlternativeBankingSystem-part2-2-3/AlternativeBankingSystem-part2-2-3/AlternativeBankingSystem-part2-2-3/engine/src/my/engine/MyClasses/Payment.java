package my.engine.MyClasses;

public class Payment {

    private int paymentYaz;
    private int capital;
    private int interest;
    private int sum;

    public Payment(int paymentYaz, int capital, int interest, int sum) {
        this.capital = capital;
        this.paymentYaz = paymentYaz;
        this.interest = interest;
        this.sum = sum;
    }

    public int getPaymentYaz() {
        return paymentYaz;
    }

    public int getCapital() {
        return capital;
    }

    public int getInterest() {
        return interest;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentYaz=" + paymentYaz +
                ", capital=" + capital +
                ", interest=" + interest +
                ", sum=" + sum +
                '}';
    }

    public void setPaymentYaz(int paymentYaz) {
        this.paymentYaz = paymentYaz;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
