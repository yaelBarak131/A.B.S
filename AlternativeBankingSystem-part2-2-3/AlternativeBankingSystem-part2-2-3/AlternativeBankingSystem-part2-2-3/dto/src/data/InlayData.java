package data;

import java.util.List;

public class InlayData {

   List<String> loansId ;
   int amount;
   String customerName;
   PrintLoans relventLoans;
   int maxPrecent;

    public InlayData(List<String> loansId, int amount, String customerName, PrintLoans relentLoans, int maxPrecent) {
        this.loansId = loansId;
        this.amount = amount;
        this.customerName = customerName;
        this.relventLoans = relentLoans;
        this.maxPrecent = maxPrecent;
    }

    public List<String> getLoansId() {
        return loansId;
    }

    public int getAmount() {
        return amount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public PrintLoans getRelventLoans() {
        return relventLoans;
    }

    public int getMaxPrecent() {
        return maxPrecent;
    }
}
