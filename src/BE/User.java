package BE;

public class User {
    private int id;
    private String last_name;
    private String first_name;
    private String user_name;
    private String password;
    private int role;

    public User(int id, String last_name, String first_name, String user_name, String password, int role) {
        this.id = id;
        this.last_name = last_name;
        this.first_name = first_name;
        this.user_name = user_name;
        this.password = password;
        this.role = role;
    }

    /**
     * Getters and setters for the entire class.
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole(){return role;}

    /**
     *The customized toString method.
     */
    @Override
    public String toString() {
        return first_name + " " +
                last_name ;
    }
}
