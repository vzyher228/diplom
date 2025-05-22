package by.vlad.fishingshop.controller.command.impl;

import by.vlad.fishingshop.controller.command.*;
import by.vlad.fishingshop.model.entity.Product;
import by.vlad.fishingshop.exception.ServiceException;
import by.vlad.fishingshop.model.repository.impl.specification.FindProductByNameSpecification;
import by.vlad.fishingshop.model.service.impl.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static by.vlad.fishingshop.controller.command.PagePath.*;
import static by.vlad.fishingshop.controller.command.PagePath.ERROR_PAGE;
import static by.vlad.fishingshop.controller.command.RequestAttribute.*;
import static by.vlad.fishingshop.controller.command.RequestAttribute.EXCEPTION;
import static by.vlad.fishingshop.controller.command.Router.RouterType.FORWARD;

public class SearchProductByProductNameCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String productName = request.getParameter(RequestParameter.PRODUCT_NAME);
        ProductServiceImpl service = ProductServiceImpl.getInstance();
        try {

            List<Product> productList = service.query(new FindProductByNameSpecification(productName));
            if (!productList.isEmpty()) {
                Product product = productList.get(0);
                request.setAttribute(PRODUCT, product);
            }else{
                request.setAttribute(RequestAttribute.FIND_NOTHING,true);
            }
            router = new Router(PRODUCT_PAGE, FORWARD);
        } catch (ServiceException e) {
            request.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
