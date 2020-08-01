package manager.servlets;


import com.google.gson.Gson;
import engine.dto.userPage.MapsTableElementDetailsDto;
import engine.manager.EngineManager;
import engine.manager.UserDetailsManager;
import manager.constans.Constants;
import manager.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        //Todo - send user name
        String userName = request.getParameter(Constants.USER_NAME);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        EngineManager engine = ServletUtils.getEngineManager(getServletContext());
        List<MapsTableElementDetailsDto> mapsTableElementDetailsDtoList = engine.getMapsTableElementDetailsDto("d");


        UserDetailsManager userDetailsManager = null;
        String userDetailsManagerJson = new Gson().toJson(userDetailsManager);
        response.getWriter().write(userDetailsManagerJson);
    }
}

