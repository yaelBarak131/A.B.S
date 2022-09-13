package servlets;

import data.AddLoanData;
import data.LoansOnSell;
import data.SellingLoanData;
import data.SellingLoansData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.engine.options.Engine;
import util.ServletUtils;

import java.io.IOException;
import java.util.List;

import static constant.Constants.CUSTOMER;
import static constant.Constants.USER_NAME;
import static servlets.InlayServlet.GSON_INSTANCE;

@WebServlet(name = "=AddLoanToSellServlet", urlPatterns = {"/addLoanToSellServlet"})
public class AddLoanToSellServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        Engine engine = ServletUtils.getEngineManager(getServletContext());
        StringBuilder sb = new StringBuilder();
        String userName = request.getHeader(USER_NAME);

        String s;

        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        if (sb == null || userName == null) {
            // stands for conflict in server state
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            LoansOnSell data = GSON_INSTANCE.fromJson(sb.toString(), LoansOnSell.class);


            synchronized (getServletContext()) {
                engine.updateLoanToSell(data, userName);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getOutputStream().print("Loans move to sell");

            }
        }
    }
}
