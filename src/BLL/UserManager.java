package BLL;

import BE.User;
import DAL.db.IUserDataAccess;
import DAL.db.UserDAO;

import java.io.IOException;
import java.util.List;

public class UserManager {
    private IUserDataAccess userDAO;

    public UserManager() throws IOException {userDAO = new UserDAO();}

    /**
     * Sending the list of loaded user through the BLL.
     * @param username
     * @return
     * @throws Exception
     */
    public List<User> loadUser(String username) throws Exception{return userDAO.loadUser(username);}

    /**
     * Sending the validate username method through the BLL.
     * @param username
     * @return
     * @throws Exception
     */
    public boolean validate(String username) throws Exception{return userDAO.validate(username);}

    /**
     * Sending the list of eventkoordinator through the BLL.
     * @return
     * @throws Exception
     */
    public List<User> readEvK() throws Exception{return userDAO.readEvK();}

    /**
     * Sending the create eventkoordinator method through the BLL.
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     * @param role
     * @return
     * @throws Exception
     */
    public User addNewEventKoordinator(String firstName, String lastName, String username, String password, int role) throws Exception {return userDAO.addNewEventKoordinator(firstName, lastName, username, password, role);}

    /**
     * Sending the delete eventkoordinator method through the BLL.
     * @param selectedKoordinator
     * @throws Exception
     */
    public void deleteEventKoordinator(User selectedKoordinator) throws Exception {userDAO.deleteEventKoordinator(selectedKoordinator);}
}
