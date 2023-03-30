package DAL.db;

import BE.User;

import java.util.List;

public interface IUserDataAccess {
    List<User> loadUser(String username, String userPassword) throws Exception;
    boolean validate(String username, String password) throws Exception;
}
