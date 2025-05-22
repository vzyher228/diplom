package by.vlad.fishingshop.controller.command.impl;

import by.vlad.fishingshop.controller.command.Command;
import by.vlad.fishingshop.controller.command.Router;
import by.vlad.fishingshop.model.entity.Role;
import by.vlad.fishingshop.model.entity.Status;
import by.vlad.fishingshop.model.entity.User;
import by.vlad.fishingshop.exception.ServiceException;
import by.vlad.fishingshop.model.repository.impl.specification.FindByLoginSpecification;
import by.vlad.fishingshop.model.service.impl.UserServiceImpl;
import by.vlad.fishingshop.model.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.vlad.fishingshop.controller.command.PagePath.*;
import static by.vlad.fishingshop.controller.command.RequestAttribute.*;
import static by.vlad.fishingshop.controller.command.RequestParameter.*;
import static by.vlad.fishingshop.controller.command.Router.RouterType.*;
import static by.vlad.fishingshop.controller.command.Router.RouterType.FORWARD;

public class EditManagerCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        UserServiceImpl service = UserServiceImpl.getInstance();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String email = request.getParameter(EMAIL);
        String lastName = request.getParameter(LAST_NAME);
        String name = request.getParameter(NAME);
        String phone = request.getParameter(PHONE);
        User user = new User.UserBuilder()
                .setLogin(login)
                .setPassword(password)
                .setName(name)
                .setLastName(lastName)
                .setPhone(phone)
                .setEmail(email)
                .setRole(Role.MANAGER)
                .setStatus(Status.ACTIVATED)
                .build();
        if (UserValidator.isValidLogin(login)) {
            try {
                List<User> userList = service.query(new FindByLoginSpecification(login));
                if (userList.isEmpty()) {
                    String confirmPassword = request.getParameter(CONFIRM_PASSWORD);
                    if (UserValidator.isIdenticalPasswords(password,confirmPassword)) {
                        if (service.insert(user)) {
                            router = new Router(GO_TO_ADMIN_PAGE, REDIRECT);
                        }else {
                            setRequestAttributes(request, user);
                            router = new Router(EDIT_MANAGER_PAGE, FORWARD);
                        }
                    } else {
                        request.setAttribute(INVALID_PASSWORDS, true);
                        setRequestAttributes(request, user);
                        router = new Router(EDIT_MANAGER_PAGE, FORWARD);
                    }
                } else {
                    request.setAttribute(BOOKED_LOGIN, true);
                    setRequestAttributes(request, user);
                    router = new Router(EDIT_MANAGER_PAGE, FORWARD);
                }
            } catch (ServiceException e) {
                request.setAttribute(EXCEPTION, e);
                router = new Router(ERROR_PAGE, FORWARD);
            }
        } else {
            setRequestAttributes(request, user);
            router = new Router(EDIT_MANAGER_PAGE, FORWARD);
        }
        return router;
    }

    private void setRequestAttributes(HttpServletRequest request, User user) {
        if (!UserValidator.isValidEmail(user.getEmail())) {
            request.setAttribute(INVALID_EMAIL, true);
            user.setEmail(null);
        }
        if (!UserValidator.isValidLogin(user.getLogin())) {
            request.setAttribute(INVALID_LOGIN, true);
            user.setLogin(null);
        }
        if (!UserValidator.isValidPassword(user.getPassword())) {
            request.setAttribute(INVALID_PASSWORD, true);
            user.setPassword(null);
        }
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
