package servlets;

import com.google.gson.Gson;
import data.CustomerScreenData;
import data.PrintCustomer;
import data.PrintCustomers;
import data.PrintLoans;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.engine.options.Engine;
import util.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static constant.Constants.*;

@WebServlet(name="=GetLoansServlet", urlPatterns = {"/GetLoansServlet"})
public class GetLoansServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        Engine engine = ServletUtils.getEngineManager(getServletContext());
        String usernameFromParameter = request.getParameter(USER_NAME);
        String typeUserFromParameter = request.getParameter(TYPE_USER);
        String statusFromParameter = request.getParameter(STATUS);
        String typeLoanFromParameter = request.getParameter(TYPE_LOAN);
        String modeFromParameter = request.getParameter(MODE);
        String currYazFromParameter = request.getParameter(CURR_YAZ);

        if (usernameFromParameter == null || statusFromParameter == null || typeLoanFromParameter == null || typeUserFromParameter == null) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }

        PrintLoans loans = null;
        Gson gson = new Gson();

        synchronized (getServletContext()) {
            if (modeFromParameter.equals(OFF)) {
                if (typeUserFromParameter.equals(ADMIN)) {
                    loans = engine.getAllLoans().getLoanWithStatus(statusFromParameter);

                } else {
                    if (typeLoanFromParameter.equals(LONER))
                        //need To change
                        loans = engine.getAllCustomerLoanerLoans(usernameFromParameter).getLoanWithStatus(statusFromParameter);

                    else if (typeLoanFromParameter.equals(LENDER))
                        //need To change
                        loans = engine.getAllCustomerLenderLoans(usernameFromParameter).getLoanWithStatus(statusFromParameter);
                    //   System.out.println("lender Loans:" + loans);
                }
            }
        else if(modeFromParameter.equals(ON)){
                if (typeUserFromParameter.equals(ADMIN)) {
                    loans = engine.getLoansInRewindMode(Integer.parseInt(currYazFromParameter)).getLoanWithStatus(statusFromParameter);

                } else {
                    if (typeLoanFromParameter.equals(LONER))
                        loans = engine.getAllCustomerLoanerLoansInRewindMode(usernameFromParameter,Integer.parseInt(currYazFromParameter)).getLoanWithStatus(statusFromParameter);

                    else if (typeLoanFromParameter.equals(LENDER))
                        loans = engine.getAllCustomerLenderLoansInRewindMode(usernameFromParameter,Integer.parseInt(currYazFromParameter)).getLoanWithStatus(statusFromParameter);
                    //   System.out.println("lender Loans:" + loans);
                }
        }

            }
        String json = gson.toJson(loans);
        try (PrintWriter out = response.getWriter()) {
            out.println(json);
            out.flush();
            //todo 14/7
            response.setStatus(HttpServletResponse.SC_OK);

        }
    }

}