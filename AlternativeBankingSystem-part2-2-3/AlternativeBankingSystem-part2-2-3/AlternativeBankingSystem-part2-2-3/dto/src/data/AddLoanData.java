package data;

public class AddLoanData {
    String customerName;
    String loanId;
    String category;
    String capital;
    String totalYaz;
    String payEveryYaz;
    String interestPerPayment;

    public AddLoanData(String customerName, String loanId, String category, String capital, String totalYaz, String payEveryYaz, String interestPerPayment) {
        this.customerName = customerName;
        this.loanId = loanId;
        this.category = category;
        this.capital = capital;
        this.totalYaz = totalYaz;
        this.payEveryYaz = payEveryYaz;
        this.interestPerPayment = interestPerPayment;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getLoanId() {
        return loanId;
    }

    public String getCategory() {
        return category;
    }

    public String getCapital() {
        return capital;
    }

    public String getTotalYaz() {
        return totalYaz;
    }

    public String getPayEveryYaz() {
        return payEveryYaz;
    }

    public String getInterestPerPayment() {
        return interestPerPayment;
    }
}
