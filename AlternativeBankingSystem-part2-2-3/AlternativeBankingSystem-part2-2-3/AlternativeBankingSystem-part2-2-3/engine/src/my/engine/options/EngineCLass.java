package my.engine.options;

import data.*;
import my.engine.MyClasses.*;
import my.engine.exception.*;

import my.engine.jaxb.generatedEX3.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class EngineCLass implements Engine {

    private int currYaz = 1;
    private Descriptor descriptor = new Descriptor();
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "my.engine.jaxb.generatedEX3";
    private AdminAndCustomersRewindData adminRewindData;
    private boolean fileOpen = false;
    private boolean rewindMode = false;
    private int rewindYaz;
    //Map< customer name, divided by id to the loan itself >
    private Map<String, Map<String, Loan>> loansOnSell =new HashMap<>();

    public int getRewindYaz() {
        return this.rewindYaz;
    }


    public Map<String, Map<String, Loan>> getLoansOnSell() {
        return loansOnSell;
    }


    public boolean isRewindMode() {
        return rewindMode;
    }

    public void setCurrYaz(int currYaz) {
        this.currYaz = currYaz;
    }

    public void increaseCurrYaz() {
        this.currYaz++;
    }

    public void decreaseCurrYaz() {
        this.currYaz--;
    }

    public void startCountingYaz() {
        if (currYaz == 0)
            currYaz++;

    }

    public boolean isFileOpen() {
        return fileOpen;
    }

    public void setFileOpen(boolean fileOpen) {
        this.fileOpen = fileOpen;
    }

    public void setCurrYaz() {
        currYaz++;
    }

    public int getCurrYaz() {
        return this.currYaz;
    }

    @Override
    public synchronized void loadXml(String input, String name) throws CategoryException, PaymentRateException, CustomerException, JAXBException, FileNotFoundException, OwnerException, LoanIdException, CloneNotSupportedException {   //need to return description, or maybe it will be an Inheritance class?
        AbsDescriptor temp;

        temp = openXmlFile(input);

        if (temp != null) { //the file open , maybe need to be caught and exception
            descriptor.checkJaxbClass(temp);
            descriptor = (Descriptor) descriptor.copyJaxbClass(temp, name);
        }
    }

    public void addCustomer(String name) { //delete loginYaz
        descriptor.addCustomer(name);
        // saveCustomerAfterLogin(currYaz, name);
    }

    private AbsDescriptor openXmlFile(String input) throws FileNotFoundException, JAXBException {

        InputStream inputStream = new FileInputStream(input);

        return deserializeFrom(inputStream);

    }

    private static AbsDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (AbsDescriptor) u.unmarshal(in);
    }

    public PrintLoans getAllLoans() {
        PrintLoans res = getPrintLoans(descriptor.getLoans());
        return res;

    }

    public PrintLoans getAllCustomerLoans(String name) {
        PrintLoans res = getPrintLoans(descriptor.getCustomers().findCustomer(name).getBorrowerLoans());
        return res;

    }

    public double paySelectedLoans(PrintLoansToPay loans, String customer, int currYaz) {
        descriptor.getLoans().paySelectedLoans(loans, customer, currYaz, descriptor.getCustomers().getCustomers());
        return descriptor.getCustomers().getCustomers().get(customer).getBalance();
    }

    public double closeSelectedLoans(PrintLoansToPay loans, String customer, int currYaz) {
        descriptor.getLoans().closeSelectedLoans(loans, currYaz, descriptor.getCustomers().getCustomers());
        return descriptor.getCustomers().getCustomers().get(customer).getBalance();
    }

    /*public PrintLoans getAllLoans() {
        PrintLoans t = new PrintLoans();

        for(Loan loan:descriptor.getLoans().getLoans()){
            Status status=loan.getStatus();
            Map<PrintCustomer,PrintInvestment> payment= getPrintPayments(status.getPayments());

          PrintStatus newStatus=new PrintStatus(status.getLenders(),status.getStatus(),status.getActivationYaz(),status.getFinishedYaz(),status.getNextYazPayment(),
                  status.getTotalCapitalPaid(),status.getTotalInterestPaid(),status.getCapitalToPay(),status.getInterestToPay(),
                  status.getPayments(),status.getCapitalDebt(),status.getInterestDebt(),status.getNumOfLatePayments(),status.getTotalAmountRaised());
        }
        return t.getAllLoans(descriptor.getLoans());
    }*/
    public Map<PrintCustomer, PrintInvestment> getPrintLenders(Map<Customer, Investment> payments) {
        Map<PrintCustomer, PrintInvestment> res = new HashMap<>();

        for (Customer cus : payments.keySet()) {
            PrintCustomer customer = getPrintCustomer(cus);
            PrintInvestment investment = new PrintInvestment(payments.get(cus).getInvestment(), payments.get(cus).getPercent());
            res.put(customer, investment);
        }
        return res;
    }

    private PrintLoans getPrintLoans(Loans loans) {

        PrintLoans res = new PrintLoans();

        for (Loan loan : loans.getLoans()) {
            Status status = loan.getStatus();
            PrintStatus newStatus = getPrintStatus(status);

            PrintLoan newLoan = new PrintLoan(loan.getId(), loan.getOwner().getName(), loan.getCategory(), loan.getCapital(), loan.getTotalYazTime(), loan.interestCalculation(), newStatus, loan.getTotalAmount(), loan.interestToPayEveryYaz(), loan.capitalPayEveryYaz(), loan.getPaysEveryYaz());
            res.addLoan(newLoan);
        }
        return res;
    }

    public PrintCustomer getPrintCustomer(Customer customer) {
        List<PrintTransaction> transactions = getPrintTransaction(customer.getAccount().getTransactions());
        PrintAccount account = new PrintAccount(transactions);
        Loans borrowLoans = customer.getBorrowerLoans();
        List<String> borrowIdLoans = getIdLoans(borrowLoans);
        Loans lenderLoans = customer.getLenderLoans();
        List<String> lenderIdLoans = getIdLoans(lenderLoans);
        PrintCustomer res = new PrintCustomer(customer.getName(), account, borrowIdLoans, lenderIdLoans, customer.getBalance());

        return res;
    }

    public List<String> getIdLoans(Loans loans) {
        List<String> res = new ArrayList<>();

        for (Loan loan : loans.getLoans()) {
            res.add(loan.getId());
        }

        return res;
    }

    private PrintStatus getPrintStatus(Status status) {

        Map<PrintCustomer, PrintInvestment> lenders = getPrintLenders(status.getLenders());
        List<PrintPayment> payments = getPrintPyments(status.getPayments());
        PrintStatus.Statuses statuses = getStatuss(status.getStatus());
        List<PrintInvestment> investments = new ArrayList<>(lenders.values());

        return new PrintStatus(lenders.keySet(), investments, statuses, status.getActivationYaz(), status.getFinishedYaz(), status.getNextYazPayment(), status.getTotalCapitalPaid(), status.getTotalInterestPaid(), status.getCapitalToPay(), status.getInterestToPay(), payments, status.getCapitalDebt(), status.getInterestDebt(), status.getNumOfLatePayments(), status.getTotalAmountRaised());
    }

    private PrintStatus.Statuses getStatuss(Status.Statuses statuses) {

        String status = statuses.toString();

        PrintStatus.Statuses res = PrintStatus.Statuses.valueOf(status);
        return res;
    }

    private List<PrintPayment> getPrintPyments(List<Payment> payments) {
        List<PrintPayment> res = new ArrayList<>();

        for (Payment payment : payments) {
            PrintPayment newPayment = new PrintPayment(payment.getPaymentYaz(), payment.getCapital(), payment.getInterest(), payment.getSum());
            res.add(newPayment);
        }


        return res;
    }

    private List<PrintTransaction> getPrintTransaction(List<Transaction> transactions) {
        List<PrintTransaction> res = new ArrayList<>();
        if (transactions != null) {
            for (Transaction transaction : transactions) {
                PrintTransaction newTransaction = new PrintTransaction(transaction.getYaz(), transaction.getAmount(), transaction.getAction(), transaction.getBalanceBefore(), transaction.getBalanceAfter());
                res.add(newTransaction);
            }
        }
        return res;

    }

    public PrintCustomers getAllCustomers() {
        //check if casting work...
        List<Customer> customers = new ArrayList<>(descriptor.getCustomers().getCustomers().values());
        PrintCustomers res = getPrintCustomers(customers);

        return res;
    }

    public int printCustomers() {
        PrintCustomers customers = getAllCustomers();
        return customers.printAllCustomers();
    }

    public double getBalance(String customer) {
        return descriptor.getCustomers().getCustomers().get(customer).getBalance();
    }


    public double addMoneyToCustomer(String customer, int moneyToAdd) {

        descriptor.getCustomers().getCustomers().get(customer).transaction(currYaz, moneyToAdd, '+');
        // updateRewindData(customer, descriptor.getCustomers().getCustomers().get(customer).getBalance());

        return descriptor.getCustomers().getCustomers().get(customer).getBalance();

    }

    private void updateRewindData(String customerName, double newBalance) {
        adminRewindData.updateCustomerBalance(customerName, newBalance, currYaz);
    }

    public double withdrawMoneyFromAccount(String customer, int withdrawMoney) {
        descriptor.getCustomers().getCustomers().get(customer).transaction(currYaz, withdrawMoney, '-');
        //updateRewindData(customer, descriptor.getCustomers().getCustomers().get(customer).getBalance());
        return descriptor.getCustomers().getCustomers().get(customer).getBalance();
    }


    public String getCustomerName(int ind) {
        PrintCustomers customers = getAllCustomers();
        return customers.getCustomer(ind);
    }

    public Set<String> getCategories() {
        return descriptor.getCategories().getCategories();
    }


    public void updateYaz(int currYaz) { // happens every time admin increases yaz
        //save information till this yaz
        if (this.currYaz < currYaz)
            saveAdminRewindData();

        this.currYaz = currYaz; // update curr yaz
        descriptor.getLoans().putLoansInRisk(this.currYaz, loansOnSell); // put relevant loans in risk mode
    }

    public void saveAdminRewindData() {
        PrintCustomerAndBalance customersInThisYaz = getAllCustomersAndBalance();
        PrintLoans loansInThisYaz = getAllLoans();

        if (adminRewindData == null)
            adminRewindData = new AdminAndCustomersRewindData();

        adminRewindData.addCustomers(customersInThisYaz, currYaz);
        adminRewindData.addLoans(loansInThisYaz, currYaz);
        saveCustomersRewind();

    }

    public void setRewindYaz(int rewindYaz) {
        this.rewindYaz = rewindYaz;
    }

    public void saveAdminRewindDataAndStartRewindMode() {
        if (!rewindMode) {
            PrintCustomerAndBalance customersInThisYaz = getAllCustomersAndBalance();
            PrintLoans loansInThisYaz = getAllLoans();

            if (adminRewindData == null)
                adminRewindData = new AdminAndCustomersRewindData();

            adminRewindData.addCustomers(customersInThisYaz, currYaz);
            adminRewindData.addLoans(loansInThisYaz, currYaz);
            saveCustomersRewind();
            rewindMode = true;
        } else
            rewindMode = false;
    }

    private void saveCustomersRewind() {
        CustomersRewindData temp = new CustomersRewindData();

        for (Customer customer : descriptor.getCustomers().getCustomers().values()) {
            String name = customer.getName();
            double balance = customer.getBalance();
            PrintLoans LenderLoans = getPrintLoans(customer.getLenderLoans());
            PrintLoans loanerLoans = getPrintLoans(customer.getBorrowerLoans());
            LoansOnSell sellLoans = findLoansCustomerCanSell(name);
            LoansOnSell buyLoans = gettingLoansCustomerCanBuy(name);
            PrintAccount account = new PrintAccount(getPrintTransaction(customer.getAccount().getTransactions()));
            CustomerRewindData newCustomerToSave = new CustomerRewindData(name, balance, LenderLoans, loanerLoans, sellLoans, buyLoans, account);
            temp.getCustomerRewindDataList().add(newCustomerToSave);
        }
        adminRewindData.saveCustomersData(temp, currYaz);
    }

    public AdminAndCustomersRewindData getAdminRewindData() {
        return adminRewindData;
    }

    public PrintCustomerAndBalance getCustomerInRewindMode(int currYaz) {
        return adminRewindData.getCustomerAndBalanceThisYaz(currYaz);
    }

    public PrintLoans getLoansInRewindMode(int currYaz) {
        return adminRewindData.getLoansInThisYaz(currYaz);
    }

    private void saveCustomerAfterLogin(int loginYaz, String customerName) {
        if (adminRewindData == null)
            adminRewindData = new AdminAndCustomersRewindData();

        adminRewindData.addCustomersInLoginYaz(loginYaz, customerName);
    }

    public int getYaz() {
        return currYaz;
    }

    public PrintLoans getOptionalLoansForInvestment(String customer, int amount, List<String> category, int interest, int totalYaz, int amountLoanerLoans) {
        //return the lender in basic way think how...
        try {
            Loans loans = descriptor.getLoans().getOptionalLoansForInvestment(customer, amount, category, interest, totalYaz, amountLoanerLoans, descriptor.getCustomers().getCustomers());
            PrintLoans res = getPrintLoans(loans);
            return res;
        } catch (CloneNotSupportedException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Integer> inlay(List<String> ids, int amount, String customer, PrintLoans printRelevantLoans, int maxPrecent) throws CloneNotSupportedException {
        Loans relevantLoans = descriptor.getLoans().findLoans(printRelevantLoans);
        relevantLoans = descriptor.getLoans().findRelevantLoans(ids, relevantLoans);
        Customer lender = descriptor.getCustomers().findCustomer(customer);
        int left = relevantLoans.divideInvestment(amount, lender, currYaz, maxPrecent);
        descriptor.getCustomers().getCustomers().get(customer).transaction(currYaz, amount - left, '-');
        descriptor.getCustomers().getCustomers().get(customer).setLenderLoans(relevantLoans);
        List<Integer> res = new ArrayList<>();
        double balance = descriptor.getCustomers().findCustomer(customer).getBalance();
        res.add(left);
        res.add((int) balance);

        return res;
    }

    public PrintCustomer getCustomer(String customer) {
        Customer newCustomer = descriptor.getCustomers().getCustomers().get(customer);
        return getPrintCustomer(newCustomer);
    }

    public PrintTransactions getAllTransactions(String name) {
        List<PrintTransaction> transactions = getPrintTransaction(descriptor.getCustomers().findCustomer(name).getAccount().getTransactions());
        PrintAccount account = new PrintAccount(transactions);
        return new PrintTransactions(account);
    }

    public PrintCustomerAndBalance getAllCustomersAndBalance() {
        List<Customer> customers = new ArrayList<>(descriptor.getCustomers().getCustomers().values());
        List<List<String>> temp = new ArrayList<>();
        if (customers != null) {
            for (Customer customer : customers) {
                List<String> newCus = new ArrayList<>();
                newCus.add(customer.getName());
                newCus.add(String.valueOf(customer.getBalance()));
                temp.add(newCus);
            }
            return new PrintCustomerAndBalance(temp);
        } else
            return null;
    }

    public PrintLoansToPay getLoansToPayInCurrYaz(String customer, int currYaz) {
        Loans temp = new Loans();
        temp.setLoans(descriptor.getLoans().getAllLoansCustomerCanPayInCurrYaz(customer, currYaz));
        PrintLoans loansToPay = getPrintLoans(temp);
        return new PrintLoansToPay(loansToPay.getLoans(), true);
    }

    public PrintLoansToPay getLoansToPayInRewindMode(String customerName, int currYaz) {
        return adminRewindData.payInThisYazRewindData(customerName, currYaz);
    }

    public PrintLoansToPay getLoansToClose(String customer) {
        Loans temp = new Loans();
        temp.setLoans(descriptor.getLoans().getSortedActiveAndInRiskLoansToCloseOfCustomer(customer));
        PrintLoans loansToPay = getPrintLoans(temp);
        return new PrintLoansToPay(loansToPay.getLoans(), false);
    }

    public PrintLoansToPay getLoansToCloseInRewindMode(String customerName, int currYaz) {
        return adminRewindData.closeLoansRewindData(customerName, currYaz);
    }

    public void closeLoans(Map<String, PrintLoanToPay> loans, String customer, int currYaz) {
        descriptor.getLoans().closeLoans(loans, customer, currYaz, descriptor.getCustomers().getCustomers());
    }

    public void payLoans(Map<String, PrintLoanToPay> loans, String customer, int currYaz) {
        descriptor.getLoans().payLoans(loans, customer, currYaz, descriptor.getCustomers().getCustomers());
    }

    public void addNewLoan(String customerName, String loanId, String category, String capital, String totalYaz, String payEveryYaz, String interestPerPayment) throws LoanIdException, PaymentRateException {
        Customer customer = descriptor.getCustomers().findCustomer(customerName);
        descriptor.addCategory(category);

        if (!findId(loanId))
            throw new LoanIdException(loanId);

        if (!checkPaymentRate(Integer.parseInt(payEveryYaz), Integer.parseInt(totalYaz)))
            throw new PaymentRateException(loanId);

        Loan newLoan = new Loan(customer, category, Integer.parseInt(capital), Integer.parseInt(totalYaz), Integer.parseInt(payEveryYaz), Integer.parseInt(interestPerPayment), loanId);

        customer.addBorrowLoan(newLoan);
        descriptor.getLoans().getLoans().add(newLoan);
    }

    private boolean findId(String loanId) {
        for (Loan loan : descriptor.getLoans().getLoans()) {
            if (loan.getId().equals(loanId))
                return false;
        }
        return true;
    }

    private Loan findLoanById(String loanId) {
        for (Loan loan : descriptor.getLoans().getLoans()) {
            if (loan.getId().equals(loanId))
                return loan;
        }
        return null;
    }

    public boolean checkPaymentRate(int paysEveryYaz, int totalYazTime) {
        return (totalYazTime % paysEveryYaz) == 0;
    }

    /**************************************************************************************************************************/

    public PrintCustomers getPrintCustomers(List<Customer> customers) {
        PrintCustomers res = new PrintCustomers();

        for (Customer customer : customers) {
            PrintCustomer temp = getPrintCustomer(customer);
            res.addCustomer(temp);
        }
        return res;
    }

    public PrintLoans getAllCustomerLoanerLoans(String name) {
        PrintLoans res = getPrintLoans(descriptor.getCustomers().findCustomer(name).getBorrowerLoans());
        return res;

    }

    public PrintLoans getAllCustomerLoanerLoansInRewindMode(String name, int currYaz) {
        CustomersRewindData customersRewindDataThisYaz = adminRewindData.getCustomersRewindThisYaz(currYaz);
        if (customersRewindDataThisYaz != null) {
            PrintLoans res = customersRewindDataThisYaz.getCustomerRewindDataList(name).getBorrowLoans();
            return res;
        } else
            return null;

    }

    public PrintLoans getAllCustomerLenderLoansInRewindMode(String name, int currYaz) {
        CustomersRewindData customersRewindDataThisYaz = adminRewindData.getCustomersRewindThisYaz(currYaz);
        if (customersRewindDataThisYaz != null) {
            PrintLoans res = customersRewindDataThisYaz.getCustomerRewindDataList(name).getLenderLoans();
            return res;
        } else
            return null;
    }

    public double getBalanceInRewindMode(String name, int currYaz) {
        CustomersRewindData customersRewindDataThisYaz = adminRewindData.getCustomersRewindThisYaz(currYaz);
        if (customersRewindDataThisYaz != null) {
            double res = customersRewindDataThisYaz.getCustomerRewindDataList(name).getBalance();
            return res;
        } else
            return 0;

    }

    public PrintTransactions getTransactionInRewindMode(String name, int currYaz) {
        CustomersRewindData customersRewindDataThisYaz = adminRewindData.getCustomersRewindThisYaz(currYaz);
        if (customersRewindDataThisYaz != null) {
            PrintTransactions res = new PrintTransactions(customersRewindDataThisYaz.getCustomerRewindDataList(name).getAccount());
            return res;
        } else
            return null;
    }

    public LoansOnSell getLoanToSellInRewindMode(String name, int currYaz) {
        CustomersRewindData customersRewindDataThisYaz = adminRewindData.getCustomersRewindThisYaz(currYaz);
        if (customersRewindDataThisYaz != null) {
            LoansOnSell res = customersRewindDataThisYaz.getCustomerRewindDataList(name).getSellLoans();
            return res;
        } else
            return null;
    }

    public LoansOnSell getLoanOnSellInRewindMode(String name, int currYaz) {
        CustomersRewindData customersRewindDataThisYaz = adminRewindData.getCustomersRewindThisYaz(currYaz);
        if (customersRewindDataThisYaz != null) {
            LoansOnSell res = customersRewindDataThisYaz.getCustomerRewindDataList(name).getBuyLoans();
            return res;
        } else
            return null;
    }

    public PrintLoans getAllCustomerLenderLoans(String name) {
        PrintLoans res = getPrintLoans(descriptor.getCustomers().findCustomer(name).getLenderLoans());
        return res;

    }

    /*  public Map<String, Double> findLoanToSell(String customerName) { //Map<loan id,sell price>

          Map<String, Double> res = new HashMap<>();
          Customer customer = descriptor.getCustomers().findCustomer(customerName);
          List<Loan> loanToSell = customer.findLoanToSell();

          for (Loan loan : loanToSell) {
              String id = loan.getId();
              Double sellPrice = calculateSellingPrice(loan, customer);
              res.put(id, sellPrice);
          }
          return res;
      }*/
    public LoansOnSell findLoansCustomerCanSell(String customerName) {

        LoansOnSell res = new LoansOnSell();


       // Map<String, Double> res = new HashMap<>();
        Customer customer = descriptor.getCustomers().findCustomer(customerName);
        List<Loan> activeLoans = customer.findActiveLoans();
        List<Loan> optionalLoans = new ArrayList<>();

        Map<String, Loan> customerLoansOnSell = new HashMap<>();

          if (loansOnSell.containsKey(customerName)) {
              customerLoansOnSell = loansOnSell.get(customerName);

              for (Loan loan : activeLoans) {
                  if (!customerLoansOnSell.containsKey(loan.getId()))
                      optionalLoans.add(loan);
              }
          }

        else
            optionalLoans = activeLoans;

        for (Loan loan : optionalLoans) {
            String id = loan.getId();
            double sellPrice = calculateSellingPrice(loan, customer);
            res.addLoan(id, sellPrice, customerName);
        }
        return res;
    }

    private double calculateSellingPrice(Loan loan, Customer customer) {

        Investment investment = loan.getLenders().get(customer);
        int capitalToPay = loan.getStatus().getCapitalToPay();

        return capitalToPay * 1.0 / (investment.getPercent() / 100.0);

    }

    public void updateLoanToSell(LoansOnSell loansToSell,String sellCustomerName) {
        //check loan not in risk mode?
        addLoansOnSellForCustomer(loansToSell.getLoans(),sellCustomerName);
        //updateLoansOnSell();
    }

/*    private void updateLoansOnSell() {

        List<SellingLoansData> loanOnSell = descriptor.getLoans().getLoansOnSell();
        for (SellingLoansData sellingLoansData : loanOnSell) {
            for (SellingLoanData sellingLoanData : sellingLoansData.getLoansOnSell()) {
                String id = sellingLoanData.getLoanId();
                Loan loan = findLoanById(id);
                if (loan != null)
                    loan.setOnSell(true);

            }
        }
    }*/

    /*   public Map<String, Double> gettingLoanOnSell(String customerName) {

           List<SellingLoansData> temp = descriptor.getLoans().getLoansOnSell();
           Map<String, Double> res = new HashMap<>();
           if (temp != null) {
               for (SellingLoansData sellingLoansData : temp) {
                   if (!sellingLoansData.getCustomerName().equals(customerName))
                       for (SellingLoanData sellingLoanData : sellingLoansData.getLoansOnSell()) {
                           String loanId = sellingLoanData.getLoanId();
                           Double price = sellingLoanData.getSellingPrice();
                           if (!loanNotBelongToCustomer(loanId, customerName))
                               res.put(loanId, price);
                       }
               }
           }
           return res;
       }*/
    // loans
    public LoansOnSell gettingLoansCustomerCanBuy(String customerName) {
        LoansOnSell res = new LoansOnSell();

        Map<String, Map<String, Loan>> loansOnSell = getLoansOnSell();

        if (loansOnSell != null) {
            for (Map.Entry<String, Map<String, Loan>> entry : loansOnSell.entrySet()) {
                if (!entry.getKey().equals(customerName)) {
                    for (Loan loan : entry.getValue().values()) {
                        if (!loan.getOwner().getName().equals(customerName)) {
                            double price = loan.getSellingLenders().get(entry.getKey());
                            String sellingOwner = entry.getKey();
                            String id = loan.getId();
                            res.addLoan(id, price, sellingOwner);

                        }
                    }
                }
            }
        }
        return res;
    }

   /* private boolean loanNotBelongToCustomer(String id, String customerName) {
        Loan temp = findLoanById(id);
        if (temp != null)
            return temp.getOwner().getName().equals(customerName);
        else
            return false;//need to be exception...
    }*/

    public void buyLoanOnSell(String buyerName, LoansOnSell loansToBuy) { // map String - customer selling the loan, Selling id + amount

        // 1.need to find the loan in the buying list(loansToBuy).
        // 2.move the lender to this loan to buyerName.
        // 3.charge many to the original lender and withdraw many from the buyer.
        // 4.remove loan from the buy page.

        for (SellingLoanData loanToBuy : loansToBuy.getLoans()) {
            Loan loan = findLoanById(loanToBuy.getLoanId());
            Customer buyer = descriptor.getCustomers().findCustomer(buyerName);
            Customer seller = descriptor.getCustomers().findCustomer(loanToBuy.getSellingCustomer());

            Investment investment = loan.getLenders().get(seller);

            seller.getLenderLoans().getLoans().remove(loan);
            buyer.getLenderLoans().getLoans().add(loan);

            loan.getLenders().remove(seller);
            loan.removeSellingLender(loanToBuy.getSellingCustomer());


            loan.getLenders().put(buyer, investment);

            makeSell(buyer, seller, loanToBuy.getSellingPrice());
            deleteSellingLoan(loanToBuy.getSellingCustomer(), loanToBuy.getLoanId());

        }

    }

    private void makeSell(Customer buyer, Customer seller, double price) {

        seller.transaction(currYaz, price, '+');
        buyer.transaction(currYaz, price, '-');

    }

    public double buyLoans(String buyerName, LoansOnSell toBuy) {

        buyLoanOnSell(buyerName, toBuy);
        return descriptor.getCustomers().getCustomers().get(buyerName).getBalance();

    }


    private void addLoansOnSellForCustomer(List<SellingLoanData> sellLoans,String sellCustomerName) {//add loans to spesific name and make the original loan on sell
        if (loansOnSell == null)
            loansOnSell = new HashMap<>();

        String id;
        Map<String, Loan> temp = new HashMap<>();

        for (Loan loan : descriptor.getLoans().getLoans()) {
            id = loan.getId();
            for (SellingLoanData data : sellLoans) {
                if (data.getLoanId().equals(id)) {
                    loan.addSellingLender(data.getSellingCustomer(), data.getSellingPrice());
                    temp.put(id, loan);
                }
            }
        }
        if (!loansOnSell.containsKey(sellCustomerName)) {
            loansOnSell.put(sellCustomerName, temp);
        } else {

            loansOnSell.get(sellCustomerName).putAll(temp);
        }


    }


    private void deleteSellingLoan(String sellingCustomer, String id) {
        List<SellingLoansData> newSellingLoan = new ArrayList<>();

        loansOnSell.get(sellingCustomer).remove(id);
    }
}