package by.vlad.fishingshop.controller;

import by.vlad.fishingshop.controller.command.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/ApiController")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private final CommandProvider COMMAND_PROVIDER = CommandProvider.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(RequestParameter.COMMAND);
        logger.debug("Received command: " + commandName);
        Command command = COMMAND_PROVIDER.getCommand(commandName);
        if (command == null) {
            logger.error("Command is null for commandName: " + commandName);
            response.sendRedirect(PagePath.ERROR_PAGE);
            return;
        }

        Router router = command.execute(request);
        if (router == null || router.getPagePath() == null) {
            logger.error("Router or router path is null for command: " + commandName);
            response.sendRedirect(PagePath.ERROR_PAGE);
            return;
        }
        switch (router.getRouterType()) {
            case REDIRECT:
                response.sendRedirect(router.getPagePath());
                break;
            case FORWARD:
                RequestDispatcher dispatcher = request.getRequestDispatcher(router.getPagePath());

                dispatcher.forward(request, response);
                break;
            default:
                logger.error("incorrect route type " + router.getRouterType());
                response.sendRedirect(PagePath.ERROR_PAGE);
        }
    }
}


