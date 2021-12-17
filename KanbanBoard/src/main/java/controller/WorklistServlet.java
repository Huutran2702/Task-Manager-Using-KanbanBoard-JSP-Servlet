package controller;

import dao.*;
import model.User;
import model.Work;
import model.WorkList;
import model.Workspace;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "WorklistServlet",value = "/workList")
public class WorklistServlet extends HttpServlet {
    private IUserDAO userDAO;
    private static IWorkspaceDAO workspaceDAO;
    private static IWorklistDAO worklistDAO;
    private static IWorkDAO workDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        workspaceDAO = new WorkspaceDAO();
        worklistDAO = new WorklistDAO();
        workDAO = new WorkDAO();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String name = request.getParameter("newWorkList");
        if (action==null) {
            action = "";
        }
        switch (action) {
            case "addWorkList" :
            default:
                try {
                    addNewWorkList(name,request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    private void addNewWorkList(String newWorklistName,HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        response.setContentType("text/html/charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        HttpSession session1 = request.getSession();
        Integer id = (Integer) session1.getAttribute("wspID");
        if (!newWorklistName.equals("")) {
            worklistDAO.insertWorkList(new WorkList(newWorklistName,id));
        }
        List<Workspace> workspaces = workspaceDAO.selectAllWorkspaceByEmail(user.getEmail());
        List<Workspace> favoriteWorkspace = workspaceDAO.selectAllFavoriteWorkspaceByEmail(user.getEmail());
        if (workspaces.size()==0) {
            workspaceDAO.insertWorkspace(new Workspace( "Không gian chính of "+user.getName()));
            workspaceDAO.shareWorkspace(user.getId(), workspaceDAO.selectWorkspaceByName("Không gian chính of "+user.getName()).getId());
            workspaces = workspaceDAO.selectAllWorkspaceByEmail(user.getEmail());
        }
        List<WorkList> worklists = worklistDAO.selectWorkListByWorkspaceID(id);
        Map<Integer, List<Work>> works = new HashMap<>();
        for (WorkList worklist: worklists
        ) {
            works.put(worklist.getId(),workDAO.selectWorkByWorkListID(worklist.getId()));
        }
        request.setAttribute("account",user.getName());
        request.setAttribute("favoriteWorkspace",favoriteWorkspace );
        request.setAttribute("workspaces",workspaces );
        request.setAttribute("worklists",worklists);
        request.setAttribute("works",works);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/home/dashboard.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html/charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String name = request.getParameter("newWorkList");
        if (action==null) {
            action = "";
        }
        switch (action) {
            case "addWorkList" :
            default:
                try {
                    addNewWorkList(name,request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
