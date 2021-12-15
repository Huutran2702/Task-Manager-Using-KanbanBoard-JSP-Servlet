package dao;

import connection.MySQLConnection;
import exception.PrintSQLException;
import model.Role;
import model.Status;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO{
    private static final String INSERT_USERS_SQL = "INSERT INTO users (name,email, role, password,status) VALUES (?,?,?,?,?)";
    private static final String SELECT_USER_BY_EMAIL = "select id,name,email,role,password,status from users where email =?";
    private static final String SELECT_ALL_USERS = "select * from users";
    private static final String UPDATE_USERS_SQL = "update users set name = ?,email=?,role=?, password =?,status=? where email = ?;";
    private static final String CHANGE_STATUS_USERS_SQL = "update users set status=? where email = ?;";

    public UserDAO() {
        MySQLConnection.init("kanban");
    }

    @Override
    public void insertUser(User user) throws SQLException {
        System.out.println(INSERT_USERS_SQL);
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, Role.USER.toString());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, Status.ACTIVE.toString());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            PrintSQLException.msg(e);
        }
    }

    @Override
    public User selectUser(String selectEmail) {
        User user = null;

        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            preparedStatement.setString(1, selectEmail);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String role = rs.getString("role");
                String password = rs.getString("password");
                String status = rs.getString("status");
                user = new User( id, name, email,Role.valueOf(role),password,Status.valueOf(status));
            }
        } catch (SQLException e) {
            PrintSQLException.msg(e);
        }
        return user;
    }

    @Override
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String role = rs.getString("role");
                String password = rs.getString("password");
                String status = rs.getString("status");
                users.add(new User(id, name, email,Role.valueOf(role),password,Status.valueOf(status)));
            }
        } catch (SQLException e) {
            PrintSQLException.msg(e);
        }
        return users;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getRole().toString());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getStatus().toString());
            statement.setString(6, user.getEmail());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    @Override
    public boolean changeStatus(String email, String status) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = MySQLConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CHANGE_STATUS_USERS_SQL)) {
            statement.setString(1, status);
            statement.setString(2, email);
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

}
