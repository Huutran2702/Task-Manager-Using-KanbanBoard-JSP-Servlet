package dao;

import connection.MySQLConnection;
import exception.PrintSQLException;
import model.Work;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkDAO implements IWorkDAO{
    private static final String INSERT_WORK_SQL = "INSERT INTO works (name,description,start,finish,wlID) VALUES (?,?,?,?,?)";
    private static final String SELECT_WORK_BY_ID = "select id,name,description,start,finish,wlID from works where id =?";
    private static final String SELECT_WORK_BY_WORKLIST_ID = "select * from works where wlID = ?";
    private static final String UPDATE_WORK_SQL = "update works set name = ?,description=?,start=?,finish=?,wlID=? where id = ?";
    private static final String DELETE_WORK_SQL = "delete from works where id = ?";
    public WorkDAO() {
        MySQLConnection.init("kanban");
    }
    @Override
    public void insertWork(Work work) throws SQLException {
        System.out.println(INSERT_WORK_SQL);
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_WORK_SQL)) {
            preparedStatement.setString(1, work.getName());
            preparedStatement.setString(2, work.getDescription());
            preparedStatement.setDate(3, work.getStart());
            preparedStatement.setDate(4,work.getFinish());
            preparedStatement.setInt(5,work.getWlID());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            PrintSQLException.msg(e);
        }
    }

    @Override
    public Work selectWork(int selectID) {
        Work work = null;

        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_WORK_BY_ID)) {
            preparedStatement.setInt(1, selectID);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Date start = rs.getDate("start");
                Date finish = rs.getDate("finish");
                int wlID = rs.getInt("wlID");
                work = new Work( id,name,description,start,finish,wlID);
            }
        } catch (SQLException e) {
            PrintSQLException.msg(e);
        }
        return work;
    }

    @Override
    public List<Work> selectWorkByWorkListID(int selectID) {
        List<Work> works = new ArrayList<>();
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_WORK_BY_WORKLIST_ID)) {
            preparedStatement.setInt(1, selectID);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Date start = rs.getDate("start");
                Date finish = rs.getDate("finish");
                int wlID = rs.getInt("wlID");
                works.add(new Work(id,name,description,start,finish,wlID));
            }
        } catch (SQLException e) {
            PrintSQLException.msg(e);
        }
        return works;
    }

    @Override
    public boolean deleteWork(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_WORK_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateWork(Work work) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_WORK_SQL)) {
            statement.setString(1, work.getName());
            statement.setString(2, work.getDescription());
            statement.setDate(3,work.getStart());
            statement.setDate(4,work.getFinish());
            statement.setInt(5,work.getWlID());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

//    public static void main(String[] args) throws SQLException {
//        long millis=System.currentTimeMillis();
//        new WorkDAO().insertWork(new Work("Tham dự tiệc tất niên","Chuẩn bị xe đón sếp",new java.sql.Date(millis),new java.sql.Date(millis+100000),1));
//    }
}
