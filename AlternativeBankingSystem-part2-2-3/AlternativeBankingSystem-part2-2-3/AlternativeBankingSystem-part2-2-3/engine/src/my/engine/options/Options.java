package my.engine.options;
import my.engine.exception.CategoryException;
import my.engine.exception.CustomerException;
import my.engine.exception.OwnerException;
import my.engine.exception.PaymentRateException;

public interface Options {
   void run() throws CategoryException, PaymentRateException, CustomerException, OwnerException;
}
