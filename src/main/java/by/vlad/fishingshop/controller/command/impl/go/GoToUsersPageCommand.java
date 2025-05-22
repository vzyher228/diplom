package by.vlad.fishingshop.controller.command.impl.go;

import by.vlad.fishingshop.controller.command.Command;
import by.vlad.fishingshop.controller.command.PagePath;
import by.vlad.fishingshop.controller.command.RequestAttribute;
import by.vlad.fishingshop.controller.command.Router;
import by.vlad.fishingshop.model.entity.User;
import by.vlad.fishingshop.exception.ServiceException;
import by.vlad.fishingshop.model.repository.impl.specification.FindAllUsersSpecification;
import by.vlad.fishingshop.model.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.vlad.fishingshop.controller.command.RequestAttribute.USER_LIST;
import static by.vlad.fishingshop.controller.command.Router.RouterType.FORWARD;
import static by.vlad.fishingshop.controller.command.Router.RouterType.REDIRECT;

public class GoToUsersPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        UserServiceImpl service = UserServiceImpl.getInstance();
        List<User> productList = null;
        try {
            productList = service.query(new FindAllUsersSpecification());
            request.setAttribute(USER_LIST,productList);
            router = new Router(PagePath.USERS_PAGE, FORWARD );
        } catch (ServiceException e) {
            //log
            request.setAttribute(RequestAttribute.EXCEPTION, e);
            router = new Router(PagePath.ERROR_PAGE, REDIRECT);
        }

        return router;
    }
}
