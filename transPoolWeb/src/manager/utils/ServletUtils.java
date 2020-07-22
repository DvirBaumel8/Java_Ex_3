package manager.utils;
import Engine.manager.EngineManager;
import manager.UserManagerDto;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import static manager.constans.Constants.INT_PARAMETER_ERROR;

public class ServletUtils {

    private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";
    private static final String ENGINE_MANAGER_ATTRIBUTE_NAME = "engineManager";

    /*
    Note how the synchronization is done only on the question and\or creation of the relevant managers and once they exists -
    the actual fetch of them is remained un-synchronized for performance POV
     */
    private static final Object userManagerLock = new Object();
    private static final Object engineLock = new Object();

    public static UserManagerDto getUserManager(ServletContext servletContext) {
        synchronized (userManagerLock) {
            if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UserManagerDto());
            }
        }
        return (UserManagerDto) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
    }

    public static EngineManager getEngineManager(ServletContext servletContext) {
        synchronized (engineLock) {
            if (servletContext.getAttribute(ENGINE_MANAGER_ATTRIBUTE_NAME) == null) {
                EngineManager engineManager = EngineManager.getEngineManagerInstance();
                servletContext.setAttribute(ENGINE_MANAGER_ATTRIBUTE_NAME, engineManager);
            }
        }
        return (EngineManager) servletContext.getAttribute(ENGINE_MANAGER_ATTRIBUTE_NAME);
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
