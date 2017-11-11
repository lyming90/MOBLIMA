package Controller;

import Model.*;

import static Model.Constant.*;

import java.io.EOFException;
import java.io.IOException;
import java.util.*;

public class CineplexManager extends DataManager {
    private static final String FILENAME_MOVIE = "res/data/movieListing.dat";  // location of movie.dat
    private static final String FILENAME_SHOWTIME = "res/data/showtime.dat";  // location of showtime.dat
    private static final String FILENAME_STAFFACCOUNT = "res/data/staffAccount.dat";  // location of staffAccount.dat
    private static final String FILENAME_CINEMALIST = "res/data/cinemaList.dat";  // location of cinemaList.dat
    private static final String FILENAME_REVIEWLIST = "res/data/reviewList.dat"; //location of reviewList.dat
    private static final String FILENAME_BOOKINGHISTORY = "res/data/bookingHistory.dat";  // location of cinema.dat
    private static final String FILENAME_HOLIDAY = "res/data/holidayList.dat";  // location of holiday.dat
    private static final String FILENAME_SYSTEM = "res/data/system.dat"; // location of system.dat

    private static ArrayList<Movie> movieListing;
    private static HashMap<Movie, ArrayList<Showtime>> movieShowtime;
    private static HashMap<Cineplex, ArrayList<Cinema>> cinemaList;
    private static HashMap<String, String> staffAccount;
    private static ArrayList<BookingHistory> bookingHistory;
    private static HashMap<Movie, ArrayList<Review>> reviewList;
    private static HashMap<String, Holiday> holidayList;
    private static HashMap<String, Boolean> system;


    public CineplexManager() {
        /**
         * Since all methods are static, constructor can be left empty
         */
    }

    /**
     * Initialize, get all data from files
     * @return
     */
    public static boolean initialize() {
        movieListing = new ArrayList<>();
        movieShowtime = new HashMap<>();
        staffAccount = new HashMap<>();
        cinemaList = new HashMap<>();
        bookingHistory = new ArrayList<>();
        reviewList = new HashMap<>();
        holidayList = new HashMap<>();
        system = new HashMap<>();

        try {
            readSystem();  // must not have class not found exception
            readMovieListing();  // may have class not found exception
            readMovieShowtime();  // may have class not found exception
            readStaffAccount();  // may have class not found exception
            readCinemaList();  // may have class not found exception
            readBookingHistory();  // may have class not found exception
            readReviewList();  // may have class not found exception
            readHolidayList();  // may have class not found exception
            return true;
        } catch (EOFException ex) {
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        } catch (ClassNotFoundException ex) {
            return true;
        }
    }


    private static void readMovieListing() throws IOException, ClassNotFoundException {
        if (readSerializedObject(FILENAME_MOVIE) == null) movieListing = null;
        else {
            movieListing = (ArrayList) readSerializedObject(FILENAME_MOVIE);
            Collections.sort(movieListing, new Comparator<Movie>() {  // sort listing by movie status
                @Override
                public int compare(Movie o1, Movie o2) {
                    return o1.getMovieStatus().toString().compareTo(o2.getMovieStatus().toString());
                }
            });
        }
    }

    private static void readMovieShowtime() throws IOException, ClassNotFoundException {
        if (readSerializedObject(FILENAME_SHOWTIME) == null) movieShowtime = null;
        else movieShowtime = (HashMap<Movie, ArrayList<Showtime>>) readSerializedObject(FILENAME_SHOWTIME);
    }
    private static void readStaffAccount() throws IOException, ClassNotFoundException {
        if (readSerializedObject(FILENAME_STAFFACCOUNT) == null) staffAccount = null;
        else staffAccount = (HashMap<String, String>) readSerializedObject(FILENAME_STAFFACCOUNT);
    }

    private static void readCinemaList() throws IOException, ClassNotFoundException {
        if (readSerializedObject(FILENAME_CINEMALIST) == null) cinemaList = null;
        else cinemaList = (HashMap<Cineplex, ArrayList<Cinema>>) readSerializedObject(FILENAME_CINEMALIST);
    }

    private static void readBookingHistory() throws IOException, ClassNotFoundException {
        if (readSerializedObject(FILENAME_BOOKINGHISTORY) == null) bookingHistory = null;
        else bookingHistory = (ArrayList<BookingHistory>) readSerializedObject(FILENAME_BOOKINGHISTORY);
    }

    private static void readReviewList() throws IOException, ClassNotFoundException {
        if (readSerializedObject(FILENAME_REVIEWLIST) == null) reviewList = null;
        else reviewList = (HashMap<Movie, ArrayList<Review>>) readSerializedObject(FILENAME_REVIEWLIST);
    }

    private static void readHolidayList() throws IOException, ClassNotFoundException {
        if (readSerializedObject(FILENAME_HOLIDAY) == null) holidayList = null;
        else holidayList = (HashMap<String, Holiday>) readSerializedObject(FILENAME_HOLIDAY);
    }

    private static void readSystem() throws IOException, ClassNotFoundException {
        if (readSerializedObject(FILENAME_SYSTEM) == null) system = null;
        else system = (HashMap<String, Boolean>) readSerializedObject(FILENAME_SYSTEM);
    }

    private static void writeMovieListing() throws IOException {
        writeSerializedObject(FILENAME_MOVIE, movieListing);
    }

    private static void writeShowtime() throws IOException {
        writeSerializedObject(FILENAME_SHOWTIME, movieShowtime);
    }

    private static void writeCinemaList() throws IOException {
        writeSerializedObject(FILENAME_CINEMALIST, cinemaList);
    }

    private static void writeBookingHistory() throws IOException {
        writeSerializedObject(FILENAME_BOOKINGHISTORY, bookingHistory);
    }

    private static void writeReviewList() throws IOException {
        writeSerializedObject(FILENAME_REVIEWLIST, reviewList);
    }

    private static void writeHolidayList() throws IOException {
        writeSerializedObject(FILENAME_HOLIDAY, holidayList);
    }

    private static void writeSystem() throws IOException {
        writeSerializedObject(FILENAME_SYSTEM, system);
    }

    public static ArrayList<Movie> getMovieListing() {
        return movieListing;
    }

    /**
     * orderBy is true: top 5 ranking by overall rating
     * orderBy is false: top 5 ranking by ticket sales
     * @return
     */
    public static ArrayList<Movie> getTop5MovieListing() {
        boolean orderBy = system.get("movieOrder");
        ArrayList<Movie> top5 = movieListing;
        if (orderBy) {  // order by overall ratings
            Collections.sort(top5, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    return Double.compare(o2.getRating(), o1.getRating());
                }
            });
        }
        else {  // order by ticket sales
            Collections.sort(top5, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    return Double.compare(o2.getSales(), o1.getSales());
                }
            });
        }

        while (top5.size() > 5) {
            top5.remove(5);
        }

        return top5;
    }

    public static ArrayList<Showtime> getMovieShowtime(Movie movie) {
        return movieShowtime.get(movie);
    }

    public static ArrayList<Cinema> getCinemaList(Cineplex cineplex) {
        return cinemaList.get(cineplex);
    }

    public static ArrayList<BookingHistory> getBookingHistory() {
        return bookingHistory;
    }

    public static ArrayList<Review> getReviewList(Movie movie) { return reviewList.get(movie); }

    public static HashMap<String, Holiday> getHolidayList() {
        return holidayList;
    }

    public static HashMap<String, Boolean> getSystem() { return system; }

    // TODO make it efficient
    public static Cinema getCinemaByCode(String code) {
        for (Cineplex cineplex : Cineplex.values()) {
            if (getCinemaList(cineplex) == null) continue;
            for (Cinema cinema : getCinemaList(cineplex)) {
                if (cinema.getCode().equals(code)) return cinema;
            }
        }
        return null;  // not found
    }

    // TODO make it efficient, and return multiple possible results
    public static ArrayList<Movie> getMovieByTitle(String title) {
        if (movieListing == null) return null;
        ArrayList<Movie> searchResult = new ArrayList<>();
        for (Movie movie: movieListing) {
            if (movie.getTitle().toUpperCase().contains(title.toUpperCase())) searchResult.add(movie);
        }
        return searchResult;
    }

    public static void addNewListing(Movie movie) throws IOException{
        movieListing.add(movie);
        writeMovieListing();
    }

    public static void addShowtime(Movie movie, Showtime showtime) throws IOException {
        if (movieShowtime.get(movie) == null) movieShowtime.put(movie, new ArrayList<>());
        movieShowtime.get(movie).add(showtime);
        writeShowtime();
    }

    public static void logBooking(BookingHistory record) throws IOException {
        bookingHistory.add(record);
        writeBookingHistory();
    }


    public static void addNewReview(Movie movie, Review review) throws IOException {
        if(reviewList.get(movie) == null) reviewList.put(movie, new ArrayList<>());
        reviewList.get(movie).add(review);
        writeReviewList();
    }

    public static void addHoliday(String date, Holiday holiday) throws IOException {
        holidayList.put(date, holiday);
        writeHolidayList();
    }

    public static void overwriteHolidayList() throws IOException {
        writeHolidayList();
    }

    public static void overwriteSystem() throws IOException {
        writeSystem();
    }

    public static void overwriteCinemaList() throws IOException {
        writeCinemaList();
    }

    public static void overwriteShowtime() throws IOException {
        writeShowtime();
    }
    // TODO redundant?
    public static void overwriteListing() throws IOException {
        writeMovieListing();
    }

    public static void removeListing(Movie movie) throws IOException {
        movie.setMovieStatus(MovieStatus.END_OF_SHOWING);
        writeMovieListing();
    }

    public static void removeShowtime(Showtime showtime) throws IOException {
        movieShowtime.get(showtime.getMovie()).remove(showtime);
        writeShowtime();
    }

    public static boolean authentication (String username, String password) {
        if (staffAccount.get(username) == null) return false;  // username does not exist
        else return staffAccount.get(username).equals(password);  // password does not match
    }

}
