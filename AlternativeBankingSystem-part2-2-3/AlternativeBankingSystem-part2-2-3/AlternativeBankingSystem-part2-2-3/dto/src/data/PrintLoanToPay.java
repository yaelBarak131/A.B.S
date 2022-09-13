package data;


public class PrintLoanToPay {

    private String id;
    private int debt;
    private int yazToPay;

    public PrintLoanToPay(String id, int debt, int yazToPay) {
        this.id = id;
        this.debt = debt;
        this.yazToPay = yazToPay;

    }

    public PrintLoanToPay() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDebt() {
        return debt;
    }

    public void setDebt(int debt) {
        this.debt = debt;
    }

    public int getYazToPay() {
        return yazToPay;
    }

    public void setYazToPay(int yazToPay) {
        this.yazToPay = yazToPay;
    }
}
