package moviedatabase.userdata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.reviews.ReviewManager;
import moviedatabase.moviedata.Movie;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * The class for a generic User, users are generally not constructed and this is a class used in UserAccount
 *
 * implements methods that make users. added as a result of !9
 */
public class User {
    private int uuid;
    private String Username;
    private String passwordHash;
    private List<ArrayList<Movie>> wishlistsToStore;
    private boolean isAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }


    public List<ArrayList<Movie>> getWishlistsToStore() {
        return wishlistsToStore;
    }

    public void setWishlistsToStore(List<ArrayList<Movie>> wishlistsToStore) {
        this.wishlistsToStore = wishlistsToStore;
    }


    /**
     * This is a guest login. Guest's shouldn't be able to leave reviews
     * (Check for uuid==-1 to prevent reviews for guests)
     */
    public User() {
        uuid = 0;
        Username = null;
        passwordHash = null;
        isAdmin = false;
    }

    public void guestUser(User u) {
        u.uuid = -1;
        u.Username = "guest";
        u.passwordHash = "";
    }

    public int getUUID() {return uuid;}
    public String getUsername() { return Username; }
    public String getPasswordHash() { return passwordHash; }

    /**
     * This is the constructor for a proper User ONLY to be used for getUser.
     * @param username
     * @param password
     * @param uuid
     * @throws NoSuchAlgorithmException
     * @throws FileNotFoundException
     */
    private User(String username, String password, Integer uuid) throws NoSuchAlgorithmException, FileNotFoundException {
        this.Username = username;
        this.passwordHash = hashPassword(password);
        this.uuid = uuid;
    }

    /**
     * Gets the absolute path to the user file, creates file if it doesn't exist
     * creates because GSON throws a fit if the file does not already exist.
     * @param userFile
     * @return
     * @throws IOException
     */
    private Path getUserFilePath(Path userFile) throws IOException {
        if (userFile == null){
            userFile = Paths.get(System.getProperty("user.home"), ".users");
        }
        // Create if not exists
        if (!Files.exists(userFile)){
            Files.createFile(userFile);

            List<User> emptyList = new ArrayList<>();
            emptyList.add(new User());
            saveUserFile(emptyList, userFile);
        }

        return userFile;
    }

    /**
     * Used for user creation, finds the next highest UUID so that they never match (these are to be matched against reviews)
     * @return
     * @throws IOException
     */
    private Integer getNextUUID(Path userFile) throws IOException {
        List<User> userList = getUserFileList(userFile);
        int uuid = 0;
        for (User u: userList) {
            if (u.uuid > uuid) {
                uuid = u.uuid;
            }
        }
        return uuid+1;
    }

    /**
     * Given a List of User, and a path, this will save the user file with all users.
     * This also overwrites the previous file (includes all old users)
     * This is not ideal for large scale, but since this is for a small-scale application it should work fine.
     * @param userList
     * @param userFile
     * @throws IOException
     */
    public void saveUserFile(List<User> userList, Path userFile) throws IOException {
        FileWriter writer = new FileWriter(String.valueOf(getUserFilePath(userFile)));
        Gson users = new Gson();
        if(this.uuid ==-1){
            users.toJson(userList, writer);
            writer.flush();
            writer.close();

        }
        else {
            userList.set(this.uuid, this);
            users.toJson(userList, writer);
            writer.flush();
            writer.close();
        }
    }

    /**
     * Creates a User, then returns User
     * This will return null if the user already exists
     * @param username
     * @param password
     * @param userFile
     * @return User or null (user already exists)
     */
    public User createUser(String username, String password, Path userFile){
        // sanitize username entry
        username = username.toLowerCase().replace(" ", "");

        // read the file and parse data
        try {
            // Find the file that stores users

            List<User> userList = getUserFileList(userFile);

            // Check if user already exists
            boolean user_found = false;

            for (User user : userList){
                if (Objects.equals(user.getUsername(), username)) {
                    user_found = true;
                    if(user.getUsername().equals("admin")) user.isAdmin = true;
                    return user; // when found, return the user
                }
            }

            // Save user to file
            User new_user = new User(username, password, getNextUUID(userFile));
            userList.add(new_user);
            new_user.saveUserFile(userList, userFile);
            return new_user;

        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * this could also be called loginUser or userLogin, (named for parity with other functions)
     * given a username, password, and path to userfile. will return a User if the user was logged in
     * returns null if the username doesn't exist, or if the password was incorrect.
     * @param username
     * @param password
     * @param userFile
     * @return
     */
    public User getUser(String username, String password, Path userFile){
        if (userFile == null) {
            userFile = Paths.get(System.getProperty("user.home"), ".users");
        }

        // sanitize username entry
        username = username.toLowerCase().replace(" ", "");
        //System.out.println(String.format("username is %s", username));
        // read the file and parse data
        try {
            List<User> userList = getUserFileList(userFile);

            // find the user
            User user_found = null;
            for (User user : userList){
                if (Objects.equals(user.getUsername(), username)){
                    user_found = user;
                    if(user_found.getUsername().equals("admin")) user_found.isAdmin=true;
                }
            }

            if (user_found == null || user_found.getUsername().equals("")) {
                // User not found, cannot login
                System.out.println("user not found");
                return null;
            }

            if (!user_found.checkPassword(password)){
                // Invalid password
                return null;
            }

            return user_found;

        } catch (FileNotFoundException e) {
            return new User();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Given that a user is ***logged in***, the user is removed from the database, and it is saved
     * @param userFile
     * @return Boolean (User was successfully deleted)
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public Boolean delUser(Path userFile) throws IOException, NoSuchAlgorithmException {
        List<User> userList = getUserFileList(userFile);

        for (User user : userList){
            if (user.getUsername().equals(this.getUsername())){
                userList.remove(user);
                saveUserFile(userList, userFile);
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the username of a user, doesn't do any matching and doesn't require authentication, since it works only off logged in users
     * NOTE: Removes spaces and makes it lower case (easier to deal with.)
     * If there is any authentication to be done on a username it should be added here, and in createUser
     * @param newName
     */
    public void changeUsername(String newName, Path userFile) throws IOException {
        // Sanitizing the name
        newName = newName.toLowerCase();
        newName = newName.replace(" ", "");

        List<User> userList = getUserFileList(getUserFilePath(userFile));
        for (User u : userList){
            if (u.getUsername().equals(Username)){
                System.out.println("succeed");
                u.setUsername(newName);
            }
        }
        System.out.println("saving");
        saveUserFile(userList, userFile);
    }

    public void setUsername(String newName){
        Username = newName;
    }

    /**
     * To change a user's password, you provide the current password, and the password to change it to
     * if the current password is wrong, the password is not change and returns false
     * @param current_password
     * @param password
     * @return Boolean (Password was changed)
     * @throws Exception
     */
    public boolean setPassword(String current_password, String password) throws Exception {
        if (!checkPassword(current_password)){
            // Password did not match and cannot be changed.
            return false;
        }
        passwordHash = hashPassword(password);
        return true;
    }

    /**
     * Given the password, it will check for equality to the hash of the current users' password
     * @param password
     * @return Boolean (Password is equal or not)
     * @throws NoSuchAlgorithmException
     */
    private Boolean checkPassword(String password) throws NoSuchAlgorithmException {
        String hash = hashPassword(password);
        //System.out.println(String.format("Password hashes %s %s", passwordHash, hash));
        return (hash.equals(passwordHash));
    }

    /**
     * Returns the MD5 hash of the string provided, Usually doesn't throw exceptions
     * @param password
     * @return String (Hash)
     * @throws NoSuchAlgorithmException
     */
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(password.getBytes());
        byte[] digest = md5.digest();
        String hash = HexFormat.of().formatHex(digest);
        return hash;
    }

    public void logout() throws IOException {
        saveUserFile(getUserFileList(null),null);
        ReviewManager saveReviews = new ReviewManager();
        saveReviews.saveReviews();

    }


    /**
     * Given the path to a file that contains JSON of users, return List\<User\>
     * This is often used to check if a user exists in the database, or to modify and save.
     * This will halt if the file does not exist. For files to exist properly, use getUser to test for user existence first using getUser.
     * @param userFile
     * @return
     * @throws FileNotFoundException
     */
    private List<User> getUserFileList(Path userFile) throws IOException {
        if (userFile == null){
            userFile = Paths.get(System.getProperty("user.home"), ".users");
        }
        userFile = getUserFilePath(userFile);
        Reader user_file = new FileReader(String.valueOf(userFile));
        Gson users = new Gson();
        Type userType = new TypeToken<ArrayList<User>>(){}.getType();
        List<User> userList = users.fromJson(user_file, userType);

        return userList;
    }
}

