package controller;

import dao.*;
import model.User;
import model.Work;
import model.WorkList;
import model.Workspace;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "WorkspaceServlet", value = "/workspace")
public class WorkspaceServlet extends HttpServlet {
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
        response.setContentType("text/html/charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        int id = Integer.parseInt(request.getParameter("id"));
        if (action==null) {
            action = "";
        }
        switch (action) {
            case "select":
                try {
                    selectWorkspace(id,request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    public void selectWorkspace(int wspId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        HttpSession id = request.getSession();
        id.setAttribute("wspID",wspId);
        loadPage(wspId, request, response, user);
    }

    public static void sendRequest(User user,List<Workspace> workspaces,List<Workspace> favoriteWorkspace,int id, HttpServletRequest request) throws SQLException {
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
        request.setAttribute("thisWsp", workspaceDAO.selectWorkspace(id));
        request.setAttribute("favoriteWorkspace",favoriteWorkspace );
        request.setAttribute("workspaces",workspaces );
        request.setAttribute("worklists",worklists);
        request.setAttribute("works",works);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html/charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        HttpSession id = request.getSession();
        int wspId = (int) id.getAttribute("wspID");
        if (action==null) {
            action = "";
        }
        switch (action) {
            case "edit":
                String name = request.getParameter("name_edit");
                int wspID_edit_id = Integer.parseInt(request.getParameter("wspID_edit_ID"));
                try {
                    editWorkspace(name,wspID_edit_id,request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "delete" :
                int deleteWspID = Integer.parseInt(request.getParameter("wspID"));
                try {
                    deleteWorkspace(deleteWspID,request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "add":
                String addName = request.getParameter("name_add");
                System.out.println(addName);
                try {
                    addWorkspace(addName,request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "share":
                String otherEmail = request.getParameter("orther_user_email");
                try {
                    shareWorkspace(otherEmail,wspId,request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
        }
    }

    private void shareWorkspace(String otherEmail, int shareWspID, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        User otherUser = userDAO.selectUser(otherEmail);
        if (otherUser == null) {
            request.setAttribute("alert","Email không tồn tại!");
        } else if (otherUser.equals(user)) {
            request.setAttribute("alert","Email đã quản lý workspace hiện tại");
        } else {
            workspaceDAO.shareWorkspace(otherUser.getId(),shareWspID);
        }
        loadPage(shareWspID, request, response, user);
    }

    private void loadPage(int shareWspID, HttpServletRequest request, HttpServletResponse response, User user) throws SQLException, ServletException, IOException {
        List<Workspace> workspaces = workspaceDAO.selectAllWorkspaceByEmail(user.getEmail());
        List<Workspace> favoriteWorkspace = workspaceDAO.selectAllFavoriteWorkspaceByEmail(user.getEmail());
        sendRequest(user, workspaces, favoriteWorkspace, shareWspID, request);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/home/dashboard.jsp");
        dispatcher.forward(request, response);
    }

    private void addWorkspace(String addName, HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        workspaceDAO.insertWorkspace(new Workspace(addName));
        Workspace workspace = workspaceDAO.selectWorkspaceByName(addName);
        workspaceDAO.shareWorkspace(user.getId(),workspace.getId());
        HttpSession id = request.getSession();
        id.setAttribute("wspID",workspace.getId());
        loadPage(workspace.getId(), request, response, user);
    }

    private void deleteWorkspace(int deleteWspID, HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        List<Workspace> workspaces = workspaceDAO.selectAllWorkspaceByEmail(user.getEmail());
       if (workspaces.size()>1) {
           workspaceDAO.deleteWorkspace(deleteWspID);
       } else {
           request.setAttribute("alert","Bạn đã xóa hết workspace!");
           workspaceDAO.deleteWorkspace(deleteWspID);
       }
        workspaces = workspaceDAO.selectAllWorkspaceByEmail(user.getEmail());
        List<Workspace> favoriteWorkspace = workspaceDAO.selectAllFavoriteWorkspaceByEmail(user.getEmail());
        sendRequest(user,workspaces,favoriteWorkspace,deleteWspID, request);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/home/dashboard.jsp");
        dispatcher.forward(request, response);
    }

    private void editWorkspace(String name,int wspID, HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        List<Workspace> workspaces = workspaceDAO.selectAllWorkspaceByEmail(user.getEmail());
        boolean exist = false;
        for (Workspace workspace: workspaces
             ) {
            if (workspace.getName().equals(name)) {
                exist = true;
                break;
            }
        }
        if (exist) {
            request.setAttribute("alert","Tên workspace đã tồn tại! ");
        } else {
            workspaceDAO.updateWorkspace(new Workspace(wspID,name));
        }
        workspaces = workspaceDAO.selectAllWorkspaceByEmail(user.getEmail());
        List<Workspace> favoriteWorkspace = workspaceDAO.selectAllFavoriteWorkspaceByEmail(user.getEmail());
        sendRequest(user,workspaces,favoriteWorkspace,wspID, request);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/home/dashboard.jsp");
        dispatcher.forward(request, response);
    }
}
