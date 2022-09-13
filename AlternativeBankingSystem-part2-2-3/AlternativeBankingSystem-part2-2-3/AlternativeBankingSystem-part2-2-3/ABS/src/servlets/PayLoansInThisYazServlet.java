package servlets;

import com.google.gson.Gson;
import data.InlayData;
import data.PrintLoans;
import data.PrintLoansToPay;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.engine.options.Engine;
import util.ServletUtils;

import java.io.IOException;
import java.util.List;

import static constant.Constants.BALANCE;

@WebServlet(name="=PayLoansInThisYazServlet", urlPatterns = {"/payLoansInThisYazServlet"})
public class PayLoansInThisYazServlet extends HttpServlet {

    public final static Gson GSON_INSTANCE = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        Engine engine = ServletUtils.getEngineManager(getServletContext());
        StringBuilder sb = new StringBuilder();
        String s;
        double balance;

        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
            if (sb ==null) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            } else {

                PrintLoansToPay loans = GSON_INSTANCE.fromJson(sb.toString(), PrintLoansToPay.class);

                synchronized (getServletContext()) {

                    balance = engine.paySelectedLoans(loans, loans.getCustomerName(),loans.getCurrYaz());
                }

                response.setHeader(BALANCE, Double.toString(balance));
                 //todo 14/7
                response.setStatus(HttpServletResponse.SC_OK);

            }
        }
    }
