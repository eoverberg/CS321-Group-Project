package views.client;

import moviedatabase.userdata.UserAccount;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Dashboard {

    /**
     *Creates the dashboard, that is orchestrated throughout the program
     */
    public Dashboard() {
        JFrame window = new JFrame("Dashboard");

        //sets up listener to both close the program and save all the changes made when the X is pressed
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    UserAccount.getInstance().logOut();
                } catch (IOException ex) {
                    System.out.println("logout failed");
                }
                finally{
                    System.exit(0);
                }
            }
        });

        SearchView searchPanel = new SearchView();
        WishlistView wishlistPanel = new WishlistView();
        UserProfile profilePanel = new UserProfile();

        JTabbedPane tabPanel = new JTabbedPane();
        tabPanel.setBounds(50,30,600,500);

        // tab text is white when selected on macOS
        tabPanel.add("Search", searchPanel.SearchBox());
        tabPanel.add("Wishlists", wishlistPanel.showWishlists());
        tabPanel.add("Profile", profilePanel.getProfile());
        window.add(tabPanel);
        window.setSize(tabPanel.getWidth()+100,tabPanel.getHeight()+100);

        // Dynamic resizing
        window.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                tabPanel.setSize(window.getWidth()-100,window.getHeight()-100);
                tabPanel.repaint();
            }
        });
        window.setLayout(null);
        window.setVisible(true);

    }

}
