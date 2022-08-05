package views.client;

import moviedatabase.moviedata.Movie;
import moviedatabase.moviedata.MovieContainer;
//import moviedatabase.userdata.User;
import moviedatabase.moviesearch.Search;
import moviedatabase.userdata.UserAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * this is the view for a users' wishlists
 */
public class WishlistView extends Frame {
    private static List<ArrayList<Movie>> wishLists;
    private ArrayList<JButton> buttons = new ArrayList<>();
    private static JPopupMenu popup = new JPopupMenu(); // popup menu for actions
    private static JMenu submenu_add = new JMenu("Add");
    private static JMenuItem pRemove = new JMenuItem("Remove");
    private static JMenu submenu_swap = new JMenu("Swap");
    private static JPanel wishlistPanel = new JPanel();


    /**
     * Default Constructor, sets up wishlists from the file loaded wishlists
     */
    public WishlistView() {
        wishLists = UserAccount.getInstance().getWishlist();
        JFrame myFrame = new JFrame();
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // close window on 'X' always

        if (wishLists == null || wishLists.isEmpty()) {
            wishlistPanel.add(new JLabel("No Wishlists"));
        } else {
            wishlistPanel.removeAll();

            // Setup buttons for movies in wishlist
            for (int i = 0; i < wishLists.size(); i++) {
                JPanel listPanel = new JPanel();
                listPanel.add(new JLabel("Wishlist #" + i));

                for (int j = 0; j < wishLists.get(i).size(); j++) {
                    Movie testMovie = wishLists.get(i).get(j);
                    buttons.add(j, new JButton(testMovie.getTitle()));
                    buttons.get(j).addActionListener(e ->
                    {
                        SingleMovieView movieShow = new SingleMovieView();
                        movieShow.show(testMovie);
                    });

                    listPanel.add(buttons.get(j));
                    handleMouse(buttons.get(j), i, testMovie);
                }
                setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
                wishlistPanel.add(listPanel);
            }
        }
        JButton createListButton = new JButton("Create New Wishlist");
        createListButton.addActionListener(e ->
        {
            createList();
        });
        wishlistPanel.add(createListButton);

        JButton deleteListButton = new JButton("Delete Wishlist");
        //add message box to choose which list to delete
        deleteListButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                popup.removeAll();
                for (int i = 1; i < wishLists.size(); i++) {
                    JMenuItem n = new JMenuItem(String.valueOf(i));
                    popup.add(n);
                    n.addActionListener(e1 ->
                    {
                        deleteList(Integer.parseInt(e1.getActionCommand()));
                    });
                }
                popup.show(deleteListButton, e.getX(), e.getY());
                wishlistPanel.add(deleteListButton);

                // the buttons will be placed vertically, when we have a nested list, it should display in columns
                wishlistPanel.setLayout(new BoxLayout(wishlistPanel, BoxLayout.Y_AXIS));
                wishlistPanel.setVisible(true);
            }
        });
    }

    /**
     * Populates wishlist 0 with reviews from previous sessions, if they exist. If no reviews exist, an empty list is created
     * @param reviewTable
     */
    public void setReviewWishList(Map<String, String> reviewTable) { //id, review
        if (wishLists == null || wishLists.size() == 0) {
            createList();
        } else {
            Search search_movies = new Search();
            wishLists.get(0).clear(); // make sure empty
            for (Map.Entry<String, String> movie_id : reviewTable.entrySet()) {
                addMovietoList(0, search_movies.searchByID(movie_id.getKey()));
            }
        }


    }

    /**
     * Creates the GUI of the wishlist page
     * @return panel with wishlist info
     */
    public JPanel showWishlists() {
        // setup panel & frame to display
        JFrame myFrame = new JFrame();
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // close window on 'X' always
        wishlistPanel.removeAll();

        if (wishLists == null || wishLists.isEmpty()) {
            wishlistPanel.add(new JLabel("No Wishlists"));
        } else {
            wishlistPanel.removeAll();
            buttons.clear();

            // Setup buttons for movies in wishlist
            for (int i = 0; i < wishLists.size(); i++) {
                JPanel listPanel = new JPanel();
                listPanel.add(new JLabel("Wishlist #" + i));

                for (int j = 0; j < wishLists.get(i).size(); j++) {
                    Movie testMovie = wishLists.get(i).get(j);
                    buttons.add(j, new JButton(testMovie.getTitle()));
                    buttons.get(j).addActionListener(e ->
                    {
                        SingleMovieView movieShow = new SingleMovieView();
                        movieShow.show(testMovie);
                    });

                    listPanel.add(buttons.get(j));
                    handleMouse(buttons.get(j), i, testMovie);
                }
                setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
                wishlistPanel.add(listPanel);
            }
        }
        JButton createListButton = new JButton("Create New Wishlist");
        createListButton.addActionListener(e ->
        {
            createList();
        });
        wishlistPanel.add(createListButton);

        JButton deleteListButton = new JButton("Delete Wishlist");
        //add message box to choose which list to delete
        deleteListButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                popup.removeAll();
                for (int i = 1; i < wishLists.size(); i++) {
                    JMenuItem n = new JMenuItem(String.valueOf(i));
                    popup.add(n);
                    n.addActionListener(e1 ->
                    {
                        deleteList(Integer.parseInt(e1.getActionCommand()));
                    });
                }
                popup.show(deleteListButton, e.getX(), e.getY());
                wishlistPanel.add(deleteListButton);

                // the buttons will be placed vertically, when we have a nested list, it should display in columns
                wishlistPanel.setLayout(new BoxLayout(wishlistPanel, BoxLayout.Y_AXIS));
                wishlistPanel.setVisible(true);
            }
        });

        wishlistPanel.add(deleteListButton);
        // the buttons will be placed vertically, when we have a nested list, it should display in columns
        wishlistPanel.setLayout(new BoxLayout(wishlistPanel, BoxLayout.Y_AXIS));
        wishlistPanel.setVisible(true);
        return wishlistPanel;

    }

    /**
     * used to handle what happens when the mouse in clicked on a button
     *
     * @param button        button being acted upon
     * @param wishlistIndex ???
     * @param movieObj      ???
     */

    public void handleMouse(JButton button, int wishlistIndex, Movie movieObj) {
        // handle the button click event
        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getButton() == MouseEvent.BUTTON3) { // right click button

                    // popup menu setup -- create on right click to ensure popup is interacting with correct Movie
                    popup.removeAll();
                    // popup menu setup
                    popup.add(submenu_add);
                    popup.add(pRemove);
                    popup.add(submenu_swap);
                    submenu_add.removeAll();
                    submenu_swap.removeAll();


                    // handle the popup menu options
                    ActionListener menuListener = new ActionListener() {
                        public void actionPerformed(ActionEvent event) {
                            System.out.println("Popup menu item [" + event.getActionCommand() + "] was selected.");
                            if (event.getActionCommand().equals("Remove")) {
                                removeMovieFromList(wishlistIndex, movieObj);
                                System.out.println("Removing " + movieObj.getTitle() + " from list");
                            }

                        }
                    };

                    pRemove.addActionListener(menuListener);
                    for (int i = 1; i < wishLists.size(); i++) {
                        JMenuItem n = new JMenuItem(String.valueOf(i));
                        JMenuItem m = new JMenuItem(String.valueOf(i));
                        submenu_add.add(n);
                        submenu_swap.add(m);
                        n.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                addMovietoList(Integer.parseInt(e.getActionCommand()), movieObj); // uncomment when wishlist is used
                                System.out.println("Adding movie " + movieObj.getTitle() + " to list");
                            }
                        });
                        m.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                swapMovieList(movieObj, Integer.parseInt(e.getActionCommand()), wishlistIndex);
                                System.out.println("Swapping movie " + movieObj.getTitle() + " to list");
                            }
                        });
                    }
                    submenu_swap.addActionListener(menuListener);
                    submenu_add.addActionListener(menuListener);
                    popup.add(submenu_add);
                    popup.add(submenu_swap);
                    // end popup menu setup

                    // display info and popup menu
                    System.out.println("Selected " + button.getText() + " movie in wishlist #" + wishlistIndex);
                    System.out.println("Movie object: " + movieObj.getTitle() + " " + movieObj.getCountry());
                    popup.show(button, me.getX(), me.getY());
                }
            }
        });
    }

    public void handleSearchMouse(JButton button, Movie movieObj) {
        // handle the button click event
        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getButton() == MouseEvent.BUTTON3) { // right click button

                    // popup menu setup -- create on right click to ensure popup is interacting with correct Movie
                    popup.removeAll();
                    // popup menu setup
                    popup.add(submenu_add);
                    submenu_add.removeAll();


                    for (int i = 1; i < wishLists.size(); i++) {
                        JMenuItem n = new JMenuItem(String.valueOf(i));
                        submenu_add.add(n);
                        n.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                addMovietoList(Integer.parseInt(e.getActionCommand()), movieObj); // uncomment when wishlist is used
                                System.out.println("Adding movie " + movieObj.getTitle() + " to list");
                            }
                        });
                    }
                    popup.add(submenu_add);
                    // end popup menu setup

                    // display info and popup menu
                    System.out.println("Selected " + button.getText());
                    System.out.println("Movie object: " + movieObj.getTitle() + " " + movieObj.getCountry());
                    popup.show(button, me.getX(), me.getY());
                }
            }
        });
    }


    /**
     * Takes in a movie object, and its new and old location, and moves the object inside the wishlist matrix accordingly
     *
     * @param movieToMove .
     * @param newList     .
     * @param oldList     .
     */
    public void swapMovieList(Movie movieToMove, int newList, int oldList) {
        //removing movie from previous list
        wishLists.get(oldList).remove(movieToMove);
        wishLists.get(newList).add(movieToMove);
        showWishlists();
        wishlistPanel.revalidate();
    }

    public int getWishlistSize() {
        return wishLists.size();
    }

    public void createList() {
        if (wishLists == null || wishLists.isEmpty()) {
            wishLists = new ArrayList<>();
        }
        wishLists.add(new ArrayList<Movie>());
        UserAccount.getInstance().SetList(wishLists);
        showWishlists();
        wishlistPanel.revalidate();
    }

    public void deleteList(int indexToRemove) {
        wishLists.remove(indexToRemove);
        UserAccount.getInstance().SetList(wishLists);
        showWishlists();
        wishlistPanel.revalidate();
    }

    public void addMovietoList(int whichList, Movie movieToAdd) {
        wishLists.get(whichList).add(movieToAdd);
        UserAccount.getInstance().SetList(wishLists);
        showWishlists();
        wishlistPanel.revalidate();
    }

    public void removeMovieFromList(int whichList, Movie movieToRemove) {
        wishLists.get(whichList).remove(movieToRemove);
        UserAccount.getInstance().SetList(wishLists);
        showWishlists();
        wishlistPanel.revalidate();
    }


    // main method
    public static void main(String args[]) {
        //maybe do a for loop, change the view to do one wishlist at a time
        MovieContainer cont = MovieContainer.getInstance();
        cont.collectMovies(null);
        WishlistView b = new WishlistView();
        b.showWishlists();
    }

}

