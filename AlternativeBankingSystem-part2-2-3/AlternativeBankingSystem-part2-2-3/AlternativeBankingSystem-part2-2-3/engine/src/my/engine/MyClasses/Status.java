package my.engine.MyClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Status {
    public enum Statuses {New, Active, Pending, InRisk, Finished}

    private Map<Customer, Investment> lenders = new HashMap<>();
    private Statuses status;//סטטוס ההלוואה
    private int activationYaz;
    private int finishedYaz;
    private int nextYazPayment;
    private int totalCapitalPaid = 0;
    private int totalInterestPaid = 0;
    private int capitalToPay;
    private int interestToPay;
    private List<Payment> payments = new ArrayList<>();
    //private int debt = 0;
    private int capitalDebt = 0;
    private int interestDebt = 0;
    private int numOfLatePayments;
    private int totalAmountRaised = 0;

    public Status() {
    }

    public Status(Statuses status, int capital, int interest) {
        this.status = status;
        this.capitalToPay = capital;
        this.interestToPay = interest;
    }

    public int getFinishedYaz() {
        return finishedYaz;
    }

    public void setFinishedYaz(int finishedYaz) {
        this.finishedYaz = finishedYaz;
    }

   /* public int getDebt() {
        return debt;
    }

    public void setDebt(int debt) {
        this.debt = debt;
    }*/

    public void setCapitalDebt(int cDebt) {
        this.capitalDebt = cDebt;
    }

    public int getCapitalDebt() {
        return capitalDebt;
    }

    public void updateCapitalDebt(int capitalToAdd) {
        this.capitalDebt += capitalToAdd;
    }

    public void setInterestDebt(int iDebt) {
        this.interestDebt = iDebt;
    }

    public int getInterestDebt() {
        return interestDebt;
    }

    public void updateInterestDebt(int interestToAdd) {
        this.interestDebt += interestToAdd;
    }


    /*public void updateDebt(int debtToAdd) {
        this.debt += debtToAdd;
    }
*/
    public Statuses getStatus() {
        return status;
    }

    public void setStatus(Statuses status) {
        this.status = status;
    }

    public int getActivationYaz() {
        return activationYaz;
    }

    public int getNextYazPayment() {
        return nextYazPayment;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setActivationYaz(int activationYaz) {
        this.activationYaz = activationYaz;
    }

    public void setNextYazPayment(int nextYazPayment) {
        this.nextYazPayment = nextYazPayment;
    }

    public void updateNextYazPayment(int nextYazPayment) {
        this.nextYazPayment = nextYazPayment;
    }


    public void setPayments(Payment payment) {
        if (payments == null)
            payments = new ArrayList<>();

        this.payments.add(payment);
    }


    public void setTotalCapitalPaid(int totalCapitalPaid) {
        this.totalCapitalPaid = totalCapitalPaid;
    }

    public void setTotalInterestPaid(int totalInterestPaid) {
        this.totalInterestPaid = totalInterestPaid;
    }

    public void setCapitalToPay(int capitalToPay) {
        this.capitalToPay = capitalToPay;
    }

    public void setInterestToPay(int interestToPay) {
        this.interestToPay = interestToPay;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public int getTotalCapitalPaid() {
        return totalCapitalPaid;
    }

    public int getTotalInterestPaid() {
        return totalInterestPaid;
    }

    public int getCapitalToPay() {
        return capitalToPay;
    }

    public int getInterestToPay() {
        return interestToPay;
    }

    public Map<Customer, Investment> getLenders() {
        return lenders;
    }

    public List<String> getLendersName(){
        List<String> res=new ArrayList<>();

        for(Customer coustomer:lenders.keySet()){
            res.add(coustomer.getName());
        }

       return res;
    }
    public List<String> getLendersInvest() {
        List<String> res=new ArrayList<>();

        for(Investment investment:lenders.values()){
            res.add(Double.toString(investment.getPercent()));
        }

        return res;
    }
    public void setLenders(Map<Customer, Investment> lenders) {
        this.lenders = lenders;
    }

    public int getNumOfLatePayments() {
        return numOfLatePayments;
    }

    public void setNumOfLatePayments(int numOfLatePayments) {
        this.numOfLatePayments = numOfLatePayments;
    }

    public void updateNumOfLatePayments(int addLatePayments) {
        this.numOfLatePayments += addLatePayments;
    }


    @Override
    public String toString() {
        String res = new String();

        res += status;

        switch (status) {
            case Pending: {
                res += pendingInfo();
                break;
            }
            case Active: {
                res += pendingInfo() + "\n" + activeInfo();
                break;
            }
            case InRisk: {
                res += activeInfo() + InRiskInfo();
                break;
            }
            case Finished: {
                res += finishedInfo();
                break;
            }
        }

        return res;
    }

    private String pendingInfo() {
        String res = new String();
        if (lenders.size() != 0) {
            res += "\nLenders:";
            for (Map.Entry<Customer, Investment> entry : lenders.entrySet()) {
                res += "\n{ name = " + entry.getKey().getName() +
                        ",investment = " + entry.getValue().getInvestment() + " }";
            }
        } else
            res += "\nNo lenders yet ";
        res += "\nThe Total amount already raised = " + totalAmountRaised;
        res += "\nThe total amount still to be raised = " + (capitalToPay - totalAmountRaised);

        return res;
    }

    public void setTotalAmountRaised(int totalAmountRaised) {
        this.totalAmountRaised = totalAmountRaised;
    }

    public int getTotalAmountRaised() {
        calculateTotalAmountRaised();
        return totalAmountRaised;
    }

    private void calculateTotalAmountRaised() {
        int sum = 0;
        for (Investment entry : lenders.values()) {
            sum += entry.getInvestment();
        }
        totalAmountRaised = sum;
    }

    private String activeInfo() {
        String res = new String();

        res += "\nactivetion yaz =" + activationYaz +
                "\nnext payment yaz = " + nextYazPayment +
                "\nhistory of payments = " + printPayments() +
                "\ntotal capital paid so far = " + totalCapitalPaid +
                "\ntotal interest paid so far = " + totalInterestPaid +
                "\ntotal capital that left to paid = " + capitalToPay +
                "\ntotal interest that left to paid = " + interestToPay;


        return res;
    }

    private String printPayments() {
        String res = new String();

        for (Payment payment : payments) {
            res += payment;
        }

        return res;
    }

    public String InRiskInfo() {
        String res = new String();

        res += "\nThe num of the late payment = " + numOfLatePayments +
                "\nThe sum of the debt = " + interestDebt + capitalDebt;

        return res;
    }

    public void updateTotalAmountRaised(int investment) {
        this.totalAmountRaised = this.totalAmountRaised + investment;
    }

    private String finishedInfo() {
        String res = new String();

        res += "\nLenders:";
        for (Map.Entry<Customer, Investment> entry : lenders.entrySet()) {
            res += "\n{ name = " + entry.getKey().getName() +
                    ",investment = " + entry.getValue().getInvestment() + " }";
        }
        res += "active yaz = " + activationYaz +
                ",finished yaz = " + finishedYaz + printPayments();

        return res;
    }
}
