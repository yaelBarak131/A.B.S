package my.engine.MyClasses;

public class Investment {

    private int investment;
    private double percent;

    public Investment(int investment, double percent) {
        this.investment = investment;
        this.percent = percent;
    }

    public void updateInvestment(Investment investment) {
        this.investment = investment.investment;
        this.percent = investment.percent;
    }

    public int getInvestment() {
        return investment;
    }

    public void setInvestment(int investment) {
        this.investment = investment;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}