package data;

import java.util.ArrayList;
import java.util.List;

public class PrintLoans {
    List<PrintLoan> loans;

     public PrintLoans(){
         loans=new ArrayList<>();
     }

    public PrintLoans(List<PrintLoan> loans) {
        this.loans = loans;
    }

    public void addLoan(PrintLoan loan){
        loans.add(loan);
    }

    public List<PrintLoan> getLoans() {
        return loans;
    }

    @Override
    public String toString() {
        return "Loans{" +
                loans +
                '\n';
    }

    public String getIdLoanFromInd(int ind) {
        return loans.get(ind).getId();
    }

    public String getIdLoan(int ind) {
        return getIdLoanFromInd(ind);
    }

    public PrintLoans getLoanWithStatus(String status) {
        PrintLoans res = new PrintLoans();

        if(status.equals("All"))
            return this;

        for(PrintLoan loan:loans) {
            if(loan.getStatus().getStatus().toString().equals(status))
                res.getLoans().add(loan);
        }
        return res;
    }


}