package my.engine.MyClasses;

import my.engine.exception.CategoryException;
import my.engine.exception.CustomerException;
import my.engine.exception.OwnerException;
import my.engine.exception.PaymentRateException;
import my.engine.jaxb.generatedEX3.AbsDescriptor;

public interface CheckAndCopy {
    public Object copyJaxbClass(AbsDescriptor abs, String name) throws CategoryException, PaymentRateException, CustomerException, OwnerException, CloneNotSupportedException;
    public void checkJaxbClass(AbsDescriptor abs) throws CategoryException, PaymentRateException, CustomerException, OwnerException; //change to every methode to throw exception.

}
