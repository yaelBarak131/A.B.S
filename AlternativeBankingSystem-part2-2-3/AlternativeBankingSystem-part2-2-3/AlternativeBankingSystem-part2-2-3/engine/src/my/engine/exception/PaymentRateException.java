package my.engine.exception;

public class PaymentRateException extends RuntimeException {

    private final String EXCEPTION_MESSAGE = "Error: The Yaz per payment doesn't divide without residue in the loan %s";

    private String loanName;

    public PaymentRateException(String name) {
        this.loanName = name;
    }

    @Override
    public String getMessage() {
        return String.format(EXCEPTION_MESSAGE, loanName);
    }
}
