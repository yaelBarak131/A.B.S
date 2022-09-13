package my.engine.MyClasses;

import java.util.HashSet;
import java.util.Set;

import my.engine.exception.CategoryException;
import my.engine.jaxb.generatedEX3.*;

public class Categories implements CheckAndCopy {//stay as List or something  else(map) need to efficient in search

    protected Set<String> categories=new HashSet<String>();

    public Set<String> getCategories() {
        if (categories == null) {
            categories = new HashSet<>();
        }
        return this.categories;
    }

    public Object copyJaxbClass(AbsDescriptor abs,String name) throws CategoryException {

        //categories.clear();

        this.categories.addAll(abs.getAbsCategories().getAbsCategory());

        return this;
    }

    public void checkJaxbClass(AbsDescriptor abs) throws CategoryException {
        if (abs.getAbsCategories() == null)
            throw new CategoryException("");

    }
}



