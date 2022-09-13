package data;

public class PrintInvestment {
    private int investment;
    private double percent;

    public PrintInvestment(int investment, double percent) {
        this.investment = investment;
        this.percent = percent;
    }

    public int getInvestment() {
        return investment;
    }

    public double getPercent() {
        return percent;
    }

    @Override
    public String toString() {
        return "PrintInvestment{" +
                "investment=" + investment +
                ", percent=" + percent +
                '}';
    }
}
