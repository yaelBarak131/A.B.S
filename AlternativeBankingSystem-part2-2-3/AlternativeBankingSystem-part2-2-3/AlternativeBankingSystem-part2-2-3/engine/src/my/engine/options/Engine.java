package my.engine.options;

import data.*;
import my.engine.MyClasses.Customer;
import my.engine.MyClasses.Loan;
import my.engine.exception.*;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Engine {
    boolean isFileOpen();

    boolean isRewindMode();

    void startCountingYaz();

    void setCurrYaz(int currYaz);

    void decreaseCurrYaz();

    int getRewindYaz();

    void increaseCurrYaz();

    void setFileOpen(boolean fileOpen);

    void loadXml(String input, String name) throws CategoryException, PaymentRateException, CustomerException, JAXBException, FileNotFoundException, OwnerException, LoanIdException, CloneNotSupportedException;   //need to return description, or maybe it will be an Inheritance class?

    PrintLoans getAllLoans();

    PrintLoans getAllCustomerLoans(String name);

    double paySelectedLoans(PrintLoansToPay loans, String customer, int currYaz);

    PrintCustomer getPrintCustomer(Customer customer);

    double closeSelectedLoans(PrintLoansToPay loans, String customer, int currYaz);

    int printCustomers();


    double addMoneyToCustomer(String customer, int moneyToAdd);

    double withdrawMoneyFromAccount(String customer, int withdrawMoney);

    void addCustomer(String name);

    void saveAdminRewindData();

    void saveAdminRewindDataAndStartRewindMode();

    void setRewindYaz(int rewindYaz);

    PrintCustomers getAllCustomers();

    String getCustomerName(int ind);

    double getBalance(String customer);

    Set<String> getCategories();

    void updateYaz(int currYaz);

    PrintCustomerAndBalance getCustomerInRewindMode(int currYaz);

    PrintLoans getLoansInRewindMode(int currYaz);

    LoansOnSell findLoansCustomerCanSell(String customerName);

    void updateLoanToSell(LoansOnSell loansToSell,String sellCustomerName);

    LoansOnSell gettingLoansCustomerCanBuy(String customerName);


    int getYaz();

    List<Integer> inlay(List<String> ids, int amount, String customer, PrintLoans relvantLoans, int maxPrecent) throws CloneNotSupportedException;

    PrintTransactions getAllTransactions(String name);

    PrintCustomerAndBalance getAllCustomersAndBalance();

    PrintCustomer getCustomer(String customer);

    PrintLoans getOptionalLoansForInvestment(String customer, int amount, List<String> category, int interest, int totalYaz, int amountLoanerLoans);

    PrintLoansToPay getLoansToPayInCurrYaz(String customer, int currYaz);

    PrintLoansToPay getLoansToPayInRewindMode(String customerName, int currYaz);

    PrintLoansToPay getLoansToClose(String customer);

    PrintLoansToPay getLoansToCloseInRewindMode(String customerName, int currYaz);

    void closeLoans(Map<String, PrintLoanToPay> loans, String customer, int currYaz);

    void payLoans(Map<String, PrintLoanToPay> loans, String customer, int currYaz);

    PrintLoans getAllCustomerLoanerLoans(String name);

    PrintLoans getAllCustomerLenderLoans(String name);

    PrintLoans getAllCustomerLoanerLoansInRewindMode(String name, int currYaz);

    PrintLoans getAllCustomerLenderLoansInRewindMode(String name, int currYaz);

    LoansOnSell getLoanToSellInRewindMode(String name, int currYaz);

    PrintTransactions getTransactionInRewindMode(String name, int currYaz);

    double getBalanceInRewindMode(String name, int currYaz);

    LoansOnSell getLoanOnSellInRewindMode(String name, int currYaz);

    void addNewLoan(String customerName, String loanId, String category, String capital, String totalYaz, String payEveryYaz, String interestPerPayment);

    double buyLoans(String buyerName, LoansOnSell toBuy);

}
