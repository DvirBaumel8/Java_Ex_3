package manager.servlets;

import Engine.UsersManagment.UsersManager;
import manager.constans.Constants;
import manager.utils.ServletUtils;
import manager.utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static manager.constans.Constants.USERNAME;


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
        String userUniqueName = SessionUtils.getUsername(request);
        String userType = request.getParameter(Constants.USER_TYPE);
        UsersManager userManager = ServletUtils.getUserManager(getServletContext());

                synchronized (this) {
                    if (userManager.isUserExists(userUniqueName)) {
                        String errorMessage = "Username " + userUniqueName + " already exists. Please enter a different username.";
                        request.setAttribute(Constants.USER_NAME_ERROR, errorMessage);
                        getServletContext().getRequestDispatcher(LOGIN_ERROR_URL).forward(request, response);
                    } else {
                        userManager.addUser(userUniqueName, userType);
                        request.getSession(true).setAttribute(USERNAME, userUniqueName);
                        System.out.println("On login, request URI is: " + request.getRequestURI());
                        response.sendRedirect(USER_DETAILS_URL);
                    }
        }
    }

}
