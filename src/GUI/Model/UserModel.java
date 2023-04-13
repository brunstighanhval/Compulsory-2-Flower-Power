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

    public ObservableList<User> getObservableEventsKoordinator() {


        return eventKoordinatorsToBeViewed;
    }

    public List<User> loadUser(String username)throws Exception{
        return userManager.loadUser(username);
    }
    public boolean validate(String username) throws Exception{
        return userManager.validate(username);
    }
    public void addNewEventKoordinator(String firstName, String lastName, String username, String password, int role) throws Exception {
        createdEventKoordinator =  userManager.addNewEventKoordinator(firstName, lastName, username, password, role);
        eventKoordinatorsToBeViewed.add(createdEventKoordinator);
        eventKoordinatorsToBeViewed.clear();
        eventKoordinatorsToBeViewed.addAll(userManager.readEvK());
    }
    public User getLoggedinUser(){return user;}

    public void setLoggedinUser(User user){
        this.user = user;
    }

    public void deleteEventKoordinator(User selectedKoordinator) throws Exception {
        userManager.deleteEventKoordinator(selectedKoordinator);
        eventKoordinatorsToBeViewed.remove(selectedKoordinator);
    }
}
