package data;

public class SellingLoanData{

    private String id;
    private double price;
    private String sellingCustomer;

    public void setSellingCustomer(String sellingCustomer) {
        this.sellingCustomer = sellingCustomer;
    }

    public String getSellingCustomer() {
        return sellingCustomer;
    }

    public SellingLoanData(String id, double price, String sellingCustomer) {
        this.id = id;
        this.price = price;
        this.sellingCustomer = sellingCustomer;
    }


    public String getLoanId() {
        return id ;
    }


    public void setLoanId(String loanId) {
         this.id=loanId ;
    }


    public double getSellingPrice() {
        return price;
    }


    public void setSellingPrice(double price) {
          this.price=price;
    }



}
