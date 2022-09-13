package my.engine.exception;

public class InputException extends RuntimeException {
    public enum Type {Range, IncorrectInput, WrongPath}

    private final String RANGE_EXCEPTION_MESSAGE = "Error: Your input is not in the range number, please try again. ";
    private final String INCORRECT_INPUT_EXCEPTION_MESSAGE = "Error: Your input is incorrect, %s ";
    private final String WORNG_PATH_EXCEPTION_MESSAGE = "Error: The path you submit is incorrect, try again." + "P.S your ending needs to be an XML or xml. %s";

    public Type type;
    public String massage;

    public InputException(Type type, String massage) {
        this.type = type;
        this.massage = massage;
    }

    @Override
    public String getMessage() {

        if (type.equals("Range"))
            return RANGE_EXCEPTION_MESSAGE;
        if (type.equals("IncorrectInput"))
            return String.format(INCORRECT_INPUT_EXCEPTION_MESSAGE, massage);
        if (type.equals("WrongPath"))
            return String.format(WORNG_PATH_EXCEPTION_MESSAGE, massage);

        return super.getMessage();
    }
}
