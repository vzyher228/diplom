package by.vlad.fishingshop.controller.command.impl;

import by.vlad.fishingshop.controller.command.Command;
import by.vlad.fishingshop.controller.command.PagePath;
import by.vlad.fishingshop.controller.command.Router;

import javax.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest req) {
        return new Router(PagePath.ERROR_404_PAGE, Router.RouterType.REDIRECT);
    }

}
