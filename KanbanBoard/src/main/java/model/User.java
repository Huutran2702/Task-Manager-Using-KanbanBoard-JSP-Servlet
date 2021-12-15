package model;

public class User {
    private int id;
    private String name;
    private String email;
    private Role role;
    private String password;
    private Status status;

    public User() {
    }

    public User (String name, String email, String password) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
        this.status = Status.ACTIVE;
    }

    public User(String name, String email, Role role, String password,Status status) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
        this.status = status;
    }

    public User(int id, String name, String email, Role role, String password, Status status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static void transferFields(User oldUser, User newUser) {
        oldUser.name = newUser.name;
        oldUser.email = newUser.email;
        oldUser.role = newUser.role;
        oldUser.password = newUser.password;
        oldUser.status = newUser.status;
    }
}
