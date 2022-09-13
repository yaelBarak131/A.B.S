package servlets;

import com.google.gson.Gson;
import data.PrintTransactions;
import data.PrintTransactionsAndBalance;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.engine.options.Engine;
import util.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import static constant.Constants.*;

@WebServlet(name = "=LastTransactionsServlet", urlPatterns = {"/lastTransactionsServlet"})
public class LastTransactionsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Engine engine = ServletUtils.getEngineManager(getServletContext());
        String customerName = request.getParameter(CUSTOMER);
        String modeFromParameter = request.getParameter(REWIND_MODE);
        String currYazFromParameter = request.getParameter(CURR_YAZ);

        if (customerName == null || modeFromParameter == null || currYazFromParameter == null) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);

        } else {
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                PrintTransactionsAndBalance transactionsAndBalance = null;
                PrintTransactions transactions =null;
                if (modeFromParameter.equals(OFF))
                {
                    transactions = engine.getAllTransactions(customerName);
                    double balance =engine.getBalance(customerName);

                    transactionsAndBalance = new PrintTransactionsAndBalance(transactions,balance);
                }
                else if (modeFromParameter.equals(ON)) {
                     transactions=engine.getTransactionInRewindMode(customerName,Integer.parseInt(currYazFromParameter));
                    double balanceInYaz=engine.getBalanceInRewindMode(customerName,Integer.parseInt(currYazFromParameter));
                    transactionsAndBalance = new PrintTransactionsAndBalance(transactions,balanceInYaz);

                }
                response.setStatus(HttpServletResponse.SC_OK);
                String json = gson.toJson(transactionsAndBalance);
                out.println(json);
                out.flush();
            }
        }
    }
}
