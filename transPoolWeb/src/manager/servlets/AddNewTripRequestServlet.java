package manager.servlets;


import manager.constans.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddNewTripRequestServlet", urlPatterns = {"/pages/mapDetails/AddNewTripRequestServlet"})
public class AddNewTripRequestServlet extends HttpServlet {
    private final String MAP_DETAILS_URL = "../mapDetails/mapDetails.html";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userRequestName = request.getParameter(Constants.USER_REQUEST_NAME);
        String userRequestSourceStation = request.getParameter(Constants.USER_REQUEST_SOURCE_STATION);
        String userRequestDestinationStation = request.getParameter(Constants.USER_REQUEST_DESTINATION_STATION);
        String userRequestPPK = request.getParameter(Constants.USER_REQUEST_PPK);
        String userRequestDepartureOrArrival = request.getParameter(Constants.USER_REQUEST_DEPARTURE_OR_ARRIVAL);




        //String jsonAddNewTripRequestResponse = new Gson().toJson(mapsTableElementDetailsListDto);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //response.getWriter().write(json);
        response.sendRedirect(MAP_DETAILS_URL);

    }

}
