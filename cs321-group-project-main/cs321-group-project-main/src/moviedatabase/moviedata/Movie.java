package moviedatabase.moviedata;

/*
Here's the schema for a movie, this determines what our Movie class looks like.
{
  "$schema": "http://json-schema.org/draft-04/schema#",
  "type": "object",
  "properties": {
    "Title": {
      "type": "string"
    },
    "Year": {
      "type": "string"
    },
    "Rated": {
      "type": "string"
    },
    "Released": {
      "type": "string"
    },
    "Runtime": {
      "type": "string"
    },
    "Genre": {
      "type": "string"
    },
    "Director": {
      "type": "string"
    },
    "Writer": {
      "type": "string"
    },
    "Actors": {
      "type": "string"
    },
    "Plot": {
      "type": "string"
    },
    "Language": {
      "type": "string"
    },
    "Country": {
      "type": "string"
    },
    "Awards": {
      "type": "string"
    },
    "Poster": {
      "type": "string"
    },
    "Ratings": {
      "type": "array",
      "items": [
        {
          "type": "object",
          "properties": {
            "Source": {
              "type": "string"
            },
            "Value": {
              "type": "string"
            }
          },
          "required": [
            "Source",
            "Value"
          ]
        }
      ]
    },
    "Metascore": {
      "type": "string"
    },
    "imdbRating": {
      "type": "string"
    },
    "imdbVotes": {
      "type": "string"
    },
    "imdbID": {
      "type": "string"
    },
    "Type": {
      "type": "string"
    },
    "DVD": {
      "type": "string"
    },
    "BoxOffice": {
      "type": "string"
    },
    "Production": {
      "type": "string"
    },
    "Website": {
      "type": "string"
    },
    "Response": {
      "type": "string"
    }
  },
  "required": [
    "Title",
    "Year",
    "Rated",
    "Released",
    "Runtime",
    "Genre",
    "Director",
    "Writer",
    "Actors",
    "Plot",
    "Language",
    "Country",
    "Awards",
    "Poster",
    "Ratings",
    "Metascore",
    "imdbRating",
    "imdbVotes",
    "imdbID",
    "Type",
    "DVD",
    "BoxOffice",
    "Production",
    "Website",
    "Response"
  ]
}
 */

/**
 * Very simple attributes, with getters and setters
 */
public class Movie {
    private String Title;
    private String Year;
    private String Rated;
    private String Released;
    private String Runtime;
    private String Genre;
    private String Director;
    private String Writer;
    private String Actors;
    private String Plot;
    private String Language;
    private String Country;
    private String Awards;
    private String Poster;
    private Object Ratings;
    private String Metascore;
    private String imdbRating;
    private String imdbVotes;
    private String imdbID;
    private String Type;
    private String DVD;
    private String BoxOffice;
    private String Production;
    private String Website;
    private String Response;


    private String userReview;

    public Movie() {
    }

    public String getUserReview() {
        return userReview;
    }

    public void setUserReview(String userReview) {
        this.userReview = userReview;
    }

    public String getTitle() {
        return Title;
    }

    public String getYear() {
        return Year;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getRated() {
        return Rated;
    }

    public void setRated(String rated) {
        Rated = rated;
    }

    public String getReleased() {
        return Released;
    }

    public void setReleased(String released) {
        Released = released;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String writer) {
        Writer = writer;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getAwards() {
        return Awards;
    }

    public void setAwards(String awards) {
        Awards = awards;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public Object getRatings() {
        return Ratings;
    }

    public void setRatings(Object ratings) {
        Ratings = ratings;
    }

    public String getMetascore() {
        return Metascore;
    }

    public void setMetascore(String metascore) {
        Metascore = metascore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDVD() {
        return DVD;
    }

    public void setDVD(String DVD) {
        this.DVD = DVD;
    }

    public String getBoxOffice() {
        return BoxOffice;
    }

    public void setBoxOffice(String boxOffice) {
        BoxOffice = boxOffice;
    }

    public String getProduction() {
        return Production;
    }

    public void setProduction(String production) {
        Production = production;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}
