package my.engine.exception;

public class CategoryException extends RuntimeException {
    private final String NO_CATEGORY_EXCEPTION_MESSAGE = "Error: No categories in xml file.";
    private final String NO_MATCH_EXCEPTION_MESSAGE = "Error: category - %s doesn't exist.";

    private String category;

    public CategoryException(String category) {
        this.category = category;
    }

    @Override
    public String getMessage() {
        if (category != "")
            return String.format(NO_MATCH_EXCEPTION_MESSAGE, category);
        else
            return NO_CATEGORY_EXCEPTION_MESSAGE;
    }
}
