package data;

public class PrintTransactionsAndBalance {
    PrintTransactions printTransactions;
    double balance;

    public PrintTransactionsAndBalance(){}
    public PrintTransactionsAndBalance(PrintTransactions printTransactions, double balance) {
        this.printTransactions = printTransactions;
        this.balance = balance;
    }

    public PrintTransactions getPrintTransactions() {
        return printTransactions;
    }

    public double getBalance() {
        return balance;
    }
}
