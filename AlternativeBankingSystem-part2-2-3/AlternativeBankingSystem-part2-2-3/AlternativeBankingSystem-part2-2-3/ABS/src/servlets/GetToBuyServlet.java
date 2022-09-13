package servlets;

import com.google.gson.Gson;
import data.LoansOnSell;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.engine.options.Engine;
import util.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static constant.Constants.*;

@WebServlet(name = "=GetToBuyServlet", urlPatterns = {"/getToBuyServlet"})
public class GetToBuyServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String usernameFromParameter = request.getParameter(USER_NAME);
        String rewindModeFromParameter = request.getParameter(REWIND_MODE);
        String currYazFromParameter = request.getParameter(CURR_YAZ);
        Engine engine = ServletUtils.getEngineManager(getServletContext());

        if (usernameFromParameter == null || rewindModeFromParameter == null || currYazFromParameter == null)
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        else {
            try (PrintWriter out = response.getWriter()) {

                Gson gson = new Gson();
                synchronized (getServletContext()) {
                    LoansOnSell  customers = null;
                    if (rewindModeFromParameter.equals(OFF))
                        customers = engine.gettingLoansCustomerCanBuy(usernameFromParameter);
                    else if (rewindModeFromParameter.equals(ON))
                        customers = engine.getLoanOnSellInRewindMode(usernameFromParameter, Integer.parseInt(currYazFromParameter));


                    response.setStatus(HttpServletResponse.SC_OK);
                    String json = gson.toJson(customers);
                    out.println(json);
                    out.flush();
                }
            }

        }
    }
}
