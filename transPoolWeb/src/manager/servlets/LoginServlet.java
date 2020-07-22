package manager.servlets;

import manager.UserManagerDto;
import manager.constans.Constants;
import manager.utils.ServletUtils;
import manager.utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

import static manager.constans.Constants.USER_NAME;

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String userName = request.getParameter(Constants.USER_NAME);
        String userType = request.getParameter(Constants.USER_TYPE);
        UserManagerDto userManagerDto = ServletUtils.getUserManager(getServletContext());
        String errorMessage = validateUserLoginInputs(userType, userName);

                synchronized (this) {
                    try {
                        if(errorMessage == null) {
                            userManagerDto.addUser(userName, userType);
                            request.getSession(true).setAttribute(USER_NAME, userName);
                            System.out.println("On login, request URI is: " + request.getRequestURI());
                            response.sendRedirect(USER_DETAILS_URL);
                        }
                        else {
                            System.out.println(errorMessage);
                            response.sendRedirect(SIGN_UP_URL);
                        }
                    }
                    catch (Exception exception) {
                        System.out.println(errorMessage);
                        response.sendRedirect(SIGN_UP_URL);
                    }
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
        //EngineManager engineManager = EngineManager.getEngineManagerInstance();

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
