package manager.servlets;


import com.google.gson.Gson;
import engine.dto.mapPage.TripRequestDto;
import engine.dto.mapPage.TripRequestResponseDto;
import engine.manager.EngineManager;
import manager.constans.Constants;
import manager.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String mapName = request.getParameter(Constants.MAP_NAME);
        String userName = request.getParameter(Constants.USER_NAME);

        String[] inputs = new String[6];
        inputs[0] = request.getParameter(Constants.USER_REQUEST_NAME);
        inputs[1] = request.getParameter(Constants.USER_REQUEST_SOURCE_STATION);
        inputs[2] = request.getParameter(Constants.USER_REQUEST_DESTINATION_STATION);
        inputs[3] = request.getParameter(Constants.USER_TIME_PARAM);
        inputs[4] = request.getParameter(Constants.ARRIVAL_START);
        inputs[5] = request.getParameter(Constants.USER_REQUEST_DAY);

        EngineManager engine = ServletUtils.getEngineManager(getServletContext());
        TripRequestResponseDto tripRequestResponseDto = engine.createNewTripRequest(mapName, inputs, userName);
        String jsonTripRequests = new Gson().toJson(tripRequestResponseDto);
        response.getWriter().write(jsonTripRequests);
    }

}
