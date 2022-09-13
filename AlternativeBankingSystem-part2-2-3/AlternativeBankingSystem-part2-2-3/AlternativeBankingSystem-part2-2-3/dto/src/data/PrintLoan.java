package data;

import java.util.List;

public class PrintLoan {

    private final String id;
    private final String owner;
    private final String category;
    private final int capital;
    private final int totalYaz;
    private final int interest;
    private final PrintStatus status;
    private final int totalAmount;
    private final int interestPaidEveryYaz;
    private final int  capitalPayEveryYaz;
    private final int paysEveryYaz;


    public PrintLoan(String id, String owner, String category, int capital, int totalYaz, int interest, PrintStatus status,int totalAmount,
                     int interestPaidEveryYaz ,int capitalPayEveryYaz,int paysEveryYaz) {
        this.id = id;
        this.owner = owner;
        this.category = category;
        this.capital = capital;
        this.totalYaz = totalYaz;
        this.interest = interest;
        this.status = status;
        this.totalAmount=totalAmount;
        this.interestPaidEveryYaz=interestPaidEveryYaz;
        this.capitalPayEveryYaz =capitalPayEveryYaz;
        this.paysEveryYaz=paysEveryYaz;
    }


    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getCategory() {
        return category;
    }

    public int getCapital() {
        return capital;
    }

    public int getTotalYaz() {
        return totalYaz;
    }

    public int getInterest() {
        return interest;
    }

    public PrintStatus getStatus() {
        return status;
    }
    public String getStatusName(){
        return status.getStatus().name();
    }
    public int getNextYazPayment() {
        return status.getNextYazPayment();
    }
    public int getActivationYaz() {
        return status.getActivationYaz();
    }
    public List<PrintPayment> getPayments(){
        PrintPayments res = new PrintPayments(status.getPayments());
        return res.getPayments();
    }
    public List<String> getLendersName(){
        return status.getLendersName();
    }
    public List<String>getLendersInvest(){
        return status.getLendersInvest();
    }
    public int getCapitalToPay(){
        return status.getCapitalToPay();
    }
    public int getTotalAmountRaised(){
        return status.getTotalAmountRaised();
    }
    public int getNumOfLatePayments(){
        return status.getNumOfLatePayments();
    }
    public int getCapitalDebt(){
        return status.getCapitalDebt();
    }
    public int getInterestDebt(){
        return status.getInterestDebt();
    }
    public int getFinishedYaz(){
        return status.getFinishedYaz();
    }
    @Override
    public String toString() {
        return "\nLoan{" +
                "\nid = '" + id + '\'' +
                "\nowner = '" + owner + '\'' +
                "\ncategory = '" + category + '\'' +
                "\ncapital = " + capital +
                "\ntotalYaz = " + totalYaz +
                "\ninterest = " + interest +
                "\nstatus = " + status+
                '}';
    }

    public int getCapitalPayEveryYaz() { // תשלום הקרן בכל יז שצריך לשלם
        int x = capital / totalYaz;
        return x * paysEveryYaz;
    }

    public int getInterestToPayEveryYaz() { //תשלום הרבית בכל יז שצריך לשלם
         return interestPaidEveryYaz;
    }


}
