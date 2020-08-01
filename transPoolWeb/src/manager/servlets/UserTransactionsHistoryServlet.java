package manager.servlets;

import engine.dto.UserTransactionsHistoryDto;
import manager.constans.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserTransactionsHistoryServlet", urlPatterns = {"/pages/userDetails/UserTransactionsHistoryServlet"})
public class UserTransactionsHistoryServlet extends HttpServlet {

    private final String MAP_DETAILS_URL = "../userDetails/userDetails.html";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter(Constants.USER_NAME);

        List<UserTransactionsHistoryDto> userTransactionsHistoryDtoList = null;


        //String jsonNewBalanceResponse = new Gson().toJson(userTransactionsHistoryDtoList);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //response.getWriter().write(json);
        response.sendRedirect(MAP_DETAILS_URL);
    }
}
