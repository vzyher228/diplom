package by.vlad.fishingshop.controller.command.impl.go;

import by.vlad.fishingshop.controller.command.Command;
import by.vlad.fishingshop.controller.command.RequestAttribute;
import by.vlad.fishingshop.controller.command.RequestParameter;
import by.vlad.fishingshop.controller.command.Router;
import by.vlad.fishingshop.model.entity.Status;
import by.vlad.fishingshop.model.entity.User;
import by.vlad.fishingshop.exception.ServiceException;
import by.vlad.fishingshop.model.repository.impl.specification.FindByLoginSpecification;
import by.vlad.fishingshop.model.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static by.vlad.fishingshop.controller.command.PagePath.*;
import static by.vlad.fishingshop.controller.command.RequestAttribute.EXCEPTION;
import static by.vlad.fishingshop.controller.command.Router.RouterType.FORWARD;
import static by.vlad.fishingshop.controller.command.Router.RouterType.REDIRECT;

public class GoToActivationPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        UserServiceImpl service = UserServiceImpl.getInstance();
        String login = request.getParameter(RequestParameter.LOGIN);
        try {
            User user = service.query(new FindByLoginSpecification(login)).get(0);
            if (user.getStatus() == Status.NOT_ACTIVATED) {
                request.setAttribute(RequestAttribute.USER, user);
                router = new Router(ACTIVATION_PAGE, FORWARD);
            } else {
                router = new Router(GO_TO_START_PAGE, REDIRECT);
            }
        } catch (ServiceException e) {
            request.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
//@Override
//public Router execute(HttpServletRequest request) {
//    Router router;
//    UserServiceImpl service = UserServiceImpl.getInstance();
//    String login = request.getParameter(RequestParameter.LOGIN);
//    try {
//        List<User> users = service.query(new FindByLoginSpecification(login));
//        if (users != null && !users.isEmpty()) {
//            User user = users.get(0);
//            if (user.getStatus() == Status.NOT_ACTIVATED) {
//                request.setAttribute(RequestAttribute.USER, user);
//                router = new Router(ACTIVATION_PAGE, FORWARD);
//            } else {
//                router = new Router(GO_TO_START_PAGE, REDIRECT);
//            }
//        } else {
//            // Пользователь с таким логином не найден
//            request.setAttribute(RequestAttribute.MESSAGE, "Пользователь не найден или ссылка недействительна.");
//            router = new Router(ACTIVATION_PAGE, FORWARD); // Можно перекинуть на страницу активации с сообщением
//        }
//    } catch (ServiceException e) {
//        request.setAttribute(EXCEPTION, e);
//        router = new Router(ERROR_PAGE, FORWARD);
//    }
//    return router;
//}
//
//}

