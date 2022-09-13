package my.engine.options;

import my.engine.MyClasses.Loan;

import java.util.Comparator;

public class SortComparator implements Comparator<Loan> {

    @Override
    public int compare(Loan o1, Loan o2) {

        int yaz1 = o1.getStatus().getActivationYaz();
        int yaz2 = o2.getStatus().getActivationYaz();
        int toPay1 = o1.interestToPayEveryYaz() + o1.capitalPayEveryYaz();
        int toPay2 = o2.interestToPayEveryYaz() + o2.capitalPayEveryYaz();


        if( yaz1 < yaz2 ) // compares them by activation yaz
            return -1;
        else if(yaz1 > yaz2) // same as above
            return 1;
        else // if they started at the same yax compare them by lower money to pay
            return Integer.compare(toPay1, toPay2);



    }
}
