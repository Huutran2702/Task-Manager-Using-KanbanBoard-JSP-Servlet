package dao;

import model.WorkList;

import java.sql.SQLException;
import java.util.List;

public interface IWorklistDAO {
    public void insertWorkList(WorkList workList) throws SQLException;
    public WorkList selectWorkList(int id);
    public List<WorkList> selectWorkListByWorkspaceID(int id);
    public boolean deleteWorkList(int id) throws SQLException;
    public boolean updateWorkList(WorkList workList) throws SQLException;
}
