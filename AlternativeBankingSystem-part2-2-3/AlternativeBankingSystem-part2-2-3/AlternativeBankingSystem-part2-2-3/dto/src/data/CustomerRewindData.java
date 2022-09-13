package data;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomerRewindData {
    private String name;
    private double balance;
    private PrintLoans lenderLoans;
    private PrintLoans borrowLoans;
    private LoansOnSell sellLoans;  //change to the object
    private LoansOnSell buyLoans;  //need to change loans to sell and buy from new object to all information about loans , after do this take care about save buy loans
    private PrintAccount account;

    public CustomerRewindData(String name, double balance, PrintLoans idLenderLoans, PrintLoans idBorrowLoans, LoansOnSell idSellLoans, LoansOnSell idBuyLoans,PrintAccount account) {
        this.name = name;
        this.balance = balance;
        this.lenderLoans = idLenderLoans;
        this.borrowLoans = idBorrowLoans;
        this.sellLoans = idSellLoans;
        this.buyLoans = idBuyLoans;
        this.account = account;
    }

    public PrintAccount getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public PrintLoans getLenderLoans() {
        return lenderLoans;
    }

    public void setLenderLoans(PrintLoans lenderLoans) {
        this.lenderLoans = lenderLoans;
    }

    public PrintLoans getBorrowLoans() {
        return borrowLoans;
    }

    public void setBorrowLoans(PrintLoans borrowLoans) {
        this.borrowLoans = borrowLoans;
    }

    public  LoansOnSell getSellLoans() {
        return sellLoans;
    }


    public LoansOnSell getBuyLoans() {
        return buyLoans;
    }

}
