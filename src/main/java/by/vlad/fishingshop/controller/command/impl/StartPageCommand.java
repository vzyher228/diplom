package by.vlad.fishingshop.controller.command.impl;

import by.vlad.fishingshop.controller.command.*;
import by.vlad.fishingshop.model.entity.Product;
import by.vlad.fishingshop.model.repository.impl.specification.FindAllProductsSpecification;
import by.vlad.fishingshop.model.service.impl.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.vlad.fishingshop.controller.command.RequestAttribute.PRODUCT_LIST;

public class StartPageCommand implements Command {
//    @Override
//    public Router execute(HttpServletRequest request) {
//        Router router;
//        ProductServiceImpl service = ProductServiceImpl.getInstance();
//        HttpSession session = request.getSession(true);
//        //boolean notAuthenticated = (boolean) session.getAttribute(SessionAttribute.NOT_AUTHENTICATED);
//        boolean notAuthenticated = Boolean.TRUE.equals(session.getAttribute(SessionAttribute.NOT_AUTHENTICATED));
//        request.setAttribute(RequestAttribute.NOT_AUTHENTICATED, notAuthenticated);
//        List<Product> productList = null;
//        try {
//            productList = service.query(new FindAllProductsSpecification());
//            request.setAttribute(PRODUCT_LIST, productList);
//            router = new Router(PagePath.START_PAGE, FORWARD);
//        } catch (ServiceException e) {
//            //log
//            request.setAttribute(RequestAttribute.EXCEPTION, e);
//            router = new Router(PagePath.ERROR_PAGE, FORWARD);
//        }
//
//        return router;
//}
    @Override
    public Router execute(HttpServletRequest request) {
        try {
            ProductServiceImpl service = ProductServiceImpl.getInstance();
            HttpSession session = request.getSession(true);
            boolean notAuthenticated = Boolean.TRUE.equals(session.getAttribute(SessionAttribute.NOT_AUTHENTICATED));
            request.setAttribute(RequestAttribute.NOT_AUTHENTICATED, notAuthenticated);

            List<Product> productList = service.query(new FindAllProductsSpecification());
            request.setAttribute(PRODUCT_LIST, productList);

            return new Router(PagePath.START_PAGE, Router.RouterType.FORWARD);
        } catch (Throwable e) {
            // Логируем полный stacktrace
            e.printStackTrace(); // или logger.error("Failed to load start page", e);
            request.setAttribute(RequestAttribute.EXCEPTION, e);
            return new Router(PagePath.ERROR_PAGE, Router.RouterType.FORWARD);
        }
    }
}
