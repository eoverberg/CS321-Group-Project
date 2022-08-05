package views.client;
// Java Program to create a popup and display
// it on a parent frame
import main.reviews.ReviewManager;
import moviedatabase.moviedata.Movie;
import moviedatabase.moviesearch.Search;
import moviedatabase.userdata.UserAccount;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//TODO:
// look into star wars acting bizarre with multiple monitors

/**
 * This is the popup view for a single movie
 */
class SingleMovieView {

    /**
     * Creates a popup with the selected movies info, with the option to add the movie to a wishlist,
     * which is entered as a number in a text box
     * @param movieToShow .
     */
    public void show(Movie movieToShow) {
        String chosenList = "1";
        JFrame jFrame = new JFrame();
        JPanel options = new JPanel(new GridLayout(8,1));
        JPanel whichList = new JPanel(new GridLayout(2,1));
        JPanel reviewBox = new JPanel(new GridLayout(2,2));
        JTextField review  = new JTextField();
        JLabel reviewHint = new JLabel("Enter your review here");
        reviewBox.add(reviewHint);
        reviewBox.add(review);
        //Movie info
        JButton reviewAdd = new JButton("Add a review!");
        JLabel title = new JLabel("Title: "+movieToShow.getTitle());
        JLabel director = new JLabel("Director: "+movieToShow.getDirector());
        JLabel rated = new JLabel("Rated: "+movieToShow.getRated());
        JLabel country = new JLabel("Country: "+movieToShow.getCountry());
        JLabel genre = new JLabel("Genre: "+movieToShow.getGenre());
        JLabel language = new JLabel("Language: "+movieToShow.getLanguage());
        JLabel similar = new JLabel("Similar Movies: "+findSimilar(movieToShow));
        JLabel runtime = new JLabel("Runtime: "+movieToShow.getRuntime());
        JLabel recordedReview;

        //adding that info into the panel
        options.add(title);
        options.add(director);
        options.add(rated);
        options.add(country);
        options.add(genre);
        options.add(language);
        options.add(runtime);
        options.add(similar);
        if(movieToShow.getUserReview()!=null){
            recordedReview = new JLabel("Review: "+movieToShow.getUserReview());
            options.add(recordedReview);
        }
        reviewAdd.addActionListener(e -> {
            int option = JOptionPane.showOptionDialog(null,reviewBox,"Write a review",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,null,null);
        if(option ==0) {
            ReviewManager allReviews = new ReviewManager();
            allReviews.addToTable(movieToShow.getImdbID(),review.getText());
            movieToShow.setUserReview(review.getText());
        }
        });

        review.addActionListener(e->{
            ReviewManager allReviews = new ReviewManager();
            allReviews.addToTable(movieToShow.getImdbID(),review.getText());
            movieToShow.setUserReview(review.getText());

        });


        options.add(reviewAdd);
        jFrame.setSize(300, 300);
        JOptionPane.showOptionDialog(jFrame,options,movieToShow.getTitle(),JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,null,null);

        /*JOptionPane.showMessageDialog(jFrame, "Title: "+movieToShow.getTitle()+"\nDirector: "+movieToShow.getDirector()+"\nRated: "+
        movieToShow.getRated()+"\nCountry:"+movieToShow.getCountry()+"\nGenre: "+movieToShow.getGenre()+"\nLanguage: "+movieToShow.getLanguage()+"\nRuntime: "+
               movieToShow.getRuntime()+"\nYear: "+movieToShow.getYear()+"\n\tSimilar:  "+findSimilar(movieToShow));
        */

    }

    /**
     * uses Erik's search functions to find a couple of movies with the same genre, put the titles of the similar movies into a string,
     * and returns it for use in the single movie dialog
     * @param base .
     * @return .
     */
    public String findSimilar(Movie base){
        List<Movie> results;
        Search test = new Search();
        String similar=" ";
        results = test.searchByGenre(base.getGenre());
        System.out.println(results);
        int count = 0;

        // loop through similar movies but no more than 4
        for (Movie aMovie : results) {
            similar += aMovie.getTitle() + ", ";
            count += 1;
            if(count >= 4){
                return similar;
            }
        }
        return similar;

    }


}


