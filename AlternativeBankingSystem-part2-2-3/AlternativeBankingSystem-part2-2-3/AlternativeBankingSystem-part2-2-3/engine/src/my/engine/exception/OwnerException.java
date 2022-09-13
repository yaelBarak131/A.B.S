package my.engine.exception;

public class OwnerException extends RuntimeException{
    private final String EXCEPTION_MESSAGE = "Error: owner: %s is not one of your customers.";

    private String name;

    public OwnerException(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return String.format(EXCEPTION_MESSAGE, name) ;
    }

}
