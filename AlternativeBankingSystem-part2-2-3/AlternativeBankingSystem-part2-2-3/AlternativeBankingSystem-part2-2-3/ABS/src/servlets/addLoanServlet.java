package servlets;

import com.google.gson.Gson;
import data.AddLoanData;
import data.InlayData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.engine.exception.LoanIdException;
import my.engine.exception.PaymentRateException;
import my.engine.options.Engine;
import util.ServletUtils;

import java.io.IOException;

import static constant.Constants.*;
import static servlets.InlayServlet.GSON_INSTANCE;

@WebServlet(name = "=addLoanServlet", urlPatterns = {"/addLoanServlet"})
public class addLoanServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        Engine engine = ServletUtils.getEngineManager(getServletContext());

        StringBuilder sb = new StringBuilder();
        String s;

        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        if (sb == null) {
            // stands for conflict in server state
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            AddLoanData data = GSON_INSTANCE.fromJson(sb.toString(), AddLoanData.class);

            String customerName = data.getCustomerName();
            String loanId = data.getLoanId();
            String category = data.getCategory();
            String capital = data.getCapital();
            String totalYaz = data.getTotalYaz();
            String payEveryYaz = data.getPayEveryYaz();
            String interestPerPayment = data.getInterestPerPayment();

            synchronized (getServletContext()) {
                try {
                    engine.addNewLoan(customerName, loanId, category, capital, totalYaz, payEveryYaz, interestPerPayment);
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getOutputStream().print("Loan successfully add");
                } catch (PaymentRateException e) {
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    response.getOutputStream().print(String.valueOf(e));
                    response.addHeader(EXCEPTION_TYPE, "Payment");
                } catch (LoanIdException e) {
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    response.getOutputStream().print(String.valueOf(e));
                    response.addHeader(EXCEPTION_TYPE, "Loan");
                }
            }


        }
    }
}
