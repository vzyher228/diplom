package by.vlad.fishingshop.controller.listener;

import by.vlad.fishingshop.controller.command.PagePath;
import by.vlad.fishingshop.controller.command.RequestParameter;
import by.vlad.fishingshop.controller.command.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@WebListener()
public class ApiRequestListenerImpl implements ServletRequestListener {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest servletRequest = sre.getServletRequest();
        try {
            servletRequest.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Incorrect encoding", e);
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String command = request.getParameter(RequestParameter.COMMAND);
        if (command != null) {
            if (command.compareToIgnoreCase("change_locale_command") != 0) {
                String prevRequest = PagePath.CONTROLLER_URL + request.getQueryString();
                HttpSession session = request.getSession(true);
                session.setAttribute(SessionAttribute.PREVIOUS_REQUEST, prevRequest);
            }
        }
    }
}
