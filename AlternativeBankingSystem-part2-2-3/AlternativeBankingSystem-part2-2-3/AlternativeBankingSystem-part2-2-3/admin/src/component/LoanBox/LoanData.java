package component.LoanBox;


import data.PrintLoan;
public interface LoanData {
    void initLoanData(LoanData loan);
    void initLoanData(PrintLoan loan);

    String getId();
    void setId(String id);
    String getStatus();
    void setStatus(String status);
    String getOwner();
    void setOwner(String owner);

    String getCategory();
    void setCategory(String category);

    int getCapital();
    void setCapital(int capital);

    int getTotalYaz();
    void setTotalYaz(int totalYaz);


    int getInterest();
    void setInterest(int interest);
    LoanData getLoanData();
    PrintLoan getLoan();


}
