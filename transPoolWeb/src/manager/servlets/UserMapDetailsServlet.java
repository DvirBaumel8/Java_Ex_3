package manager.servlets;


import com.google.gson.Gson;
import engine.manager.EngineManager;
import engine.manager.UserDetailsDto;
import manager.constans.Constants;
import manager.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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


        UserDetailsDto userDetailsDto = engine.getUserDetailsDto(userName);
        String userDetailsDtoJson = new Gson().toJson(userDetailsDto);
        response.getWriter().write(userDetailsDtoJson);
    }
}

