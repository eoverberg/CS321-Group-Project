package views.client;

import moviedatabase.userdata.UserAccount;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * This is the view used for login, I'll let the other comment explain the lock.
 */
public class LoginView {
    private UserAccount myUser = UserAccount.getInstance();
    private JFrame myFrame = new JFrame("Login");

    /**
     * Takes a thread lock and a Condition object to signal once the user has successfully logged in
     * This is used so we don't continue execution until the user has logged in somehow
     * @param loggedIn
     * @param lock
     */
    public void loginLoop(Condition loggedIn, Lock lock){
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // close window on 'X' always
        JPanel infoSection = new JPanel(new GridLayout(3,2));
        JTextField username  = new JTextField();
        JPasswordField password = new JPasswordField();
        JButton login = new JButton("Login");
        JButton create = new JButton("Create User");
        JButton guest = new JButton("Login As Guest");
        JPanel buttonSection = new JPanel(new GridLayout(1,3));
        buttonSection.add(login);
        buttonSection.add(create);
        buttonSection.add(guest);
        infoSection.add(new JLabel("Username"));
        infoSection.add(new JLabel("Password"));
        infoSection.add(username);
        infoSection.add(password);
        myFrame.add(infoSection);
        myFrame.add(buttonSection);
        myFrame.setLayout(new GridLayout(4,4));
        JLabel error = new JLabel("Invalid Username or Password");
        JLabel myError = new JLabel("User already Exists");
        //For each of these buttons, they have to be able to lock and unlock the thread, so we need the try catch finally in each, so the can lock, signal, then unlock
        login.addActionListener(e -> {
            lock.lock();
            if (myUser.login(username.getText(), password.getText(), null)) {
                myFrame.remove(error);
                myFrame.remove(myError);
                loggedIn.signal();
                lock.unlock();
                myFrame.setVisible(false);
                return;

            }
            else {
                myFrame.remove(myError);
                myFrame.add(error);
                myFrame.revalidate();
                lock.unlock();
            }

        });

        create.addActionListener(e ->{
            lock.lock();
            if(!username.getText().equals("")) {
                if (myUser.createAccount(username.getText(), password.getText(), null)) {
                    loggedIn.signal();
                    myFrame.remove(myError);
                    myFrame.remove(error);
                    lock.unlock();
                    myFrame.setVisible(false);
                    return;
                } else {
                    myFrame.add(myError);
                    myFrame.remove(error);
                    myFrame.revalidate();
                    lock.unlock();
                }
            }
            else {
                lock.unlock();
            }
        } );
        guest.addActionListener(e->{
            lock.lock();
            myUser.loginAsGuest();
            loggedIn.signal();
            lock.unlock();
            myFrame.setVisible(false);
            return;

        });
        myFrame.setSize(700,400);
        myFrame.setVisible(true);
    }


}
