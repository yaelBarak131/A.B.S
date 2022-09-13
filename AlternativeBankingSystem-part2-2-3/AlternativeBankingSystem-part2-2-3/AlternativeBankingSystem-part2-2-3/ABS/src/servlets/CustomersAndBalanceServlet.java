package servlets;

import com.google.gson.Gson;
import data.PrintCustomerAndBalance;
import data.PrintTransactions;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.engine.options.Engine;
import util.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;

import static constant.Constants.*;

@WebServlet(name="=CustomerAndBalanceServlet", urlPatterns = {"/customerAndBalanceServlet"})
public class CustomersAndBalanceServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Engine engine = ServletUtils.getEngineManager(getServletContext());
        String modeFromParameter = request.getParameter(MODE);
        String currYazFromParameter = request.getParameter(CURR_YAZ);
//todo 14/7
        if(modeFromParameter==null||currYazFromParameter==null)
            response.setStatus(HttpServletResponse.SC_CONFLICT);

        try (PrintWriter out = response.getWriter()) {
            PrintCustomerAndBalance customers = null;
            Gson gson = new Gson();
            if(modeFromParameter.equals(OFF))
                    customers  = engine.getAllCustomersAndBalance();
             else if(modeFromParameter.equals(ON)){
                 customers = engine.getCustomerInRewindMode(Integer.parseInt(currYazFromParameter));
            }
             //todo 14/7
            response.setStatus(HttpServletResponse.SC_OK);
            String json = gson.toJson(customers);
            out.println(json);
            out.flush();
        }
    }


}
