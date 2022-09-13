package data;

import java.util.Set;

public class CategoriesAndFileStatus {

    Set<String> categories;
    boolean fileInSystem=false;

    public CategoriesAndFileStatus(Set<String> categories, boolean fileInSystem) {
        this.categories = categories;
        this.fileInSystem = fileInSystem;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public boolean isFileInSystem() {
        return fileInSystem;
    }
}
