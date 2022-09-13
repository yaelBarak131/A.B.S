package my.engine.MyClasses;

import java.util.*;

import data.*;
import my.engine.exception.*;
import my.engine.jaxb.generatedEX3.*;

import static java.lang.Math.min;

public class Loans implements Cloneable {

    private List<Loan> loans = new ArrayList<>();

    private PrintLoans printLoansOnSell;//Map< customer name, loan id >

    public Loans() {}
    public Loans(List<Loan> loans) {
        this.loans = loans;
    }
    /**
     * Gets the value of the absLoan property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the absLoan property.
     */
    public Object clone() throws CloneNotSupportedException {
        Loans temp = new Loans();

        for (Loan loan : loans)
            temp.getLoans().add(loan);
        return temp;
    }

    public List<Loan> getLoans() {
        if (loans == null) {
            loans = new ArrayList<>();
        }
        return this.loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }



    public PrintLoans getPrintLoansOnSell() {
        return printLoansOnSell;
    }



/*
    private boolean findLoansToSellByCustomer(String customerName) {
        for (SellingLoansData sellingLoanData : loansOnSell)
            if (sellingLoanData.getCustomerName().equals(customerName)) {
                return true;
            }
        return false;
    }
*/

    public Loans copyJaxbClass(AbsDescriptor abs, String name) throws CategoryException, PaymentRateException, OwnerException, CloneNotSupportedException {
        Loans res = new Loans();
        for (AbsLoan absL : abs.getAbsLoans().getAbsLoan()) {
            Loan temp = new Loan();
            temp = temp.setNewLoan(absL, name);

            res.getLoans().add(temp);
        }
        return res;
    }

    public void checkJaxbClass(AbsDescriptor abs, Set<String> categories, List<Loan> loans) throws CategoryException, PaymentRateException, OwnerException, CustomerException, LoanIdException {
        Loan temp = new Loan();
        if (loans != null) {
            for (AbsLoan loan : abs.getAbsLoans().getAbsLoan())
                if (findId(loan.getId()))
                    throw new LoanIdException(loan.getId());
        }
        for (AbsLoan absC : abs.getAbsLoans().getAbsLoan()) {
            temp.checkLoan(absC, abs.getAbsCategories(), categories);
        }
    }

    private boolean findId(String id) {
        if (loans != null) {
            for (Loan loan : loans) {
                if (loan.getId().equals(id))
                    return true;
            }
        }
        return false;
    }

    private List<Loan> getSortedActiveAndInRiskLoansToPay(String customer, int currYaz) { // get all the loans that are in risk mode of active mode then sort them by activation yaz time

        List<Loan> temp = new ArrayList<>();
        List<Loan> res = new ArrayList<>();


        for (Loan loan : loans) { // get active and in risk loans

            if ((loan.getStatus().getStatus().equals(Status.Statuses.Active) || loan.getStatus().getStatus().equals(Status.Statuses.InRisk)) && loan.getOwner().getName().equals(customer)) // check if this method works well
                temp.add(loan);
        }

        for(Loan loan: temp)
        {
            if(loan.getStatus().getStatus().equals(Status.Statuses.Active)) {
                if (loan.getStatus().getNextYazPayment() == currYaz)
                    res.add(loan);
            }
            else
                res.add(loan);
        }
        //res.removeIf(a -> a.getStatus().getNextYazPayment() != currYaz && a.getStatus().getStatus().equals(Status.Statuses.Active)); // is this check is enough

        return res;
    }

    public void payLoans(Map<String, PrintLoanToPay> loans, String customer, int currYaz, Map<String, Customer> customers) {
        String id;
        List<Loan> relevantCustomerLoans = getSortedActiveAndInRiskLoansToCloseOfCustomer(customer);

        for (Loan a : relevantCustomerLoans) {
            id = a.getId();
            if (loans.containsKey(id))
                a.payLoan(currYaz, customers);
        }

    }

    public Loans getPendingAndNewLoans() {
        Loans res = new Loans();

        for (Loan loan : loans) { // get active and in risk loans
            if (loan.getStatus().getStatus().equals(Status.Statuses.Pending) || loan.getStatus().getStatus().equals(Status.Statuses.New)) // check if this method works well
                res.getLoans().add(loan);
        }
        return res;
    }

    public Loans getOptionalLoansForInvestment(String customer, int amount, List<String> category, int interest, int totalYaz, int amountLoanerLoans, Map<String, Customer> customers) throws CloneNotSupportedException {
        Loans res = getPendingAndNewLoans();
        Loans temp = (Loans) res.clone();

        for (int i = 0; i < temp.getLoans().size(); i++) {
            if (temp.getLoans().get(i).getOwner().getName().equals(customer)) //delete loans that the customer is loan;
                res.getLoans().remove(temp.getLoans().get(i));
                //לחשוב על התנאי עם הסכום
            else if (category != null && !checkIfLoanInTheCategory(category, temp.getLoans().get(i)))//delete loans that dont have one of the categories
                res.getLoans().remove(temp.getLoans().get(i));

            else if (interest != 0 && (temp.getLoans().get(i).getInterestPerPayment() < interest))
                res.getLoans().remove(temp.getLoans().get(i));

            else if (totalYaz != 0 && (temp.getLoans().get(i).getTotalYazTime() < totalYaz))
                res.getLoans().remove(temp.getLoans().get(i));

            if (customers.get(temp.getLoans().get(i).getOwner().getName()).getBorrowLoansWithoutFinish().getLoans().size() > amountLoanerLoans && (amountLoanerLoans != 0))
                res.getLoans().remove(temp.getLoans().get(i));
        }

        return res;
    }

    public Loans findLoans(PrintLoans printLoans) {
        Loans res = new Loans();

        for (Loan loan : this.loans) {
            for (PrintLoan printLoan : printLoans.getLoans()) {
                if (loan.getId().equals(printLoan.getId()))
                    res.getLoans().add(loan);
            }
        }
        return res;
    }

    private boolean checkIfLoanInTheCategory(List<String> categories, Loan loan) {
        if (categories.contains("All Categories"))
            return true;
        else
            return categories.contains(loan.getCategory());
    }

    public Loans findRelevantLoans(List<String> ids, Loans relevantLoans) {
        Loans res = new Loans();
        for (String id : ids) {
            for (Loan loan : relevantLoans.getLoans())
                if (loan.getId().equals(id))
                    res.getLoans().add(loan);
        }

        return res;
    }

    public int divideInvestment(int amount, Customer lender, int curYaz, int maxPrecent) throws CloneNotSupportedException {
        int amountPerLoan = amount / getLoans().size();
        amount = amount - (amountPerLoan * getLoans().size()); //what left to split

        Loans temp = (Loans) this.clone();

        while ((amountPerLoan > 0) && (temp.getLoans().size() != 0)) {
            amount += addInvestmentForLoans(temp, amountPerLoan, lender, curYaz, maxPrecent);
            temp = removeActiveLoans(temp);
            //remove
            if (temp.getLoans().size() != 0) {
                amountPerLoan = amount / temp.getLoans().size();
                amount = amount - (amountPerLoan * temp.getLoans().size());
            }
        }
        return amount;
    }

    public Loans removeActiveLoans(Loans loans) throws CloneNotSupportedException {
        Loans temp = (Loans) loans.clone();

        for (int i = 0; i < temp.getLoans().size(); i++) {
            if (temp.getLoans().get(i).getStatus().getStatus().equals(Status.Statuses.Active))
                loans.getLoans().remove(temp.getLoans().get(i));
        }

        return loans;
    }

    public int addInvestmentForLoans(Loans loans, int amount, Customer lender, int curYaz, int maxPrecent) {
        int left = 0;
        int investment = amount;
        double present;
        double maxAmountToInvest;
        List<Loan> toDelete = new ArrayList<>();
        for (Loan loan : loans.getLoans()) {
            if (maxPrecent == 0)
                maxAmountToInvest = loan.getCapital();
            else {
                present = maxPrecent / 100.0;
                maxAmountToInvest = loan.getCapital() * present;  //amount to raised or capital?
            }
            if (!(loan.getStatus().getStatus().equals(Status.Statuses.Active))) {
                if (amount > (int) maxAmountToInvest || (loan.getCapital() - loan.getStatus().getTotalAmountRaised()) < amount) {    //check the amount not big from the precent
                    investment = (int) min(maxAmountToInvest, loan.getCapital() - loan.getStatus().getTotalAmountRaised());
                    left += amount - investment;
                }
                if (investment <= maxAmountToInvest) {
                    loan.getStatus().updateTotalAmountRaised(investment);
                    loan.addLender(lender, investment);
                    loan.updateStatus(curYaz);
                }
                if (investment >= maxAmountToInvest)
                    toDelete.add(loan);
            }
        }
        if (toDelete.size() > 0) {
            for (Loan a : toDelete)
                loans.getLoans().remove(a);

        }

        return left;
    }

    public List<Loan> getAllLoansCustomerCanPayInCurrYaz(String customer, int currYaz) {
        return getSortedActiveAndInRiskLoansToPay(customer, currYaz);

    }

    public List<Loan> getSortedActiveAndInRiskLoansToCloseOfCustomer(String customer) {

        List<Loan> res = new ArrayList<>();

        for (Loan loan : loans) { // get active and in risk loans
            if ((loan.getStatus().getStatus().equals(Status.Statuses.Active) || loan.getStatus().getStatus().equals(Status.Statuses.InRisk)) && loan.getOwner().getName().equals(customer)) // check if this method works well
                res.add(loan);
        }

        return res;
    }

    public void putLoansInRisk(int currYaz,Map<String, Map<String, Loan>> loansOnSell) {

        List<Loan> loansToPay = getSortedActiveAndInRiskLoansToPutInRiskMode(currYaz);

        for (Loan a : loansToPay) {
            a.putLoanInRiskMode();
            findIfLoanToSellAndRemoveHer(a, loansOnSell);
        }

    }

    private void findIfLoanToSellAndRemoveHer(Loan loan,  Map<String, Map<String, Loan>> loansOnSell) {
        String id = loan.getId();
        loan.removeSellingLenders();
        if (loansOnSell != null) {


            for (Customer customer : loan.getLenders().keySet()) {
                if (loansOnSell.containsKey(customer.getName())) {
                    if (loansOnSell.get(customer.getName()).containsKey(id)) {
                        loansOnSell.get(customer.getName()).remove(id);

                    }
                }

            }

        }
    }

    private Loan findLoanById(String loanId) {
        for (Loan loan : loans) {
            if (loan.getId().equals(loanId))
                return loan;
        }
        return null;
    }
    private List<Loan> getSortedActiveAndInRiskLoansToPutInRiskMode(int currYaz) {

        List<Loan> res = getSortedActiveAndInRiskLoans();


        res.removeIf(a -> a.getStatus().getNextYazPayment() >= currYaz);

        return res;
    }

    private List<Loan> getSortedActiveAndInRiskLoans() {

        List<Loan> res = new ArrayList<>();

        for (Loan loan : loans) { // get active and in risk loans
            if (loan.getStatus().getStatus().equals(Status.Statuses.Active) || loan.getStatus().getStatus().equals(Status.Statuses.InRisk)) // check if this method works well
                res.add(loan);
        }

        return res;
    }

    public void closeLoans(Map<String, PrintLoanToPay> loans, String customer, int currYaz, Map<String, Customer> customers) {
        String id;

        for (Loan a : this.loans) {
            id = a.getId();
            if (loans.containsKey(id))
                a.closeLoan(currYaz, customers);
        }

    }



    public void paySelectedLoans(PrintLoansToPay loans, String customer, int currYaz, Map<String, Customer> customers) {
        String id;
        List<Loan> relevantCustomerLoans = getSortedActiveAndInRiskLoansToCloseOfCustomer(customer);

        for (Loan a : relevantCustomerLoans) {
            id = a.getId();
            for(PrintLoanToPay loan: loans.getLoansToPay()) {
                if (loan.getId().equals(id)) {
                    a.payLoan(currYaz, customers);
                    break;
                }
            }
        }

    }
    public void closeSelectedLoans(PrintLoansToPay loans, int currYaz, Map<String, Customer> customers) {
        String id;

        for (Loan a : this.loans) {
            id = a.getId();
            for(PrintLoanToPay loan: loans.getLoansToPay()) {
                if (loan.getId().equals(id)) {
                    a.closeLoan(currYaz, customers);
                    break;
                }
            }
        }

    }


}
