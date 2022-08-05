package moviedatabase.userdata;

import moviedatabase.moviedata.Movie;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Singlet for a currently logged-in user. This is implemented so that a user can be kept track of across multiple methods, classes, or views.
 * (There can only be one user logged in at a time, ideally)
 *
 * Many methods are a wrapper for User methods with instance-aware syntax.
 */
public class UserAccount {

    private static UserAccount instance = new UserAccount();
    private User user;

    /**
     * Makes the default user account a null, or not even a User (user needs to login as guest or otherwisee)
     */
    public UserAccount() {
        user = null;
        instance = this;
    }
    public boolean getIsAdmin(){return user.isAdmin();}

    /**
     * Gets the current instance of user, or null
     * @return this
     */
    public static UserAccount getInstance(){
        return instance;
    }
    public void logOut() throws IOException {
        user.logout();
    }

    /**
     * This will change the name of a user, this is mainly a wrapper, so there is less exposed GSON code
     * @param newName
     * @param userFile
     * @return Boolean (Username was changed)
     */
    public Boolean changeName(String newName, Path userFile) throws IOException {
        user.changeUsername(newName, null);
        return true;
    }

    /**
     * sets a new password given old and new
     * @param oldPass
     * @param newPass
     * @throws Exception
     */
    public void changePassword(String oldPass, String newPass) throws Exception {
        user.setPassword(oldPass,newPass);
    }


    public List<ArrayList<Movie>> getWishlist(){return user.getWishlistsToStore();}
    public void SetList(List<ArrayList<Movie>> listToSet){
        user.setWishlistsToStore(listToSet);
    }

    /**
     * Creates a user account given username, password, and file location (wraps user's method)
     * Also sets current user to the newly created user.
     * @param username
     * @param password
     * @param userFile
     */
    public boolean createAccount(String username, String password, Path userFile){
        // I dont like this pattern, which indicates createUser should be a util, instead of being in user
        // TODO: Make createUser a util instead of a user
        User user = new User();
        this.user = user.createUser(username, password, userFile);
        //lets other classes hinge on successful creation by returning true or false if creation was done
        if(user == null){
            return false;
        }
        return true;
    }

    /**
     * Given what you would expect to login a user, logs in the user
     * Also sets this.user to the newly logged in user (this doesn't care if someone is already logged in, there can only be one)
     * @param username
     * @param password
     * @param userFile
     * @return Bool (Login succeeded)
     */
    public Boolean login(String username, String password, Path userFile){
        User user = new User();
        this.user = user.getUser(username, password, null);
        if (this.user == null) {
            return false;
        }
        return true;
    }

    /**
     * Logs in as guest.
     */
    public void loginAsGuest(){
        this.user = new User();
        this.user.guestUser(this.user);
    }

    public String getUserName(){
        return instance.user.getUsername();
    }


    /**
     * Deletes the currently logged-in user
     * @param path
     * @return Boolean (delUser succeeded or failed)
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public Boolean delUser(Path path) throws IOException, NoSuchAlgorithmException {
        return instance.user.delUser(path);
    }

    /**
     * Logs out a user
     * shouldn't need to do anything robused when it comes to saving data as when it's modified it's already saved
     */
    public void logoutUser() {
        if (this.user != null) {
            this.user = new User();
        }
    }
}
