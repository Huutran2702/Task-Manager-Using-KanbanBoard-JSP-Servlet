package dao;

import model.Work;

import java.sql.SQLException;
import java.util.List;

public interface IWorkDAO {
    public void insertWork(Work work) throws SQLException;
    public Work selectWork(int id);
    public List<Work> selectWorkByWorkListID(int id);
    public boolean deleteWork(int id) throws SQLException;
    public boolean updateWork(Work work) throws SQLException;
}
