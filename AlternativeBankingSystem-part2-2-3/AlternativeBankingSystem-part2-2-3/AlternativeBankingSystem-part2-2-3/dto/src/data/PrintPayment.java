package data;

public class PrintPayment {
    private int paymentYaz;
    private int capital;
    private int interest;
    private int sum;

    public PrintPayment(int paymentYaz, int capital, int interest, int sum) {
        this.paymentYaz = paymentYaz;
        this.capital = capital;
        this.interest = interest;
        this.sum = sum;
    }

    public int getPaymentYaz() {
        return paymentYaz;
    }

    public void setPaymentYaz(int paymentYaz) {
        this.paymentYaz = paymentYaz;
    }

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public int getInterest() {
        return interest;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "PrintPayment{" +
                "paymentYaz=" + paymentYaz +
                ", capital=" + capital +
                ", interest=" + interest +
                ", sum=" + sum +
                '}';
    }
}
