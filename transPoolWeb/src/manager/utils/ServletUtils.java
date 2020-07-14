package manager.utils;

import Engine.UsersManagment.UsersManager;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static manager.constans.Constants.INT_PARAMETER_ERROR;

public class ServletUtils {

    private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";
    //private static final String CHAT_MANAGER_ATTRIBUTE_NAME = "chatManager";

    /*
    Note how the synchronization is done only on the question and\or creation of the relevant managers and once they exists -
    the actual fetch of them is remained un-synchronized for performance POV
     */
    private static final Object userManagerLock = new Object();
    //private static final Object chatManagerLock = new Object();

    public static UsersManager getUserManager(ServletContext servletContext) {
        UsersManager userManager = new UsersManager();
        try {
            synchronized (userManagerLock) {
                if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null) {
                    servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UsersManager());
                }
            }
            userManager = (UsersManager) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return userManager;
    }

    public static int getIntParameter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException numberFormatException) {
            }
        }
        return INT_PARAMETER_ERROR;
    }
}
