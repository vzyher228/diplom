package by.vlad.fishingshop.controller.command.impl.go;

import by.vlad.fishingshop.controller.command.Command;
import by.vlad.fishingshop.controller.command.PagePath;
import by.vlad.fishingshop.controller.command.Router;

import javax.servlet.http.HttpServletRequest;

public class GoToEditProductPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.EDIT_PRODUCT,Router.RouterType.REDIRECT);
    }
}
