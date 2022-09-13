package servlets;

import data.LoansOnSell;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.engine.options.Engine;
import util.ServletUtils;

import java.io.IOException;

import static constant.Constants.BALANCE;
import static constant.Constants.USER_NAME;
import static servlets.InlayServlet.GSON_INSTANCE;

@WebServlet(name="=BuyLoansServlet", urlPatterns = {"/buyLoansServlet"})
public class BuyLoansServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        Engine engine = ServletUtils.getEngineManager(getServletContext());
        StringBuilder sb = new StringBuilder();
        String s;
        String userName = request.getHeader(USER_NAME);
        double balance;

        if (userName == null)
            response.setStatus(HttpServletResponse.SC_CONFLICT);

        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        if (sb == null) {
            // stands for conflict in server state
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {

            LoansOnSell data = GSON_INSTANCE.fromJson(sb.toString(), LoansOnSell.class);

            synchronized (getServletContext()) {
                balance = engine.buyLoans(userName, data);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getOutputStream().print("Successful buying");
                response.setHeader(BALANCE, Double.toString(balance));

            }
        }
    }


}