package by.vlad.fishingshop.controller.command.impl;

import by.vlad.fishingshop.controller.command.Command;
import by.vlad.fishingshop.controller.command.Router;
import by.vlad.fishingshop.controller.command.SessionAttribute;
import by.vlad.fishingshop.model.entity.*;
import by.vlad.fishingshop.exception.ServiceException;
import by.vlad.fishingshop.model.service.impl.PurchaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.vlad.fishingshop.controller.command.PagePath.*;
import static by.vlad.fishingshop.controller.command.RequestAttribute.*;
import static by.vlad.fishingshop.controller.command.Router.RouterType.*;

public class PurchaseCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession(true);
        Cart cart = (Cart) session.getAttribute(SessionAttribute.CART);
        if (!cart.getProducts().isEmpty()) {
            User user = (User) session.getAttribute(SessionAttribute.USER);
            Purchase purchase = new Purchase(user.getId(), cart);
            try {
                PurchaseService service = PurchaseService.getInstance();
                service.insert(purchase);
                cart.clearCart();
                router = new Router(GO_TO_START_PAGE, REDIRECT);
            } catch (ServiceException e) {
                request.setAttribute(EXCEPTION, e);
                router = new Router(ERROR_PAGE, FORWARD);
            }
        } else {
            router = new Router(GO_TO_START_PAGE, REDIRECT);
        }
        return router;
    }
}
