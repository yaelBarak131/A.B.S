package component.SellingLoanBox;

public interface SellingData {
    String getLoanId();
    void setLoanId(String loanId);

    double getSellingPrice();
    void setSellingPrice(double price);

    void initSellingData(String loanId, double sellingPrice);

}
