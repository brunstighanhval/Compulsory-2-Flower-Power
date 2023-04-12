package DAL.db;

import BE.User;

import java.util.List;

public interface IUserDataAccess {
    List<User> loadUser(String username) throws Exception;
    boolean validate(String username) throws Exception;
    List<User> readEvK() throws Exception;
}
