package manager.servlets;


import com.google.gson.Gson;
import engine.manager.EngineManager;
import manager.constans.Constants;
import manager.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RatingDriversServlet", urlPatterns = {"/pages/mapDetails/RatingDriversServlet"})
public class RatingDriversServlet extends HttpServlet {

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
        String tripSuggestId = request.getParameter(Constants.CHOSEN_DRIVER);
        String ratingNumber = request.getParameter(Constants.RATING_NUMBER);
        String ratingNotes = request.getParameter(Constants.RATING_NOTES);
        String tripRequestId = request.getParameter(Constants.TRIP_REQUEST_ID);
        String mapName = request.getParameter(Constants.MAP_NAME);

        EngineManager engine = ServletUtils.getEngineManager(getServletContext());
        String error = engine.rankDriver(mapName, Integer.parseInt(tripRequestId), Integer.parseInt(tripSuggestId), ratingNumber, ratingNotes);
        String errorJson = new Gson().toJson(error);
        response.getWriter().write(errorJson);
    }

}
