package dao;

import connection.MySQLConnection;
import exception.PrintSQLException;
import model.WorkList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorklistDAO implements IWorklistDAO{
    private static final String INSERT_WORKLIST_SQL = "INSERT INTO worklists (name,wspID) VALUES (?,?)";
    private static final String SELECT_WORKLIST_BY_ID = "select id,name,wspID from worklists where id = ?";
    private static final String SELECT_WORKLIST_BY_WORKSPACE_ID = "select id,name,wspID from worklists where wspID = ?";
    private static final String UPDATE_WORKLIST_SQL = "update worklists set name = ? where id = ?";
    private static final String DELETE_WORKLIST_SQL = "delete from worklists where id = ?";

    public WorklistDAO() {
        MySQLConnection.init("kanban");
    }

    @Override
    public void insertWorkList(WorkList workList) throws SQLException {
        System.out.println(INSERT_WORKLIST_SQL);
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_WORKLIST_SQL)) {
            preparedStatement.setString(1, workList.getName());
            preparedStatement.setInt(2,workList.getWspID());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            PrintSQLException.msg(e);
        }
    }

    @Override
    public WorkList selectWorkList(int selectID) {
        WorkList workList = null;

        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_WORKLIST_BY_ID);) {
            preparedStatement.setInt(1, selectID);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                workList = new WorkList(id, name);
            }
        } catch (SQLException e) {
            PrintSQLException.msg(e);
        }
        return workList;
    }

    @Override
    public List<WorkList> selectWorkListByWorkspaceID(int selectID) {
        List<WorkList> workLists = new ArrayList<>();
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_WORKLIST_BY_WORKSPACE_ID)) {
            preparedStatement.setInt(1, selectID);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int wspID = rs.getInt("wspID");
                workLists.add(new WorkList(id, name,wspID));
            }
        } catch (SQLException e) {
            PrintSQLException.msg(e);
        }
        return workLists;
    }

    @Override
    public boolean deleteWorkList(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_WORKLIST_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateWorkList(WorkList workList) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_WORKLIST_SQL)) {
            statement.setString(1, workList.getName());
            statement.setInt(2,workList.getId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
}
