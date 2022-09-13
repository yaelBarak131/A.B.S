package data;

import java.util.ArrayList;
import java.util.List;

public class PrintLoansToPay {


    List<PrintLoanToPay> loansToPay;
    String customerName = null;
    int currYaz;

    public int getCurrYaz() {
        return currYaz;
    }

    public String getCustomerName() {
        return customerName;
    }

    public PrintLoansToPay(List<PrintLoanToPay> loans, String customerName, int currYaz)
    {
        this.loansToPay = new ArrayList<>();

        loansToPay.addAll(loans);
        this.customerName = customerName;
        this.currYaz = currYaz;
    }

    public PrintLoansToPay(List<PrintLoan> a, boolean type) {   //true- pay a loan, false-close a loan

        this.loansToPay = new ArrayList<>();

        for(PrintLoan loan: a) {
            PrintLoanToPay add = new PrintLoanToPay();

            add.setId(loan.getId());
            add.setYazToPay(loan.getStatus().getNextYazPayment());
            if(type) {
                if (loan.getStatus().getStatus() == PrintStatus.Statuses.Active)
                    add.setDebt(loan.getCapitalPayEveryYaz() + loan.getInterestToPayEveryYaz());   //

                else if (loan.getStatus().getStatus() == PrintStatus.Statuses.InRisk) {
                    add.setDebt(loan.getCapitalPayEveryYaz() + loan.getInterestToPayEveryYaz() + loan.getStatus().getCapitalDebt() + loan.getStatus().getInterestDebt());
                }
            }
            else{
                add.setDebt(loan.getCapital() + loan.getInterest() - (loan.getStatus().getTotalCapitalPaid() + loan.getStatus().getTotalInterestPaid()));
            }

            this.loansToPay.add(add);

        }


    }

    public List<PrintLoanToPay> getLoansToPay() { return loansToPay;}

}