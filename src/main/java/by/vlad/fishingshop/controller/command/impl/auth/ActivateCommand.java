package by.vlad.fishingshop.controller.command.impl.auth;

import by.vlad.fishingshop.controller.command.*;
import by.vlad.fishingshop.model.entity.Role;
import by.vlad.fishingshop.model.entity.Status;
import by.vlad.fishingshop.model.entity.User;
import by.vlad.fishingshop.exception.ServiceException;
import by.vlad.fishingshop.model.service.impl.UserServiceImpl;
import by.vlad.fishingshop.model.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;

import static by.vlad.fishingshop.controller.command.PagePath.*;
import static by.vlad.fishingshop.controller.command.RequestAttribute.*;
import static by.vlad.fishingshop.controller.command.RequestParameter.*;
import static by.vlad.fishingshop.controller.command.Router.RouterType.*;

//public class ActivateCommand implements Command {
//    @Override
//    public Router execute(HttpServletRequest request) {
//        Router router;
//        UserServiceImpl service = UserServiceImpl.getInstance();
//        long id = Long.parseLong(request.getParameter(ID));
//        String login = request.getParameter(LOGIN);
//        String password = request.getParameter(PASSWORD);
//        String email = request.getParameter(EMAIL);
//        String lastName = request.getParameter(LAST_NAME);
//        String name = request.getParameter(NAME);
//        String phone = request.getParameter(PHONE);
//        User user = new User.UserBuilder()
//                .setId(id)
//                .setLogin(login)
//                .setPassword(password)
//                .setEmail(email)
//                .setName(name)
//                .setLastName(lastName)
//                .setPhone(phone)
//                .setRole(Role.USER)
//                .setStatus(Status.ACTIVATED)
//                .build();
//        try {
//            if (service.update(user)) {
//                router = new Router(GO_TO_START_PAGE, REDIRECT);
//            } else {
//                setRequestAttributes(request, user);
//                router = new Router(GO_TO_ACTIVATION_PAGE, FORWARD);
//            }
//        } catch (ServiceException e) {
//            request.setAttribute(EXCEPTION, e);
//            router = new Router(ERROR_PAGE, FORWARD);
//        }
//        return router;
//    }
//
//    private void setRequestAttributes(HttpServletRequest request, User user) {
//        if (!UserValidator.isValidPhone(user.getPhone())) {
//            request.setAttribute(INVALID_PHONE, true);
//            user.setPhone(null);
//        }
//        if (!UserValidator.isValidName(user.getName())) {
//            request.setAttribute(INVALID_NAME, true);
//            user.setName(null);
//        }
//        if (!UserValidator.isValidLastName(user.getLastName())) {
//            request.setAttribute(INVALID_LAST_NAME, true);
//            user.setLastName(null);
//        }
//        request.setAttribute(USER, user);
//    }
//
//}


public class ActivateCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        UserServiceImpl service = UserServiceImpl.getInstance();
        long id = Long.parseLong(request.getParameter(ID));
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String email = request.getParameter(EMAIL);
        String lastName = request.getParameter(LAST_NAME);
        String name = request.getParameter(NAME);
        String phone = request.getParameter(PHONE);
        User user = new User.UserBuilder()
                .setId(id)
                .setLogin(login)
                .setPassword(password)
                .setEmail(email)
                .setName(name)
                .setLastName(lastName)
                .setPhone(phone)
                .setRole(Role.USER)
                .setStatus(Status.ACTIVATED)
                .build();
        try {
            if (service.update(user)) {
                router = new Router(GO_TO_START_PAGE, REDIRECT);
            } else {
                setRequestAttributes(request, user);
                router = new Router(GO_TO_ACTIVATION_PAGE, FORWARD);
            }
        } catch (ServiceException e) {
            request.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }

    private void setRequestAttributes(HttpServletRequest request, User user) {
        if (!UserValidator.isValidPhone(user.getPhone())) {
            request.setAttribute(INVALID_PHONE, true);
            user.setPhone(null);
        }
        if (!UserValidator.isValidName(user.getName())) {
            request.setAttribute(INVALID_NAME, true);
            user.setName(null);
        }
        if (!UserValidator.isValidLastName(user.getLastName())) {
            request.setAttribute(INVALID_LAST_NAME, true);
            user.setLastName(null);
        }
        request.setAttribute(USER, user);
    }

}

