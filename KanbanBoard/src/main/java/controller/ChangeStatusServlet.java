package controller;

import dao.UserDAO;
import model.Status;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ChangeStatusServlet", value = "/status")
public class ChangeStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action= "";
        }
        switch (action) {
            case "lock":
                try {
                    lockUser(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "active":
                try {
                    activeUser(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void activeUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String userEmail = request.getParameter("activeUserEmail");
        UserDAO userDAO = new UserDAO();
        User user = userDAO.selectUser(userEmail);
        user.setStatus(Status.ACTIVE);
        userDAO.updateUser(user);
        List<User> users = userDAO.selectAllUsers();
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute("admin");
        request.setAttribute("account",admin.getName());
        request.setAttribute("users",users);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/users/index.jsp");
        dispatcher.forward(request, response);
    }

    private void lockUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String userEmail = request.getParameter("lockUserEmail");
        UserDAO userDAO = new UserDAO();
        User user = userDAO.selectUser(userEmail);
        user.setStatus(Status.LOCK);
        userDAO.updateUser(user);
        List<User> users = userDAO.selectAllUsers();
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute("admin");
        request.setAttribute("account",admin.getName());
        request.setAttribute("users",users);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/users/index.jsp");
        dispatcher.forward(request, response);

    }
}
