package by.vlad.fishingshop.controller.command.impl.go;

import by.vlad.fishingshop.controller.command.Command;
import by.vlad.fishingshop.controller.command.Router;

import javax.servlet.http.HttpServletRequest;

import static by.vlad.fishingshop.controller.command.PagePath.*;
import static by.vlad.fishingshop.controller.command.Router.RouterType.REDIRECT;

public class GoToSignUpPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {

        return new Router(SIGN_UP_PAGE,REDIRECT);
    }
}
