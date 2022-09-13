package data;

import java.util.ArrayList;
import java.util.List;

public class LoansOnSell {
    List<SellingLoanData> loans;

    public LoansOnSell(List<SellingLoanData> loans) {
        this.loans = loans;
    }
    public LoansOnSell() {
        this.loans = new ArrayList<>();
    }

    public void addLoan(String id, double price, String sellingCustomer) {
        SellingLoanData temp = new SellingLoanData(id, price, sellingCustomer);
        loans.add(temp);
    }

    public void setLoans(List<SellingLoanData> loans) {
        this.loans = loans;
    }

    public List<SellingLoanData> getLoans() {

        return loans;
    }
}
