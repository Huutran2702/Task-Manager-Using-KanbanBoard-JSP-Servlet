package controller;

import dao.*;
import model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UserServlet", value = "/kanban")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserDAO userDAO;
    private IWorkspaceDAO workspaceDAO;
    private IWorklistDAO worklistDAO;
    private IWorkDAO workDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        workspaceDAO = new WorkspaceDAO();
        worklistDAO = new WorklistDAO();
        workDAO = new WorkDAO();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html/charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        System.out.println(action);
        switch (action) {
            case "signup":
                signupUser(request, response);
                break;
            default:
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login/signin.jsp");
                dispatcher.forward(request, response);
        }
    }

    private void signupUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login/signup.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html/charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "home":
                try {
                    processRequest(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "register":
                registerRequest(request, response);
                break;

        }
    }

    private void registerRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setCharset(request, response);
        PrintWriter printWriter = response.getWriter();
        try {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("pass");
            String re_password = request.getParameter("re_pass");
            String agree = request.getParameter("agree-term");
            System.out.println(agree);

            User user = userDAO.selectUser(email);

            if (user != null) {
                alertRegister(request, response, "Account exist!", "alert-danger", "/signup.jsp");
            } else {

                if (password.equals(re_password)) {
                    userDAO.insertUser(new User(name, email, Role.USER, password, Status.ACTIVE));
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login/signin.jsp");
                    dispatcher.forward(request, response);
                } else {
                    alertRegister(request, response, "Re-enter password does not match!", "alert-warning", "/login/signup.jsp");
                }

            }
        } catch (SQLException | ServletException throwables) {
            throwables.printStackTrace();
        } finally {
            printWriter.close();
        }
    }

    private void setCharset(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType("text/html/charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        setCharset(request, response);
        try (PrintWriter out = response.getWriter()) {
            String email = request.getParameter("your_email");
            String password = request.getParameter("your_pass");
            User user = userDAO.selectUser(email);
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    if (user.getRole().equals(Role.ADMIN)) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute("admin",user);
                        request.setAttribute("account",user.getName());
                        request.setAttribute("users", userDAO.selectAllUsers());
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/users/index.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        if (user.getStatus().equals(Status.LOCK)) {
                            alertRegister(request, response, "Account have been locked!", "alert-danger", "/login/signin.jsp");
                        } else {
                            HttpSession session = request.getSession(true);
                            List<Workspace> workspaces = workspaceDAO.selectAllWorkspaceByEmail(user.getEmail());
                            List<Workspace> favoriteWorkspace = workspaceDAO.selectAllFavoriteWorkspaceByEmail(user.getEmail());
                            if (workspaces.size()==0) {
                                workspaceDAO.insertWorkspace(new Workspace( "Không gian chính of "+user.getName()));
                                workspaceDAO.shareWorkspace(user.getId(), workspaceDAO.selectWorkspaceByName("Không gian chính of "+user.getName()).getId());
                                workspaces = workspaceDAO.selectAllWorkspaceByEmail(user.getEmail());
                            }
                            List<WorkList> worklists = worklistDAO.selectWorkListByWorkspaceID(workspaces.get(0).getId());
                            Map<Integer, List<Work>> works = new HashMap<>();
                            for (WorkList worklist: worklists
                            ) {
                                works.put(worklist.getId(),workDAO.selectWorkByWorkListID(worklist.getId()));
                            }
                            session.setAttribute("account",user);
                            session.setAttribute("favoriteWorkspace",favoriteWorkspace);
                            session.setAttribute("workspaces",workspaces);
                            HttpSession id = request.getSession(true);
                            id.setAttribute("wspID",workspaces.get(0).getId());
                            request.setAttribute("account",user.getName());
                            request.setAttribute("favoriteWorkspace",favoriteWorkspace );
                            request.setAttribute("workspaces",workspaces );
                            request.setAttribute("worklists",worklists);
                            request.setAttribute("works",works);
                            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/home/dashboard.jsp");
                            dispatcher.forward(request, response);
                        }
                    }

                } else {
                    alertRegister(request, response, "Password invalid!", "alert-danger", "/login/signin.jsp");
                }
            } else {
                alertRegister(request, response, "Account not exist!", "alert-danger", "/login/signin.jsp");
            }
        }
    }

    private void alertRegister(HttpServletRequest request, HttpServletResponse response, String message, String style, String path) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.setAttribute("style", style);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

}
