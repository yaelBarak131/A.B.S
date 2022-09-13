package my.engine.exception;

public class CustomerException extends RuntimeException {
    private final String EXCEPTION_MESSAGE = "Error: You have a different customer with the same name: %s in your XML file.";
    private final String Empty_EXCEPTION_MESSAGE = "Error: No customers in the system";

    private String name;

    public CustomerException(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        if (name == "")
            return Empty_EXCEPTION_MESSAGE;
        else
            return String.format(EXCEPTION_MESSAGE, name);
    }
}
