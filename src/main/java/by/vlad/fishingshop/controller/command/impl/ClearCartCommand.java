package by.vlad.fishingshop.controller.command.impl;

import by.vlad.fishingshop.controller.command.Command;
import by.vlad.fishingshop.controller.command.Router;
import by.vlad.fishingshop.controller.command.SessionAttribute;
import by.vlad.fishingshop.model.entity.Cart;
import by.vlad.fishingshop.exception.ServiceException;
import by.vlad.fishingshop.model.service.ClearCartService;
import by.vlad.fishingshop.model.service.impl.ClearCartServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.vlad.fishingshop.controller.command.PagePath.ERROR_PAGE;
import static by.vlad.fishingshop.controller.command.PagePath.GO_TO_START_PAGE;
import static by.vlad.fishingshop.controller.command.RequestAttribute.EXCEPTION;
import static by.vlad.fishingshop.controller.command.Router.RouterType.FORWARD;
import static by.vlad.fishingshop.controller.command.Router.RouterType.REDIRECT;

public class ClearCartCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = null;
        HttpSession session = request.getSession(true);
        Cart cart = (Cart) session.getAttribute(SessionAttribute.CART);
        try {
            ClearCartService cartService = ClearCartServiceImpl.getInstance();
            cartService.clearCart(cart);
        } catch (ServiceException e) {
            request.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        cart.clearCart();
        if (router == null) {
            router = new Router(GO_TO_START_PAGE, REDIRECT);
        }
        return router;
    }
}
