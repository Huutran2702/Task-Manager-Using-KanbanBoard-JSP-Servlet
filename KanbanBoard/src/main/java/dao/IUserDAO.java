package dao;

import model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    public void insertUser(User user) throws SQLException;
    public User selectUser(String email);
    public List<User> selectAllUsers();
    public boolean updateUser(User user) throws SQLException;
    boolean changeStatus(String email, String status) throws SQLException;
}
