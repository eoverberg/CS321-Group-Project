package views.client;

import moviedatabase.userdata.UserAccount;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static javax.swing.BoxLayout.Y_AXIS;

/**
 * The profile for the user, complete with a ton of maybe unnecessary JPanels for the sake of orientation
 */
public class UserProfile {
    private JPanel userProfile = new JPanel();

    /**
     * creates panel with user info and options to change info about the user
     * returns panel for dashboard to use
     * @return
     */
    public JPanel getProfile(){
        userProfile.setLayout(new BoxLayout(userProfile, Y_AXIS));

        UserAccount user  = UserAccount.getInstance();
        JPanel buttonPanel = new JPanel();
        JPanel textboxPanel = new JPanel();
        JPanel passwordChange = new JPanel();


        passwordChange.setLayout(new GridLayout(2,2));
        JTextField newPass= new JTextField(30);
        JTextField oldPass = new JTextField(30);
        JLabel passPrompt1 = new JLabel("Old password");
        JLabel passPrompt2 = new JLabel("New password");
        passwordChange.add(passPrompt1);
        passwordChange.add(oldPass);
        passwordChange.add(passPrompt2);
        passwordChange.add(newPass);

        JPanel usernameChange = new JPanel();
        usernameChange.setLayout(new FlowLayout());
        JLabel userPrompt = new JLabel("Enter a new Username");
        JTextField newName = new JTextField(30);
        usernameChange.add(userPrompt);
        usernameChange.add(newName);


        textboxPanel.setLayout(new BoxLayout(textboxPanel,Y_AXIS));
        JButton logout = new JButton("Logout");
        JButton changeName = new JButton("Change username");
        JButton changePass = new JButton("Change Password");
        JButton adminPanel = new JButton("Change config locations");

    adminPanel.addActionListener(e -> {
        ConfigurationView confv = new ConfigurationView();
        confv.setVisible(true);
    });

        newPass.addActionListener(e->{
            try {
                user.changePassword(oldPass.getText(),newPass.getText());
            } catch (Exception ex) {
                JLabel error = new JLabel("Incorrect previous password");
                textboxPanel.add(error);
            }
        });

        changePass.addActionListener(e->{
            passwordChange.setVisible(!passwordChange.isVisible());
        });

        changeName.addActionListener(e->{
            usernameChange.setVisible(!usernameChange.isVisible());
        });

        newName.addActionListener(e->{
            try {
                user.changeName(newName.getText(), null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            usernameChange.setVisible(false);
        });

        logout.addActionListener(e -> {
            try {
                user.logOut();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.exit(0);
        });

    buttonPanel.add(logout);
    buttonPanel.add(changeName);
    buttonPanel.add(changePass);
    textboxPanel.add(usernameChange);
    textboxPanel.add(passwordChange);

    usernameChange.setVisible(false);
    passwordChange.setVisible(false);

    userProfile.add(buttonPanel);
    userProfile.add(textboxPanel);
    userProfile.revalidate();

    if(user.getIsAdmin()){
        userProfile.add(adminPanel);
    }
    textboxPanel.setSize(100,100);

    return this.userProfile;
    }


}
