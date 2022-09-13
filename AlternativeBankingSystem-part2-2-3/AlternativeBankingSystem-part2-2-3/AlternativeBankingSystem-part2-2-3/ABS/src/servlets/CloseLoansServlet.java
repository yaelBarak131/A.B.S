package servlets;

import com.google.gson.Gson;
import data.PrintLoansToPay;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.engine.options.Engine;
import util.ServletUtils;

import java.io.IOException;

import static constant.Constants.BALANCE;

@WebServlet(name = "=CloseLoansServlet", urlPatterns = {"/closeLoansServlet"})
public class CloseLoansServlet extends HttpServlet {

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
        if (sb == null) {
            //no username in session and no username in parameter - not standard situation. it's a conflict
           // stands for conflict in server state
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {

            PrintLoansToPay loans = GSON_INSTANCE.fromJson(sb.toString(), PrintLoansToPay.class);

            synchronized (getServletContext()) {

                balance = engine.closeSelectedLoans(loans, loans.getCustomerName(), loans.getCurrYaz());
                //todo 14/7
                response.setStatus(HttpServletResponse.SC_OK);
                response.setHeader(BALANCE, Double.toString(balance));
            }


        }
    }
}
