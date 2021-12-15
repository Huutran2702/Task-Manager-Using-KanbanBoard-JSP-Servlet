package dao;

import connection.MySQLConnection;
import exception.PrintSQLException;
import model.Favorite;
import model.Workspace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkspaceDAO implements IWorkspaceDAO{
    private static final String INSERT_WORKSPACES_SQL = "INSERT INTO workspaces(name,favorite) VALUES (?,?)";
    private static final String SELECT_WORKSPACE_BY_ID = "select id,name,favorite from workspaces where id =?";
    private static final String SELECT_WORKSPACE_BY_NAME = "select id,name,favorite from workspaces where name =?";
    private static final String SELECT_ALL_WORKSPACE_BY_EMAIL = "select workspaces.id,workspaces.name,workspaces.favorite from workspaces join manager_workspaces on workspaces.id = manager_workspaces.wspID join users on manager_workspaces.userID = users.id where email = ?";
    private static final String SELECT_ALL_FAVORITE_WORKSPACE_BY_EMAIL = "select workspaces.id,workspaces.name,workspaces.favorite from workspaces join manager_workspaces on workspaces.id = manager_workspaces.wspID join users on manager_workspaces.userID = users.id where email = ? and favorite = 'FAVORITE'";
    private static final String UPDATE_WORKSPACE_SQL = "update workspaces set name = ? where id = ?";
    private static final String DELETE_WORKSPACE_SQL = "delete from workspaces where id = ?";
    private static final String SET_FAVORITE_TO_WORKSPACE = "update workspaces set favorite = ? where id = ?;";
    private static final String INSERT_USER_MANAGE_WORKSPACES ="insert into manager_workspaces (userID,wspID) values (?,?);";
    public WorkspaceDAO() {
        MySQLConnection.init("kanban");
    }

    @Override
    public void insertWorkspace(Workspace workspace) throws SQLException {
        System.out.println(INSERT_WORKSPACES_SQL);
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_WORKSPACES_SQL)) {
            preparedStatement.setString(1, workspace.getName());
            preparedStatement.setString(2, workspace.getFavorite().toString() );
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            PrintSQLException.msg(e);
        }
    }

    @Override
    public Workspace selectWorkspace(int selectId) {
        Workspace workspace = null;

        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_WORKSPACE_BY_ID)) {
            preparedStatement.setInt(1, selectId);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String favorite = rs.getString("favorite");
                workspace = new Workspace(id, name, Favorite.valueOf(favorite));
            }
        } catch (SQLException e) {
            PrintSQLException.msg(e);
        }
        return workspace;
    }

    @Override
    public List<Workspace> selectAllWorkspaceByEmail(String email) {
        List<Workspace> workspaces = new ArrayList<>();
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WORKSPACE_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String favorite = rs.getString("favorite");
                workspaces.add(new Workspace(id,name,Favorite.valueOf(favorite)));
            }
        } catch (SQLException e) {
            PrintSQLException.msg(e);
        }

        return workspaces;
    }

    @Override
    public boolean deleteWorkspace(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_WORKSPACE_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateWorkspace(Workspace workspace) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_WORKSPACE_SQL)) {
            statement.setString(1, workspace.getName());
            statement.setInt(2,workspace.getId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    @Override
    public boolean setFavoriteToWorkspace(int id) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_FAVORITE_TO_WORKSPACE)) {
            statement.setString(1, "FAVORITE");
            statement.setInt(2,id);
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    @Override
    public void shareWorkspace(int userID, int wspID) {
        System.out.println(INSERT_USER_MANAGE_WORKSPACES);
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_MANAGE_WORKSPACES)) {
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2,wspID);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            PrintSQLException.msg(e);
        }
    }

    @Override
    public Workspace selectWorkspaceByName(String selectName) {
        Workspace workspace = null;

        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_WORKSPACE_BY_NAME)) {
            preparedStatement.setString(1, selectName);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String favorite = rs.getString("favorite");
                workspace = new Workspace(id, name, Favorite.valueOf(favorite));
            }
        } catch (SQLException e) {
            PrintSQLException.msg(e);
        }
        return workspace;
    }

    @Override
    public List<Workspace> selectAllFavoriteWorkspaceByEmail(String email) {
        List<Workspace> workspaces = new ArrayList<>();
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FAVORITE_WORKSPACE_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String favorite = rs.getString("favorite");
                workspaces.add(new Workspace(id,name,Favorite.valueOf(favorite)));
            }
        } catch (SQLException e) {
            PrintSQLException.msg(e);
        }

        return workspaces;
    }

}
