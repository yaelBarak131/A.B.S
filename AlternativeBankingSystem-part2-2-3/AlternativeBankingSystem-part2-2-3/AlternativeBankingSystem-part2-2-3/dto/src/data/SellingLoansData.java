package data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SellingLoansData {
    Set<SellingLoanData> loansOnSell;
    String customerName;

    public SellingLoansData(Set<SellingLoanData> loansOnSell, String customerName) {
        this.loansOnSell = loansOnSell;
        this.customerName = customerName;
    }

    public Set<SellingLoanData> getLoansOnSell() {
        return loansOnSell;
    }

    public void setLoansOnSell(Set<SellingLoanData> loansOnSell) {
        this.loansOnSell = loansOnSell;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

   public boolean deleteSellingLoan(SellingLoanData loanToDelete){
       Set<SellingLoanData> newLoans=new HashSet<>();
        for(SellingLoanData data:loansOnSell)
        {
            if(!data.equals(loanToDelete))
                newLoans.add(data);
        }
        loansOnSell=newLoans;

        return loansOnSell.size()==0;

   }
}
