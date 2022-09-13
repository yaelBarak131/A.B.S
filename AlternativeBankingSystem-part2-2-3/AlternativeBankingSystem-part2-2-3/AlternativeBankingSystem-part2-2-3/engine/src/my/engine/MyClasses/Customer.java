package my.engine.MyClasses;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    //need map? maybe if we need to search something about costumer informtion..
    private double balance;
    private String name;
    private Account account = new Account();
    private Loans borrowerLoans = new Loans();//ההלוואת שאתה לווה
    private Loans lenderLoans = new Loans();//ההלוואת שאתה מלווה

    /**
     * Gets the value of the Balance property.
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * Sets the value of the Balance property.
     */
    public void setBalance(int value) {
        this.balance = value;
    }

    /**
     * Gets the value of the name property.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     */
    public void setName(String value) {
        this.name = value;
    }

    public Customer copy(String name) {

        this.name = name;
        this.balance = 0;
        return this;

    }

    public void transaction(int curYaz, double amount, char action) {
        double newBalance = calculateNewBalance(amount, action);
        account.addTransaction(curYaz, amount, action, balance, newBalance);
        balance = newBalance;
    }

    private double calculateNewBalance(double amount, char action) {
        double newBalance = 0;

        switch (action) {
            case '+': {
                newBalance = balance + amount;
                break;
            }
            case '-': {
                newBalance = balance - amount;
                break;
            }
        }
        return newBalance;
    }


    public void updateAllLenderLoan(Loans loans) {
        for (Loan loan : loans.getLoans()) {
            if (loan.getLenders().containsKey(this) && !(lenderLoans.getLoans().contains(loan)))
                lenderLoans.getLoans().add(loan);
        }

    }

    public void updateAllBorrowerLoan(Loans loans) {

        for (Loan loan : loans.getLoans()) {
            if (loan.getOwner().getName().equals(name) && !(borrowerLoans.getLoans().contains(loan)))
                borrowerLoans.getLoans().add(loan);
        }
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Loans getBorrowerLoans() {
        return borrowerLoans;
    }

    public void setBorrowerLoans(Loans borrowerLoans) {
        if(this.borrowerLoans == null)
            this.borrowerLoans = new Loans();

        this.borrowerLoans.getLoans().addAll(borrowerLoans.getLoans());
    }

    public Loans getLenderLoans() {
        return lenderLoans;
    }

    public void setLenderLoans(Loans lenderLoans) {
        if(this.lenderLoans == null)
            this.lenderLoans = new Loans();

        this.lenderLoans.getLoans().addAll(lenderLoans.getLoans());

    }

    public Loans getBorrowLoansWithoutFinish(){
        Loans res = new Loans();

        for(Loan loan:borrowerLoans.getLoans()){
            if(!loan.getStatus().getStatus().name().equals("Finish"))
                res.getLoans().add(loan);
        }
        return res;
    }

    public void addBorrowLoan(Loan loan){
        borrowerLoans.getLoans().add(loan);
    }

    public List<Loan> findActiveLoans(){

        List<Loan> res = new ArrayList<>();

        for(Loan loan: lenderLoans.getLoans())
        {
            if(loan.getStatus().getStatus().equals(Status.Statuses.Active))
                res.add(loan);
        }
        return res;
    }


}
