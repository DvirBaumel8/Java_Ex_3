package manager.servlets;

import com.google.gson.Gson;
import manager.constans.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddNewTripSuggestServlet", urlPatterns = {"/pages/mapDetails/AddNewTripSuggestServlet"})
public class AddNewTripSuggestServlet extends HttpServlet {
    private final String MAP_DETAILS_URL = "../mapDetails/mapDetails.html";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userSuggestName = request.getParameter(Constants.USER_SUGGEST_NAME);
        String userSuggestRoute = request.getParameter(Constants.USER_SUGGEST_ROUTE);
        String userSuggestDepartureTime = request.getParameter(Constants.USER_SUGGEST_DEPARTURE_TIME);
        String userSuggestPPK = request.getParameter(Constants.USER_SUGGEST_PPK);
        String userSuggestPassengerCapacity = request.getParameter(Constants.USER_SUGGEST_PASSENGER_CAPACITY);




        //String jsonAddNewTripSuggestResponse = new Gson().toJson(mapsTableElementDetailsListDto);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //response.getWriter().write(json);
        response.sendRedirect(MAP_DETAILS_URL);
        //MapPageRepresentation mapRep = engine.getMapDetailsByMapName(mapName);
    }
}
