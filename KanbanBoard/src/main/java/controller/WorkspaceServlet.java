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
        HttpSession id = request.getSession(true);
        id.setAttribute("wspID",id);
        List<Workspace> workspaces = (List<Workspace>) session.getAttribute("workspaces");
        List<Workspace> favoriteWorkspace = (List<Workspace>) session.getAttribute("favoriteWorkspace");
        sendRequest(user,workspaces,favoriteWorkspace,wspId, request);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/home/dashboard.jsp");
        dispatcher.forward(request, response);
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
        request.setAttribute("favoriteWorkspace",favoriteWorkspace );
        request.setAttribute("workspaces",workspaces );
        request.setAttribute("worklists",worklists);
        request.setAttribute("works",works);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
