package by.vlad.fishingshop.controller.command.impl;

import by.vlad.fishingshop.controller.command.Command;
import by.vlad.fishingshop.controller.command.RequestParameter;
import by.vlad.fishingshop.controller.command.Router;
import by.vlad.fishingshop.model.entity.Product;
import by.vlad.fishingshop.exception.ServiceException;
import by.vlad.fishingshop.model.repository.impl.specification.FindByVendorSpecification;
import by.vlad.fishingshop.model.service.impl.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;

import static by.vlad.fishingshop.controller.command.PagePath.ERROR_PAGE;
import static by.vlad.fishingshop.controller.command.PagePath.PRODUCT_PAGE;
import static by.vlad.fishingshop.controller.command.RequestAttribute.EXCEPTION;
import static by.vlad.fishingshop.controller.command.RequestAttribute.PRODUCT;
import static by.vlad.fishingshop.controller.command.Router.RouterType.FORWARD;

public class SearchProductByVendorCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String vendor = request.getParameter(RequestParameter.VENDOR);
        ProductServiceImpl service = ProductServiceImpl.getInstance();
        try {
            Product product = service.query(new FindByVendorSpecification(vendor)).get(0);
            request.setAttribute(PRODUCT, product);
            router = new Router(PRODUCT_PAGE, FORWARD);
        } catch (ServiceException e) {
            request.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}


