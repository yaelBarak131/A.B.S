package my.engine.exception;

public class LoanIdException extends RuntimeException {
    private final String EXCEPTION_MESSAGE = "Error: there is a loan with the same id in the system, the id name: %s";


    private String name;

    public LoanIdException(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return String.format(EXCEPTION_MESSAGE, name);
    }
}