package by.vlad.fishingshop.controller.listener;

import by.vlad.fishingshop.model.entity.Cart;
import by.vlad.fishingshop.model.entity.Role;
import by.vlad.fishingshop.model.entity.User;
import by.vlad.fishingshop.exception.ServiceException;
import by.vlad.fishingshop.model.service.ClearCartService;
import by.vlad.fishingshop.model.service.impl.ClearCartServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import java.util.Locale;

import static by.vlad.fishingshop.controller.command.SessionAttribute.CART;
import static by.vlad.fishingshop.controller.command.SessionAttribute.LOCALE;
import static by.vlad.fishingshop.controller.command.SessionAttribute.USER;

@WebListener
public class ApiSessionListenerImpl implements HttpSessionListener {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setMaxInactiveInterval(20 * 60);
        session.setAttribute(LOCALE, Locale.getDefault());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        User user = (User) session.getAttribute(USER);
        if (user.getRole() == Role.USER) {
            Cart cart = (Cart) session.getAttribute(CART);
            ClearCartService clearCartService = ClearCartServiceImpl.getInstance();
            try {
                clearCartService.clearCart(cart);
            } catch (ServiceException e) {
                logger.error("Can not clear cart in session and insert into db products", e);
            }
        }
    }
}
