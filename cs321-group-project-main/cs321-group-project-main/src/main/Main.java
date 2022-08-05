package main;

import views.client.Dashboard;

import views.client.LoginView;
import main.reviews.ReviewManager;

import moviedatabase.moviedata.MovieContainer;
import moviedatabase.userdata.UserAccount;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The main of the program. This coordinates the views and dashboard, as well as the event loop
 */
public class Main {
    public static void main(String []args) throws Exception {
        Lock lock = new ReentrantLock();
        MovieContainer cont = MovieContainer.getInstance();
        cont.collectMovies(null);
        

        // Creates testing logins
        UserAccount user = new UserAccount();
        user.createAccount("user", "user", null);
        user.logoutUser();
        UserAccount admin = new UserAccount();
        admin.createAccount("admin", "admin", null);
        admin.logoutUser();


        Condition finished= lock.newCondition();
        LoginView test = new LoginView();

        //locking execution to start the await process
        //Basically, I place a lock on  this main thread, then await for the login to finish to continue with the program
        lock.lock();
        try {
            test.loginLoop(finished,lock);
            finished.await();
        }
        catch (Exception e){

        }
        finally {
            lock.unlock();

        }

        BootstrapProgram bootstrapFiles = new BootstrapProgram();
        new ReviewManager(bootstrapFiles);
        new Dashboard();

    }
}
