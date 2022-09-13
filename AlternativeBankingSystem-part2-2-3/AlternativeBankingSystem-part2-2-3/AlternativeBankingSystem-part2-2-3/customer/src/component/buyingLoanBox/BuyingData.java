package component.buyingLoanBox;

public interface BuyingData {
    String getLoanId();
    void setLoanId(String loanId);

    double getBuyingPrice();
    void setBuyingPrice(double price);

    void initBuyingData(String loanId, double sellingPrice);

}
