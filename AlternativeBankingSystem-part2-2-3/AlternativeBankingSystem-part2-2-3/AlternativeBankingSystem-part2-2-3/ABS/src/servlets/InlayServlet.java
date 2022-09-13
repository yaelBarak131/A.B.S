package servlets;

import com.google.gson.Gson;
import data.InlayData;
import data.PrintLoans;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.engine.options.Engine;
import util.ServletUtils;

import java.io.IOException;
import java.util.List;

@WebServlet(name="=InlayServlet", urlPatterns = {"/InlayServlet"})
public class InlayServlet  extends HttpServlet {

    public final static Gson GSON_INSTANCE = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        Engine engine = ServletUtils.getEngineManager(getServletContext());
        StringBuilder sb = new StringBuilder();
        String s;

        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
            if (sb ==null) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            } else {
                InlayData data = GSON_INSTANCE.fromJson(sb.toString(), InlayData.class);
                int amount = data.getAmount();
                String customerName = data.getCustomerName();
                List<String> loansId = data.getLoansId();
                int maxPrecent = data.getMaxPrecent();
                PrintLoans relentLoans = data.getRelventLoans();

                List<Integer> res = null;
                synchronized (getServletContext()) {
                    try {
                        res = engine.inlay(loansId, amount, customerName, relentLoans, maxPrecent);

                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }

                Integer left =res.get(0);
                Integer balance=res.get(1);

                response.setHeader("left", String.valueOf(left));
                response.setHeader("balance", String.valueOf(balance));
                //todo 14/7
                response.setStatus(HttpServletResponse.SC_OK);

            }
        }
    }
