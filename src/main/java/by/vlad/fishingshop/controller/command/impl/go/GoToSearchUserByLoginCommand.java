package by.vlad.fishingshop.controller.command.impl.go;

import by.vlad.fishingshop.controller.command.Command;
import by.vlad.fishingshop.controller.command.Router;

import javax.servlet.http.HttpServletRequest;

import static by.vlad.fishingshop.controller.command.PagePath.SEARCH_BY_LOGIN_PAGE;
import static by.vlad.fishingshop.controller.command.Router.RouterType.REDIRECT;

public class GoToSearchUserByLoginCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(SEARCH_BY_LOGIN_PAGE, REDIRECT);
    }
}
