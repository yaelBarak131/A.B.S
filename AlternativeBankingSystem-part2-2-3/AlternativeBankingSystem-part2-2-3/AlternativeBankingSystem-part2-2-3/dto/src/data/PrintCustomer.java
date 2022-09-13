package data;


import java.util.List;

public class PrintCustomer {

    private String name;
    private PrintAccount account ;
    //private Account account = new Account();
    private List<String> borrowerLoans;//ההלוואת שאתה לווה
    private List<String> lenderLoans;//ההלוואת שאתה מלווה
    private double balance;

    public PrintCustomer(String name, PrintAccount account, List<String> borrowerLoans, List<String> lenderLoans,double balance) {
        this.name = name;
        this.account = account;
        this.borrowerLoans = borrowerLoans;
        this.lenderLoans = lenderLoans;
        this.balance = balance;

    }

    public String getName() {
        return name;
    }

    public PrintAccount getAccount() {
        return account;
    }
    public double getBalance(){
        return balance;
    }

    public List<String> getBorrowerIdLoans(){ return borrowerLoans;}
    public List<String> getLenderIdLoans(){return lenderLoans;}

    @Override
    public String toString() {
        return "PrintCustomer{" +
                "name='" + name + '\'' +
                ", account=" + account +
                ", borrowerLoans=" + borrowerLoans +
                ", lenderLoans=" + lenderLoans +
                ", balance=" + balance +
                '}';
    }
}
