package GUI.Model;

import BE.Event;
import BE.User;
import BLL.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class UserModel {
    private ObservableList<User> eventKoordinatorsToBeViewed;
    private UserManager userManager;
    private User user;

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
    public User getLoggedinUser(){return user;}

    public void setLoggedinUser(User user){
        this.user = user;
    }
}
