package component.LoanBox;

import data.PrintLoan;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BasicLoanData implements LoanData{

    protected SimpleStringProperty id;
    protected SimpleStringProperty owner;
    protected SimpleStringProperty category;
    protected SimpleIntegerProperty capital;
    protected SimpleIntegerProperty totalYaz;
    protected SimpleIntegerProperty interest;
    protected SimpleStringProperty status;
    private LoanData loan;
    private PrintLoan printLoan;


    public BasicLoanData(){
        id=new SimpleStringProperty();
        owner=new SimpleStringProperty();
        category=new SimpleStringProperty();
        capital=new SimpleIntegerProperty();
        totalYaz=new SimpleIntegerProperty();
        interest=new SimpleIntegerProperty();
        status=new SimpleStringProperty();
    }
    public void initLoanData(LoanData _loan){
        this.loan = _loan;
        id.set(_loan.getId());
        owner.set(_loan.getOwner());
        category.set(_loan.getCategory());
        capital.set(_loan.getCapital());
        totalYaz.set(_loan.getTotalYaz());
        interest.set(_loan.getInterest());
        status.set(_loan.getStatus());
    }
    public void initLoanData(PrintLoan _loan){
        this.printLoan = _loan;
        id.set(_loan.getId());
        owner.set(_loan.getOwner());
        category.set(_loan.getCategory());
        capital.set(_loan.getCapital());
        totalYaz.set(_loan.getTotalYaz());
        interest.set(_loan.getInterest());
        status.set(printLoan.getStatusName());
    }


    @Override
    public String getId() {
        return this.id.get();
    }

    @Override
    public void setId(String id) {
        this.id.set(id);
    }

    @Override
    public String getStatus() {
        return this.status.get();
    }

    @Override
    public void setStatus(String status) {
        this.status.set(status);
    }

    @Override
    public String getOwner() {
        return this.owner.get();
    }

    @Override
    public void setOwner(String owner) {
       this.owner.set(owner);
    }


    @Override
    public String getCategory() {
        return this.category.get();
    }

    @Override
    public void setCategory(String category) {
       this.category.set(category);
    }


    @Override
    public int getCapital() {
        return this.capital.get();
    }

    @Override
    public void setCapital(int capital) {
        this.capital.set(capital);
    }


    @Override
    public int getTotalYaz() {
        return this.totalYaz.get();
    }

    @Override
    public void setTotalYaz(int totalYaz) {
       this.totalYaz.set(totalYaz);
    }


    @Override
    public int getInterest() {
        return this.interest.get();
    }

    @Override
    public void setInterest(int interest) {
        this.interest.set(interest);
    }
    @Override
    public LoanData getLoanData(){return loan;}
    @Override
    public PrintLoan getLoan(){return printLoan;}

}
