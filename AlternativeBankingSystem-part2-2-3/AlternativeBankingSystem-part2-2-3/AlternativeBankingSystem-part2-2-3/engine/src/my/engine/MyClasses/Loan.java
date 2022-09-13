package my.engine.MyClasses;

import my.engine.exception.CategoryException;
import my.engine.exception.OwnerException;
import my.engine.exception.PaymentRateException;
import my.engine.jaxb.generatedEX3.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Loan {


    private Customer owner;
    private String category;
    private int capital;//קרן
    private int totalYazTime;
    private int paysEveryYaz;
    private int interestPerPayment; //ריבית
    private String id;//השם הייחודי של ההלואה
    private Map<Customer, Investment> lenders = new HashMap<>();//Map<loner,Map<investment,Several percent of the capital>
    private Map<String, Double> sellingLenders = new HashMap<>();
    private Status status;
    private int currentPayment = 0;
    private boolean onSell = false;

    public Map<String, Double> getSellingLenders() {
        return sellingLenders;
    }
    public void removeSellingLender(String customerName){
        sellingLenders.remove(customerName);
    }

    public Loan() {
    }
    public void addSellingLender(String customerName, double sellingPrice){
        this.sellingLenders.put(customerName, sellingPrice);
    }

    public void removeSellingLenders(){
        this.sellingLenders = new HashMap<>();
    }


    public Loan(Customer owner, String category, int capital, int totalYazTime, int paysEveryYaz, int interestPerPayment, String id) {
        this.owner = owner;
        this.category = category;
        this.capital = capital;
        this.totalYazTime = totalYazTime;
        this.paysEveryYaz = paysEveryYaz;
        this.interestPerPayment = interestPerPayment;
        this.id = id;
        this.status = new Status(Status.Statuses.New, this.capital, interestCalculation());
    }

    public boolean isOnSell() {
        return onSell;
    }

    public void setOnSell(boolean onSell) {
        this.onSell = onSell;
    }

    /**
     * Gets the value of the owner property.
     */


    public Customer getOwner() {
        return this.owner;
    }

    /**
     * Sets the value of the owner property.
     */

    public void setOwner(Customer value) {
        this.owner = value;
    }

    /**
     * Gets the value of the category property.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     */
    public void setCategory(String value) {
        this.category = value;
    }

    /**
     * Gets the value of the capital property.
     */
    public int getCapital() {
        return capital;
    }

    /**
     * Sets the value of the capital property.
     */
    public void setCapital(int value) {
        this.capital = value;
    }

    /**
     * Gets the value of the totalYazTime property.
     */
    public int getTotalYazTime() {
        return totalYazTime;
    }

    /**
     * Sets the value of the totalYazTime property.
     */
    public void setTotalYazTime(int value) {
        this.totalYazTime = value;
    }

    /**
     * Gets the value of the paysEveryYaz property.
     */
    public int getPaysEveryYaz() {
        return paysEveryYaz;
    }

    /**
     * Sets the value of the paysEveryYaz property.
     */
    public void setPaysEveryYaz(int value) {
        this.paysEveryYaz = value;
    }

    /**
     * Gets the value of the interestPerPayment property.
     */
    public int getInterestPerPayment() {
        return interestPerPayment;
    }

    /**
     * Sets the value of the interest PerPayment property.
     */
    public void setInterestPerPayment(int value) {
        this.interestPerPayment = value;
    }

    /**
     * Gets the value of the id property.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     */
    public void setId(String value) {
        this.id = value;
    }

//    public Customer setCustomer(String name, Customers customers) {
//
//        for (AbsCustomer a : absCustomers.getAbsCustomer()) {
//            if (a.getName().equals(name))
//                return new Customer().copy(a);
//        }
//        return null;
//    }

    public Loan setNewLoan(AbsLoan absLoan, String name) throws CategoryException, PaymentRateException, OwnerException {
        //      lenders.clear();

        this.capital = absLoan.getAbsCapital();
        this.id = absLoan.getId();
        this.category = absLoan.getAbsCategory();
        this.interestPerPayment = absLoan.getAbsIntristPerPayment();
        this.owner = new Customer().copy(name); // check that this works
        this.paysEveryYaz = absLoan.getAbsPaysEveryYaz();
        this.totalYazTime = absLoan.getAbsTotalYazTime();

        status = new Status(Status.Statuses.New, capital, interestCalculation());
        lenders = new HashMap<>();

        return this;

    }

    public void checkLoan(AbsLoan absLoan, AbsCategories absCategories, Set<String> categories) throws CategoryException, PaymentRateException, OwnerException {

        checkCategoryExists(absLoan.getAbsCategory(), categories, absCategories);
        checkPaymentRate(absLoan.getAbsPaysEveryYaz(), absLoan.getAbsTotalYazTime());


    }

    public int getTotalAmount() {
        return capital + interestCalculation();
    }
//    public void checkOwner(String name, AbsCustomers absCostumers) throws OwnerException {
//        List<AbsCustomer> temp = absCostumers.getAbsCustomer();
//        Boolean found = false;
//        for (AbsCustomer t : temp) {
//            if (t.getName().equals(name))
//                found = true;
//        }
//        if (!found)
//            throw new OwnerException(name);
//    }

    public void checkCategoryExists(String absCategory, Set<String> categories, AbsCategories absCategories) throws CategoryException {

        if (!absCategories.getAbsCategory().contains(absCategory))
            if (categories != null) {
                if (!categories.contains(absCategory)) {
                    throw new CategoryException(absCategory);
                }
            }
    }

    //
    public void checkPaymentRate(int absPaysEveryYaz, int absTotalYazTime) throws PaymentRateException {
        if (!((absTotalYazTime % absPaysEveryYaz) == 0))
            throw new PaymentRateException(id);
    }


    /**
     * מחזיר העתק או את הmap עצמו?
     */
    public Map<Customer, Investment> getLenders() {
        return lenders;
    }

    public void addLender(Customer lender, int investment) {
        if (lenders == null)
            lenders = new HashMap<>();

        double percent = (investment * 1.0 / capital) * 100.0;
        Investment temp = new Investment(investment, percent);

        if (lenders.containsKey(lender)) {
            int oldInvest = lenders.get(lender).getInvestment();
            investment += oldInvest;
            percent = (investment * 1.0 / capital) * 100;
            temp = new Investment(investment, percent);
            lenders.get(lender).updateInvestment(temp);
        } else {
            lenders.put(lender, temp);
        }
        status.setLenders(lenders);
    }

    //  public void setTotalInterestPaid(){
    //    this.totalInterestPaid = currentPayment * interestPaidEveryYaz();  //מספר התשלומים שעברו * כמה ריבית משלמים בכל תשלום
    //}

    //public int getTotalInterestPaid() { //חישוב כמה ריבית שולמה
    //   return this.totalInterestPaid;
    // }

    public int interestCalculation() { //חישוב כמה ריבית צריך לשלם(מעבר מאחוזים לכסף)
        double present = interestPerPayment / 100.0;
        double sum = capital * present;
        return (int) sum;
    }

    public int interestToPayEveryYaz() { //תשלום הרבית בכל יז שצריך לשלם
        int interest = interestCalculation();
        int howManyPayments = totalYazTime / paysEveryYaz;

        return interest / howManyPayments;
    }


    public int capitalPayEveryYaz() { // תשלום הקרן בכל יז שצריך לשלם
        int x = capital / totalYazTime;
        return x * paysEveryYaz;
    }


    public void newActiveModePayment(int curYaz) {
        int sum = capitalPayEveryYaz() + interestToPayEveryYaz();
        Payment payment = new Payment(curYaz, capitalPayEveryYaz(), interestToPayEveryYaz(), sum);
        status.setPayments(payment);
        status.setTotalInterestPaid(currentPayment * interestToPayEveryYaz());
        status.setTotalCapitalPaid(currentPayment * capitalPayEveryYaz());
        status.updateNextYazPayment(paysEveryYaz);
        currentPayment++;

    }

    public void newInRiskModePayment(int curYaz) { // after checking customer can pay

        int capitalDebt = status.getCapitalDebt() + capitalPayEveryYaz();
        int interestDebt = status.getInterestDebt() + interestToPayEveryYaz();
        Payment payment = new Payment(curYaz, capitalDebt, interestDebt, capitalDebt + interestDebt);

        status.setPayments(payment);
        status.setTotalInterestPaid(interestDebt);
        status.setTotalCapitalPaid(capitalDebt);
        status.updateNextYazPayment(curYaz + paysEveryYaz); //
        currentPayment++;

        setActiveMode(curYaz);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void updateStatus(int curYaz) {
        if (status.getStatus().equals(Status.Statuses.New))  //New--->Pending
            if (lenders.size() != 0) {
                status.setStatus(Status.Statuses.Pending);
            }

        if (status.getStatus().equals(Status.Statuses.Pending)) //Pending--->Active
            if (status.getTotalAmountRaised() == capital) {
                status.setStatus(Status.Statuses.Active);
                status.setActivationYaz(curYaz);
                status.setCapitalToPay(capital);
                status.setInterestToPay(interestCalculation());
                status.setNextYazPayment(curYaz + paysEveryYaz);
            }

    }

    public Status getStatus() {
        return status;
    }

    public void setActiveMode(int curYaz) { // change from pending to active or from in risk to active

        if (status.getStatus() == Status.Statuses.Pending) { //מעבר pending
            status.setStatus(Status.Statuses.Active);//check if this is the syntax
            status.setActivationYaz(curYaz);
            status.setCapitalToPay(capital);
            status.setInterestToPay(interestCalculation());
            status.setNextYazPayment(curYaz + paysEveryYaz);
        } else if (status.getStatus() == Status.Statuses.InRisk) {
            status.setStatus(Status.Statuses.Active);
            status.setCapitalDebt(0);
            status.setInterestDebt(0);
            status.setNumOfLatePayments(0);
        }
    }

    public void setRiskMode() { // change from active mode to in risk mode

        status.setStatus(Status.Statuses.InRisk);
        status.setCapitalDebt(capitalPayEveryYaz());
        status.setInterestDebt(interestToPayEveryYaz());
        status.setNumOfLatePayments(1);
    }

    public void updateRiskMode() { // customer still doesn't have money to pay the loan

        status.updateCapitalDebt(capitalPayEveryYaz());
        status.updateInterestDebt(interestToPayEveryYaz());
        status.updateNumOfLatePayments(1);
        status.updateNextYazPayment(paysEveryYaz);
    }

    public void setFinishedMode(int currYaz) { // to finish mode from active or in risk mode - after paying all loan
        if (status.getTotalCapitalPaid() + status.getTotalInterestPaid() == capital + interestCalculation()) {

            status.setStatus(Status.Statuses.Finished);
            status.setFinishedYaz(currYaz);
            status.setNextYazPayment(0); // doesn't need to pay anymore
            status.setCapitalDebt(0);
            status.setInterestDebt(0);
            status.setNumOfLatePayments(0);
        }
    }// anyone needs to know that loan is finished?

    /**
     * @Override public String toString() {
     * return "Loan{" +
     * "id='" + id + '\'' +
     * ", category = '" + category + '\'' +
     * ", capital = " + capital +
     * ", pays every yaz =" + paysEveryYaz +
     * ", interest per payment = " + interestPerPayment +
     * ", Final loan amount = " + (capital + interestCalculation()) +
     * ", status=" + status.getStatus() + ", " + infoAboutStatus(status) +
     * '}';
     * }
     **/


    public void payLoan(int currYaz, Map<String, Customer> customers) {
        int debt;

        if (status.getStatus() == Status.Statuses.Active) {
            // new debt
            debt = capitalPayEveryYaz() + interestToPayEveryYaz();
            if (customers.get(owner.getName()).getBalance() >= debt) {
                owner.transaction(currYaz, debt, '-');
                customers.get(owner.getName()).transaction(currYaz, debt, '-');

                newActiveModePayment(currYaz);
                payBackToLenders(currYaz, debt);

                setFinishedMode(currYaz);
            } else
                setRiskMode();
        } else if (status.getStatus() == Status.Statuses.InRisk) {
            // old debt + new payment
            debt = capitalPayEveryYaz() + interestToPayEveryYaz() + status.getCapitalDebt() + status.getInterestDebt();
            if (customers.get(owner.getName()).getBalance() >= debt) {
                owner.transaction(currYaz, debt, '-');
                customers.get(owner.getName()).transaction(currYaz, debt, '-');
                newInRiskModePayment(currYaz);
                payBackToLenders(currYaz, debt);

                setFinishedMode(currYaz);
            } else
                updateRiskMode();
        }
    }

    private void payBackToLenders(int curYaz, int debt) {
        double amount;

        for (Customer customer : lenders.keySet()) {
            Investment investment = lenders.get(customer);
            amount = debt * 1.0 / (investment.getPercent() / 100.0);
            customer.transaction(curYaz, amount, '+');
        }

    }

    public void putLoanInRiskMode() {

        if (status.getStatus() == Status.Statuses.Active)
            setRiskMode();
        else if (status.getStatus() == Status.Statuses.InRisk)
            updateRiskMode();

    }

    public void closeLoan(int currYaz, Map<String, Customer> customers) {
        int debt = capital + interestCalculation() - (status.getTotalCapitalPaid() + status.getTotalInterestPaid());
        customers.get(owner.getName()).transaction(currYaz, debt, '-');
        owner.transaction(currYaz, debt, '-');
        Payment payment = new Payment(currYaz, capitalPayEveryYaz(), interestToPayEveryYaz(), debt);
        status.setPayments(payment);
        status.setTotalInterestPaid(interestCalculation() - status.getTotalInterestPaid());
        status.setTotalCapitalPaid(capital - status.getTotalCapitalPaid());
        payBackToLenders(currYaz, debt);
        setFinishedMode(currYaz);

    }

}


