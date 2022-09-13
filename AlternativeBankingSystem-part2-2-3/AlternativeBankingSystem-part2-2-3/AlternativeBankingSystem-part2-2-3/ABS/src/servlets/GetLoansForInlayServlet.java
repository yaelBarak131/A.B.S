package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import data.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.engine.options.Engine;
import util.ServletUtils;

import javax.servlet.ServletException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

@WebServlet(name = "=GetLoansForInlayServlet", urlPatterns = {"/GetLoansForInlayServlet"})
public class GetLoansForInlayServlet extends HttpServlet {
    public final static Gson GSON_INSTANCE = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Engine engine = ServletUtils.getEngineManager(getServletContext());
        Gson gson = new Gson();


        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        //todo 14/7
        if (sb == null)
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        else {
            RelventLoansData data = GSON_INSTANCE.fromJson(sb.toString(), RelventLoansData.class);

            String name = data.getName();
            int amount = data.getAmount();
            List<String> categories = data.getCategories();
            int interest = data.getInterest();
            int maxLoan = data.getMaxLoan();
            int minYaz = data.getMinYaz();
            PrintLoans loans = null;


            loans = engine.getOptionalLoansForInvestment(name, amount, categories, interest, minYaz, maxLoan);


            String json1 = gson.toJson(loans, PrintLoans.class);
            // String json1 = gson.toJson(loans,new TypeToken<HashMap<PrintCustomer,
            //                    PrintInvestment>>(){}.getType());
            // System.out.println(json1);
            try (PrintWriter out = response.getWriter()) {
                out.println(json1);
                out.flush();
                response.setStatus(HttpServletResponse.SC_OK);

            }
        }
    }
}