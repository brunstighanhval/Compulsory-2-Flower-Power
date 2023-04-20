package GUI.Model;

import BE.User;
import BLL.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class UserModel {
    private ObservableList<User> eventKoordinatorsToBeViewed;
    private UserManager userManager;
    private User user;
    private User createdEventKoordinator;

    public UserModel() throws Exception {
        userManager = new UserManager();
        eventKoordinatorsToBeViewed = FXCollections.observableArrayList();
        eventKoordinatorsToBeViewed.addAll(userManager.readEvK());
    }

    /**
     * Gets the list of eventkoordinator.
     * @return
     */
    public ObservableList<User> getObservableEventsKoordinator() {return eventKoordinatorsToBeViewed;}

    /**
     * Sending the list of users based on a username through the GUI.
     * @param username
     * @return
     * @throws Exception
     */
    public List<User> loadUser(String username)throws Exception{return userManager.loadUser(username);}

    /**
     * Sending the boolean value based on a username through the GUI.
     * @param username
     * @return
     * @throws Exception
     */
    public boolean validate(String username) throws Exception{return userManager.validate(username);}

    /**
     * Sending the data for making a new eventkoordinator through the GUI and adding it to the listview.
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     * @param role
     * @throws Exception
     */
    public void addNewEventKoordinator(String firstName, String lastName, String username, String password, int role) throws Exception {
        createdEventKoordinator =  userManager.addNewEventKoordinator(firstName, lastName, username, password, role);
        eventKoordinatorsToBeViewed.add(createdEventKoordinator);
        eventKoordinatorsToBeViewed.clear();
        eventKoordinatorsToBeViewed.addAll(userManager.readEvK());
    }

    /**
     * Getter and setter for the loggedin user.
     * @return
     */
    public User getLoggedinUser(){return user;}

    public void setLoggedinUser(User user){
        this.user = user;
    }

    /**
     * Sending the event koordinator for deletion through the GUI and removing it from the listview.
     * @param selectedKoordinator
     * @throws Exception
     */
    public void deleteEventKoordinator(User selectedKoordinator) throws Exception {
        userManager.deleteEventKoordinator(selectedKoordinator);
        eventKoordinatorsToBeViewed.remove(selectedKoordinator);
    }
}
