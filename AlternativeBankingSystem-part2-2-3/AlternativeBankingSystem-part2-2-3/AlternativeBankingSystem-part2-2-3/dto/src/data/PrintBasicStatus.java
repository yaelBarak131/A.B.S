package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrintBasicStatus {
    public enum Statuses {New, Active, Pending, InRisk, Finished}

    private List<PrintCustomer> lendersName;
    private List<PrintInvestment> lendersInvest;
    private PrintStatus.Statuses status;//סטטוס ההלוואה
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


    public PrintBasicStatus(List<PrintCustomer> lendersName, List<PrintInvestment> lendersInvest, PrintStatus.Statuses status, int activationYaz, int finishedYaz, int nextYazPayment, int totalCapitalPaid, int totalInterestPaid, int capitalToPay, int interestToPay, List<PrintPayment> payments, int capitalDebt, int interestDebt, int numOfLatePayments, int totalAmountRaised) {
        this.lendersName = lendersName;
        this.lendersInvest = lendersInvest;
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

    public List<PrintCustomer> getLendersName() {
        return lendersName;
    }

    public List<PrintInvestment> getLendersInvest() {
        return lendersInvest;
    }

    public PrintStatus.Statuses getStatus() {
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

    @Override
    public String toString() {
        return "PrintBasicStatus{" +
                "lendersName=" + lendersName +
                ", lendersInvest=" + lendersInvest +
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
