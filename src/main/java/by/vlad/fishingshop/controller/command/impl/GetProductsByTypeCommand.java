package by.vlad.fishingshop.controller.command.impl;

import by.vlad.fishingshop.controller.command.Command;
import by.vlad.fishingshop.controller.command.RequestParameter;
import by.vlad.fishingshop.controller.command.Router;
import by.vlad.fishingshop.model.entity.Product;
import by.vlad.fishingshop.model.entity.ProductType;
import by.vlad.fishingshop.exception.ServiceException;
import by.vlad.fishingshop.model.repository.impl.specification.FindProductsByTypeSpecification;
import by.vlad.fishingshop.model.service.impl.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static by.vlad.fishingshop.controller.command.PagePath.ERROR_PAGE;
import static by.vlad.fishingshop.controller.command.PagePath.FIND_PRODUCTS_PAGE;
import static by.vlad.fishingshop.controller.command.RequestAttribute.*;
import static by.vlad.fishingshop.controller.command.Router.RouterType.FORWARD;

public class GetProductsByTypeCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String type = request.getParameter(RequestParameter.PRODUCT_TYPE);
        ProductType productType = ProductType.valueOf(type.toUpperCase());
        ProductServiceImpl service = ProductServiceImpl.getInstance();
        try {
            List<Product> productList =  service.query(new FindProductsByTypeSpecification(productType));
            request.setAttribute(PRODUCT_LIST,productList);
            router = new Router(FIND_PRODUCTS_PAGE, FORWARD);
        } catch (ServiceException e) {
            request.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
