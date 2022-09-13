package servlets;

import com.google.gson.Gson;
import data.CategoriesAndFileStatus;
import data.PrintLoans;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.engine.options.Engine;
import util.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import static constant.Constants.*;
import static constant.Constants.MODE;

@WebServlet(name = "=GetCategoriesAndFileStatusServlet", urlPatterns = {"/getCategoriesAndFileStatusServlet"})
public class GetCategoriesAndFileStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        Engine engine = ServletUtils.getEngineManager(getServletContext());
        Gson gson = new Gson();

        synchronized (getServletContext()) {
            Set<String> categories = engine.getCategories();
            boolean isFileOpen = engine.isFileOpen();
            CategoriesAndFileStatus res = new CategoriesAndFileStatus(categories, isFileOpen);
            String json1 = gson.toJson(res, CategoriesAndFileStatus.class);
            // String json1 = gson.toJson(loans,new TypeToken<HashMap<PrintCustomer,
            //                    PrintInvestment>>(){}.getType());
            //System.out.println(json1);
            try (PrintWriter out = response.getWriter()) {
                out.println(json1);
                out.flush();
                //todo 14/7
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }
}
