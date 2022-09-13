package servlets;

import com.google.gson.Gson;
import data.PrintCustomer;
import data.PrintCustomers;
import data.PrintLoans;
import data.CustomerScreenData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.engine.exception.*;
import my.engine.options.Engine;
import util.ServletUtils;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static constant.Constants.*;

@WebServlet(name="=LoadXmlServlet", urlPatterns = {"/loadXmlServlet"})
public class LoadXmlServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        Engine engine = ServletUtils.getEngineManager(getServletContext());
        String usernameFromParameter = request.getParameter(USER_NAME);
        String pathXmlFromParameter = request.getParameter(PATH_XML);

        if(usernameFromParameter==null||pathXmlFromParameter==null)
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        List<String> message=new ArrayList<>();
         message.add("false");
        synchronized (getServletContext()) {
             message.clear();
             message = tryToLoadXml(pathXmlFromParameter, engine, usernameFromParameter);
         }
       if(message.get(0).equals("false")){
           response.setStatus(HttpServletResponse.SC_CONFLICT);
           response.getOutputStream().print(message.get(1));
        }
        else {
            Gson gson = new Gson();
            PrintLoans loans = engine.getAllCustomerLoans(usernameFromParameter);
            PrintCustomer customer = engine.getCustomer(usernameFromParameter);
            CustomerScreenData res = new CustomerScreenData(loans,engine.getCategories(),customer,true);
            String json = gson.toJson(res);
            try (PrintWriter out = response.getWriter()) {
                out.println(json);
                out.flush();//    response.getOutputStream().print(message.get(1));
                //todo 14/7
                response.setStatus(HttpServletResponse.SC_OK);

            }
        }

    }

    private List<String> tryToLoadXml(String input, Engine engine, String userName) {
        List<String> res=new ArrayList<>();
        try {
            synchronized (getServletContext()) {
                engine.loadXml(input, userName);

                engine.setFileOpen(true);
            }
            res.add("true");
            res.add("The file was successfully open");
        } catch (CustomerException | FileNotFoundException | NumberFormatException | LoanIdException | PaymentRateException | JAXBException | OwnerException | CategoryException | CloneNotSupportedException e) {
            res.add("false");
            res.add(e.getMessage());
        }
        return res;
    }

}
