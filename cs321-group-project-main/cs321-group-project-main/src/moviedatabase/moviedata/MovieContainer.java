package moviedatabase.moviedata;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This is the singleton class that orchestrates our movie list as well as the GSON instance (incase it is needed twice)
 */
public class MovieContainer {
    private List<Movie> movieList;
    private Movie[] tempMovie;
    private Gson movieCollection;

    // singleton instance of the movie list
    private static MovieContainer instance;

    // private singleton constructor
    private MovieContainer() {
        List<Movie> movieList = null;
        movieCollection = new Gson();
    }

    // public getInstance
    public static MovieContainer getInstance(){
        if(instance == null) {
            instance = new MovieContainer();
        }
        return instance;
    }

    /**
     * Add a review to a movie by IMDBID
     * @param reviewMap
     */
    public void addReviews(Map<String,String> reviewMap){
        for(Movie thisMovie : movieList){
            if(reviewMap.get(thisMovie.getImdbID())!=null){
                thisMovie.setUserReview(reviewMap.get(thisMovie.getImdbID()));
            }
        }
    }

    /**
     * MovieCollection contains a collection of movies in a list (possible later a vector list)
     * @param filepath
     * @return boolean, whether or not movies were loaded
     */
    public boolean collectMovies(Path filepath) {
        if (filepath == null) {
            filepath = Paths.get("data/data.json");
        };
        try {
            Reader file = new FileReader(String.valueOf(filepath));
            Movie[] tempMovie = movieCollection.fromJson(file, Movie[].class);
            movieList = Arrays.asList(tempMovie);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public List<Movie> getMovieList() {
        return movieList;
    }
}
