package manager.servlets.signupPageServlet;

import com.google.gson.Gson;
import engine.manager.EngineManager;
import manager.constans.Constants;
import manager.popups.PopupTypesMess;
import manager.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "LoginServlet", urlPatterns = {"/pages/signup/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String USER_DETAILS_URL = "../userDetails/userDetails.html";
    private final String SIGN_UP_URL = "../signup/signup.html";
    private final String LOGIN_ERROR_URL = "/pages/loginerror/loginError.html";
    // must start with '/' since will be used in request dispatcher...
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.reset();
        String userName = request.getParameter(Constants.USER_NAME);
        String userType = null;
        String json = null;

        if(userName != null) {
            userType = request.getParameter(Constants.USER_TYPE);
            StringBuilder errorMessageLoginScreen = new StringBuilder();
            EngineManager engine = ServletUtils.getEngineManager(getServletContext());
            boolean isValid = engine.validateUserLoginParams(userName, userType, errorMessageLoginScreen);
            if(isValid) {
                userName = userName.trim();
                synchronized (this) {
                    try {
                        if(!engine.isUserExist(userName)) {
                            engine.addUser(userName, userType);
                        }
                        json = new Gson().toJson(PopupTypesMess.SUCCESSFUL_SIGN_IN);
                        response.getWriter().write(json);
                    }

                    catch (Exception exception) {
                        json = new Gson().toJson(exception.getMessage());
                        response.getWriter().write(json);
                    }
                }
            }
            else {
                json = new Gson().toJson(errorMessageLoginScreen.toString());
                response.getWriter().write(json);
            }
        }
        else {
            json = new Gson().toJson(PopupTypesMess.EMPTY_USER_MESSAGE);
            response.getWriter().write(json);
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private String validateUserLoginInputs(String userType, String userName)
            throws IOException {
        StringBuilder errorMessageSb = null;

        try {
            if(userType.equals("requestPassenger") || userType.equals("suggestPassenger")) {
                errorMessageSb = new StringBuilder();
                errorMessageSb.append("User Typ not chosen. please choose one");
            }
        }
        catch (Exception exception) {
            return null;
        }

        return null;
    }
}
