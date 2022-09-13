package data;


import java.util.*;
import java.util.stream.Collectors;

public class PrintStatus {

    public enum Statuses {New, Active, Pending, InRisk, Finished}

    private Set<PrintCustomer> lendersNames;
    private List<PrintInvestment> lendersInvests;
    private Statuses status;//סטטוס ההלוואה
    private int activationYaz;
    private int finishedYaz;
    private int nextYazPayment;
    private int totalCapitalPaid = 0;
    private int totalInterestPaid = 0;
    private int capitalToPay;
    private int interestToPay;
    private List<PrintPayment> payments = new ArrayList<>();
    //private int debt = 0;
    private int capitalDebt = 0;
    private int interestDebt = 0;
    private int numOfLatePayments;
    private int totalAmountRaised = 0;

    public PrintStatus(Set<PrintCustomer>lendersNames,List<PrintInvestment> lendersInvests, Statuses status, int activationYaz, int finishedYaz, int nextYazPayment, int totalCapitalPaid, int totalInterestPaid, int capitalToPay, int interestToPay, List<PrintPayment> payments, int capitalDebt, int interestDebt, int numOfLatePayments, int totalAmountRaised) {
        this.lendersNames = lendersNames;
        this.lendersInvests= lendersInvests;
        this.status = status;
        this.activationYaz = activationYaz;
        this.finishedYaz = finishedYaz;
        this.nextYazPayment = nextYazPayment;
        this.totalCapitalPaid = totalCapitalPaid;
        this.totalInterestPaid = totalInterestPaid;
        this.capitalToPay = capitalToPay;
        this.interestToPay = interestToPay;
        this.payments = payments;
        this.capitalDebt = capitalDebt;
        this.interestDebt = interestDebt;
        this.numOfLatePayments = numOfLatePayments;
        this.totalAmountRaised = totalAmountRaised;
    }



    public Statuses getStatus() {
        return status;
    }

    public int getActivationYaz() {
        return activationYaz;
    }

    public int getFinishedYaz() {
        return finishedYaz;
    }

    public int getNextYazPayment() {
        return nextYazPayment;
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

    public List<PrintPayment> getPayments() {
        return payments;
    }

    public int getCapitalDebt() {
        return capitalDebt;
    }

    public int getInterestDebt() {
        return interestDebt;
    }

    public int getNumOfLatePayments() {
        return numOfLatePayments;
    }

    public int getTotalAmountRaised() {
        return totalAmountRaised;
    }

    public List<String> getLendersName(){
        List<String> res=new ArrayList<>();

        for(PrintCustomer coustomer:lendersNames){
            res.add(coustomer.getName());
        }

        return res;
    }
    public List<String> getLendersInvest() {
        List<String> res=new ArrayList<>();

        for(PrintInvestment investment:lendersInvests){
            res.add(Double.toString(investment.getPercent()));
        }

        return res;
    }

    @Override
    public String toString() {
        return "PrintStatus{" +
                "lenders name=" + lendersNames +
                "lenders invests" + lendersInvests +
                ", status=" + status +
                ", activationYaz=" + activationYaz +
                ", finishedYaz=" + finishedYaz +
                ", nextYazPayment=" + nextYazPayment +
                ", totalCapitalPaid=" + totalCapitalPaid +
                ", totalInterestPaid=" + totalInterestPaid +
                ", capitalToPay=" + capitalToPay +
                ", interestToPay=" + interestToPay +
                ", payments=" + payments +
                ", capitalDebt=" + capitalDebt +
                ", interestDebt=" + interestDebt +
                ", numOfLatePayments=" + numOfLatePayments +
                ", totalAmountRaised=" + totalAmountRaised +
                '}';
    }
}
