package manager.servlets;


import com.google.gson.Gson;
import engine.dto.mapPage.TripRequestDto;
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
        //inputs[3] = request.getParameter(Constants.USER_REQUEST_TIME);
        inputs[3] = "10:20";
        //inputs[4] = request.getParameter(Constants.USER_TIME_PARAM);
        inputs[4] = "S";
        //inputs[5] = request.getParameter(Constants.USER_REQUEST_DAY);
        inputs[5] = "2";
        //Trip request params from user: update in UI and send to servlet
        //1. request owner name - Done
        //2. request source station - Done
        //3. request destination station  Done
        //4. user request time (arrival/start) - need to update
        //5. user request time param (insert S or A) to decide if he want to ask by arrival time or start time - need to update
        //6. user request day - need to update

        EngineManager engine = ServletUtils.getEngineManager(getServletContext());

        try {
            //String x = new String();
            //int y = Integer.parseInt(x);
            engine.createNewTripRequest(mapName, inputs);
            List<TripRequestDto> tripRequestsDto = engine.getAllTripRequestsDto(mapName, userName);
            String jsonTripRequests = new Gson().toJson(tripRequestsDto);
            response.getWriter().write(jsonTripRequests);
        }
        catch (Exception ex) {
            String error = ex.getMessage();
            String json = new Gson().toJson(error);
            response.getWriter().write(json);
            //Display Error to user
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "The  not found.");
        }
    }

}
