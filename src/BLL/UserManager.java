package BLL;

import BE.User;
import DAL.db.IUserDataAccess;
import DAL.db.UserDAO;

import java.io.IOException;
import java.util.List;

public class UserManager {
    private IUserDataAccess userDAO;

    public UserManager() throws IOException {
        userDAO = new UserDAO();
    }
    public List<User> loadUser(String username) throws Exception{
        return userDAO.loadUser(username);
    }
    public boolean validate(String username) throws Exception{
        return userDAO.validate(username);
    }
    public List<User> readEvK() throws Exception{
    return userDAO.readEvK();
    }

    public User addNewEventKoordinator(String firstName, String lastName, String username, String password, int role) throws Exception {
        return userDAO.addNewEventKoordinator(firstName, lastName, username, password, role);
    }

    public void deleteEventKoordinator(User selectedKoordinator) throws Exception {
        userDAO.deleteEventKoordinator(selectedKoordinator);
    }
}
