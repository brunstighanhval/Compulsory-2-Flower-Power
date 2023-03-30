package GUI.Model;

import BE.User;
import BLL.UserManager;

import java.io.IOException;
import java.util.List;

public class UserModel {
    private UserManager userManager;
    private User user;

    public UserModel() throws IOException {
        userManager = new UserManager();
    }
    public List<User> loadUser(String username, String userPassword)throws Exception{
        return userManager.loadUser(username, userPassword);
    }
    public boolean validate(String username, String password) throws Exception{
        return userManager.validate(username, password);
    }
    public User getLoggedinUser(){return user;}

    public void setLoggedinUser(User user){
        this.user = user;
    }
}
