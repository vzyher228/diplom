package by.vlad.fishingshop.controller.command.impl;

import by.vlad.fishingshop.controller.command.*;
import by.vlad.fishingshop.model.entity.User;
import by.vlad.fishingshop.exception.ServiceException;
import by.vlad.fishingshop.model.repository.impl.specification.FindByLoginSpecification;
import by.vlad.fishingshop.model.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

import static by.vlad.fishingshop.controller.command.PagePath.ERROR_PAGE;
import static by.vlad.fishingshop.controller.command.PagePath.USER_DATA_PAGE;
import static by.vlad.fishingshop.controller.command.RequestAttribute.EXCEPTION;
import static by.vlad.fishingshop.controller.command.Router.RouterType.FORWARD;

public class SearchUserByLoginCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String login = request.getParameter(RequestParameter.LOGIN);
        UserServiceImpl service = UserServiceImpl.getInstance();
        try {
            User user = (User) service.query(new FindByLoginSpecification(login)).get(0);
            request.setAttribute(RequestAttribute.USER, user);
            router = new Router(USER_DATA_PAGE, FORWARD);
        } catch (ServiceException e) {
            request.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
