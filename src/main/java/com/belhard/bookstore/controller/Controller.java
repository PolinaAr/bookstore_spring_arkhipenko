package com.belhard.bookstore.controller;

import com.belhard.bookstore.ContextController;
import com.belhard.bookstore.controller.command.Command;
import com.belhard.bookstore.exceptions.BookException;
import com.belhard.bookstore.exceptions.UserException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    private AnnotationConfigApplicationContext context;
    private static final Logger logger = LogManager.getLogger(Controller.class);

    @Override
    public void init() {
        context = new AnnotationConfigApplicationContext(ContextController.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            try {
                processRequest(req, resp);
                resp.setStatus(200);
            } catch (BookException e) {
                logger.error("User call get book", e);
                resp.setStatus(404);
                req.setAttribute("message", "There is no this book.");
                req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
            } catch (UserException e) {
                logger.error("User call get user", e);
                resp.setStatus(404);
                req.setAttribute("message", "There is no this user");
                req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
            } catch (NumberFormatException e) {
                logger.error("Illegal request", e);
                resp.setStatus(404);
                req.setAttribute("message", "There is no such page");
                req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
            }
        } catch (ServletException | IOException e) {
            resp.setStatus(404);
            logger.error("User call GET", e);
        }
    }


    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("command");
        Command command;
        try {
            command = (Command) context.getBean(action);
        } catch (NoSuchBeanDefinitionException e){
            command = (Command) context.getBean("error");
        }
        String page = command.execute(req);
        req.getRequestDispatcher(page).forward(req, resp);
    }
}
