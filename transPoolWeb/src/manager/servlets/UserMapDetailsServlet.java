package manager.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import engine.maps.MapsTableElementDetails;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


@WebServlet(name = "UserMapDetailsServlet", urlPatterns = {"/pages/userDetails/UserMapDetailsServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UserMapDetailsServlet extends HttpServlet {
    private final String USER_DETAILS_URL = "../userDetails/userDetails.html";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
       // Gson gson = builder.create();
        //EngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        //MapsTableElementDetailsd mapsTableElementDetails = new MapsTableElementDetailsd();
        //mapsTableElementDetails = mapsTableElementDetails.getMapTableElementDetailsCheck();
        //List<MapsTableElementDetails> mapsTableElementDetailsList = engineManager.getMapsManager()
          //      .getAllMapsTableElementsDetailsCheck();

        List<MapsTableElementDetailsCheck> res = new LinkedList<>();
        MapsTableElementDetailsCheck mapsTableElementDetails = new MapsTableElementDetailsCheck();
        mapsTableElementDetails.setMatchedTripRequestQuantity(3);
        mapsTableElementDetails.setUserNameOwner("check");
        mapsTableElementDetails.setMapName("x");
        mapsTableElementDetails.setStationsQuantity(3);
        mapsTableElementDetails.setRoadsQuantity(4);
        mapsTableElementDetails.setTripSuggestsQuantity(2);
        mapsTableElementDetails.setTripRequestQuantity(4);
        mapsTableElementDetails.setMatchedTripRequestQuantity(1);
        res.add(mapsTableElementDetails);

        String json = new Gson().toJson(res);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}

