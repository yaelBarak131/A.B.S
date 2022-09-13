package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.engine.options.Engine;
import util.ServletUtils;
import util.SessionUtils;

import java.io.IOException;

import static constant.Constants.*;

@WebServlet(name="=ChargeOrWithdrawServlet", urlPatterns = {"/chargeOrWithdrawServlet"})
public class ChargeOrWithdrawServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        Engine engine = ServletUtils.getEngineManager(getServletContext());


        String customerNameParameter = request.getParameter(CUSTOMER);
        String actionParameter = request.getParameter(CHARGE_OR_WITHDRAW);
        String amountParameter = request.getParameter(AMOUNT);

        if (customerNameParameter == null || customerNameParameter.isEmpty()) {
            //no username in session and no username in parameter - not standard situation. it's a conflict

            // stands for conflict in server state
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            //normalize the username value
            customerNameParameter = customerNameParameter.trim();

            synchronized (getServletContext()) {
                if (actionParameter.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                } else {
                    amountParameter = amountParameter.trim();
                    if (actionParameter.equals(CHARGE)) {

                        double balance = engine.addMoneyToCustomer(customerNameParameter, Integer.parseInt(amountParameter));
                        response.getOutputStream().print(Double.toString(balance));
                        response.setStatus(HttpServletResponse.SC_OK);
                    } else if (actionParameter.equals(WITHDRAW)) {

                        double balance = engine.withdrawMoneyFromAccount(customerNameParameter, Integer.parseInt(amountParameter));
                        response.getOutputStream().print(Double.toString(balance));
                        response.setStatus(HttpServletResponse.SC_OK);
                    }
                }
            }
        }
    }

}


