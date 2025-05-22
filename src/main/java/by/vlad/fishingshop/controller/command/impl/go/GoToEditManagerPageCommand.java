package by.vlad.fishingshop.controller.command.impl.go;

import by.vlad.fishingshop.controller.command.Command;
import by.vlad.fishingshop.controller.command.Router;
import by.vlad.fishingshop.model.entity.User;

import javax.servlet.http.HttpServletRequest;

import static by.vlad.fishingshop.controller.command.PagePath.EDIT_MANAGER_PAGE;
import static by.vlad.fishingshop.controller.command.RequestAttribute.*;
import static by.vlad.fishingshop.controller.command.Router.RouterType.FORWARD;

public class GoToEditManagerPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        request.setAttribute(USER, new User());
        return new Router(EDIT_MANAGER_PAGE, FORWARD);
    }
}
