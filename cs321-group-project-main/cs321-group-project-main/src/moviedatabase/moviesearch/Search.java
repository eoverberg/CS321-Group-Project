package moviedatabase.moviesearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.*;

import moviedatabase.moviedata.Movie;
import moviedatabase.moviedata.MovieContainer;

import static java.util.Collections.reverse;

//TODO:
// tokenize the genre, actors, etc so we search for each
// search using "contains" for the multi-value fields

/**
 * This is the class used to search the MovieList by whichever parameter is needed
 */
public class Search {
    private final List<Movie> movieList = MovieContainer.getInstance().getMovieList();

    /**
     * takes in a string name and returns a list of all matches
     * @param name is a string
     * @return returns a list of results
     */
    public  List<Movie> searchByName(String name) {
        List<Movie> results = new ArrayList<>();
        for (Movie movie : movieList) {
            if (movie.getTitle().toLowerCase().contains(name.toLowerCase())) {
                results.add(movie);
            }
        }
        return results;
    }

    /**
     * see above
     * @param director string
     * @return list
     */
    public  List<Movie> searchByDirector(String director) {
        List<Movie> results = new ArrayList<>();
        for (Movie movie : movieList) {
            if (movie.getDirector().toLowerCase().contains(director.toLowerCase())) {
                results.add(movie);
            }
        }
        return results;
    }

    /**
     * again, see above
     * @param genre string
     * @return list
     */
    public  List<Movie> searchByGenre(String genre) {
        List<Movie> results = new ArrayList<>();
        for (Movie movie : movieList) {
            if (movie.getGenre().toLowerCase().contains(genre.toLowerCase())) {
                results.add(movie);
            }
        }
        return results;
    }

    /**
     * All these searches work the same, why are you even reading this?
     * @param actor string
     * @return list
     */
    public  List<Movie> searchByActor(String actor) {
        List<Movie> results = new ArrayList<>();
        for (Movie movie : movieList) {
            if (movie.getActors().toLowerCase().contains(actor.toLowerCase())) {
                results.add(movie);
            }
        }
        return results;
    }

    /**
     * Takes in a list and sorts it by name using compare
     * @param tempList .
     * @return .
     */
    public List<Movie> sortByName(List<Movie> tempList) {
        tempList.sort(new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return o2.getTitle().compareTo(o1.getTitle());
            }
        });
        reverse(tempList);
        return tempList;
    }

    /**
     * Takes in a list and sorts it by year
     * @param tempList .
     * @return .
     */
    public List<Movie> sortByYear(List<Movie> tempList) {
        tempList.sort(new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return o2.getYear().compareTo(o1.getYear());
            }
        });
        return tempList;
    }

    /**
     * Takes in a list and sorts it by director
     * @param tempList .
     * @return .
     */
    public List<Movie> sortByDirector(List<Movie> tempList) {
        tempList.sort(new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return o2.getDirector().compareTo(o1.getDirector());
            }
        });
        return tempList;
    }

    public Movie searchByID(String ID) {
        for (Movie movie : movieList) {
            if (movie.getImdbID().contains(ID)) {
                return movie;
            }
        }
        return null;
    }

    public List<Movie> showAll() {
        return movieList;
    }
}
