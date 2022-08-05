package views.client;

import main.BootstrapProgram;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This is the view for configuring data.
 */
public class ConfigurationView extends JFrame {

    private JTextField reviewFile;
    private JButton reviewButton;

    private JTextField movieFile;
    private JButton movieButton;

    /**
     * The constructor for ConfigurationView, this creates all the components we need.
     */
    public ConfigurationView() {
        BootstrapProgram p = new BootstrapProgram();
        p.loadConfig();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PopupFactory pop = PopupFactory.getSharedInstance();
        JFrame popupFrame = new JFrame("Popup");
        JPanel popupPanel = new JPanel();
        popupPanel.setLayout(new GridLayout(1,2));
        JLabel l = new JLabel("Invalid Path!");
        JButton ok = new JButton("Close");
        popupPanel.add(l);
        popupPanel.add(ok);


        setTitle("Config");
        setSize(550,120);
        setResizable(false);
        this.reviewFile = new JTextField(p.getProperty("reviewPath"),30);
        this.reviewButton = new JButton("Submit");

        reviewButton.addActionListener(e -> {
            try {
                if (!Files.exists(Paths.get(reviewFile.getText()))){
                    Popup popup = pop.getPopup(popupFrame, popupPanel, reviewFile.getX(), reviewFile.getY());

                    ok.addActionListener(eee -> {
                        popup.hide();
                    });

                    popup.show();
                    l.requestFocus();
                }
                else {
                    p.setProperty("reviewPath", reviewFile.getText());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        System.out.println(p.getProperty("moviePath"));
        this.movieFile = new JTextField(p.getProperty("moviePath"),30);
        this.movieButton = new JButton("Submit");

        movieButton.addActionListener(e -> {
            try {
                if (!Files.exists(Paths.get(movieFile.getText()))){
                    Popup popup = pop.getPopup(popupFrame, popupPanel, movieFile.getX(), movieFile.getY());

                    ok.addActionListener(eee -> {
                        popup.hide();
                    });

                    popup.show();
                    l.requestFocus();
                } else {
                    p.setProperty("moviePath", movieFile.getText());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        setLayout(new FlowLayout());

        add(new Label("Review File:"));
        add(reviewFile);
        add(reviewButton);
        add(new Label("Movie File:"));
        add(movieFile);
        add(movieButton);
    }

}