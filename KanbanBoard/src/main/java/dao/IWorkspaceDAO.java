package dao;

import model.Workspace;

import java.sql.SQLException;
import java.util.List;

public interface IWorkspaceDAO {
    public void insertWorkspace(Workspace workspace) throws SQLException;
    public Workspace selectWorkspace(int id);
    public List<Workspace> selectAllWorkspaceByEmail(String email);
    public boolean deleteWorkspace(int id) throws SQLException;
    public boolean updateWorkspace(Workspace workspace) throws SQLException;
    public boolean setFavoriteToWorkspace(int id) throws SQLException;
    public void shareWorkspace(int userID, int wspID);
    public Workspace selectWorkspaceByName(String name);
    public List<Workspace> selectAllFavoriteWorkspaceByEmail(String email);
}
