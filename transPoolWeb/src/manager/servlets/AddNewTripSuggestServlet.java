package manager.servlets;

import com.google.gson.Gson;
import engine.dto.mapPage.TripSuggestDto;
import engine.dto.mapPage.TripSuggestResponseDto;
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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String mapName = request.getParameter(Constants.MAP_NAME);
        String userName = request.getParameter(Constants.USER_NAME);

        String[] inputs = new String[7];
        inputs[0] = request.getParameter(Constants.USER_SUGGEST_NAME);
        inputs[1] = request.getParameter(Constants.USER_SUGGEST_ROUTE);
        inputs[2] = request.getParameter(Constants.USER_DEPARTURE_DAY);
        inputs[3] = request.getParameter(Constants.USER_SUGGEST_DEPARTURE_TIME);
        inputs[4] = request.getParameter(Constants.USER_SCHEDULE_INT);
        inputs[5] = request.getParameter(Constants.USER_SUGGEST_PPK);
        inputs[6] = request.getParameter(Constants.USER_SUGGEST_PASSENGER_CAPACITY);

        EngineManager engine = ServletUtils.getEngineManager(getServletContext());
        TripSuggestResponseDto tripSuggestResponseDto = engine.createNewTripSuggest(mapName, inputs, userName);
        String jsonTripSuggests = new Gson().toJson(tripSuggestResponseDto);
        response.getWriter().write(jsonTripSuggests);

    }
}
