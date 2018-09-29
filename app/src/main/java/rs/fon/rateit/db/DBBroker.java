package rs.fon.rateit.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import rs.fon.rateit.adapter.MovieItem;
import rs.fon.rateit.model.Movie;
import rs.fon.rateit.model.Rating;
import rs.fon.rateit.model.User;


/**
 * Created by pavlepavle on 17-Jun-17.
 */

public class DBBroker {
    public StorageDbHelper dbHelper;
    public SQLiteDatabase database;


    private static DBBroker instance;

    public static DBBroker vratiInstancu(Context context){
        if(instance == null){
            instance = new DBBroker(context);
        }
        return instance;
    }

    private DBBroker(Context context){
        dbHelper = new StorageDbHelper(context);
        inicijalizujFilmove();
        inicijalizujAdmine();
    }

    private void inicijalizujAdmine() {
        User adminUser = new User("admin","admin@email.com", "admin", 1);
        insertUser(adminUser);
    }


    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertUser(User u){

        try{
            open();
            ContentValues values = new ContentValues();
            values.put("Username",u.getUsername());
            values.put("Email",u.getEmail());
            values.put("Password",u.getPassword());
            values.put("Admin",u.getAdmin());
            long result = database.insert("users", null, values);
            close();
            if (result==-1) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e){
            return false;
        }
    }

    public List<User> getAllUsers(){
        open();
        List<User> listUsers = new ArrayList<>();
        Cursor cursor = database.query("users",null,null,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User u = new User(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3));
            listUsers.add(u);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return listUsers;
    }

    public boolean insertMovie(Movie m) {
        try{

            open();
            String upit1 = "select * from movies where Title = '" + m.getTitle() + "' AND Year = '" + m.getYear() + "' ;";
            Cursor cursor = database.rawQuery(upit1, null);
            if(cursor.getCount() > 0){
                cursor.close();
                close();
                return false;
            } else {
                ContentValues values = new ContentValues();
                values.put("Title", m.getTitle());
                values.put("Year", m.getYear());
                long result = database.insert("movies", null, values);
                close();
                if (result == -1) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e){
            return false;
        }
    }


    public int userLoginn(User u) {
        open();
        String upit1 = "select * from users where Username = '" + u.getUsername() + "' AND Password = '" + u.getPassword() + "' AND Admin = '1' ;";
        String upit2 = "select * from users where Username = '" + u.getUsername() + "' AND Password = '" + u.getPassword() +"' ;";
        Cursor cursor = database.rawQuery(upit1, null);
        if(cursor.getCount() > 0){
            cursor.close();
            close();
            //vraca 2 ako je admin user
            return 2;
        }
        Cursor cursor2 = database.rawQuery(upit2, null);
        if(cursor2.getCount() > 0){
            cursor2.close();
            close();
            //vraca 1 ako je obican user
            return 1;
        }
        cursor.close();
        cursor2.close();
        close();
        //vraca 0 ako ne postoji takav user
        return 0;

    }


    public List<MovieItem> getAllMoviesData() {
        open();
        List<MovieItem> listMovieItems = new ArrayList<>();
        Cursor cursor = database.query("movies",null,null,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MovieItem mi = new MovieItem(cursor.getString(1),cursor.getString(2));
            listMovieItems.add(mi);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return listMovieItems;
    }

    public void inicijalizujFilmove(){
        Movie m1 = new Movie("Memento", 2000);
        insertMovie(m1);
        Movie m2 = new Movie("Inception", 2010);
        insertMovie(m2);
        Movie m3 = new Movie("Shutter Island", 2010);
        insertMovie(m3);
        Movie m4 = new Movie("Fight Club", 1999);
        insertMovie(m4);
        Movie m5 = new Movie("Matrix", 1999);
        insertMovie(m5);
        Movie m6 = new Movie("Se7en", 1995);
        insertMovie(m6);
        Movie m7 = new Movie("The Usual Suspects", 1995);
        insertMovie(m7);
        Movie m8 = new Movie("The Prestige", 2006);
        insertMovie(m8);
        Movie m9 = new Movie("Snatch", 2000);
        insertMovie(m9);
        Movie m10 = new Movie("Logan", 2017);
        insertMovie(m10);
    }

    public Movie getMovie(String imeFilma, String godinaFilma) {
        open();
        String upit1 = "select * from movies where Title = '" + imeFilma + "' AND Year = '" + godinaFilma + "' ;";
        Cursor cursor = database.rawQuery(upit1, null);
        Movie m = null;
        while (cursor.moveToFirst()) {
            m = new Movie(cursor.getInt(0),cursor.getString(1),cursor.getInt(2));
            break;
        }
        cursor.close();
        close();
        return m;
    }

    public Boolean insertRating(Rating r) {

        try{
            open();
            String upit1 = "select * from ratings where User = '" + r.getUser().getUsername() + "' AND Movie = '" + r.getMovie().getMovieID() + "' ;";
            Cursor cursor = database.rawQuery(upit1, null);
            if(cursor.getCount() > 0){
                cursor.close();
                ContentValues values = new ContentValues();
                values.put("User", r.getUser().getUsername());
                values.put("Movie", r.getMovie().getMovieID());
                values.put("Rating",r.getOcena());
                long result = database.update("ratings",values,"User="+"'"+r.getUser().getUsername()+"'"+" and Movie="+r.getMovie().getMovieID()+";", null);
                close();
                if (result == -1) {
                    return false;
                } else {
                    return true;
                }
            } else {
                ContentValues values = new ContentValues();
                values.put("User", r.getUser().getUsername());
                values.put("Movie", r.getMovie().getMovieID());
                values.put("Rating", r.getOcena());
                long result = database.insert("ratings", null, values);
                close();
                if (result == -1) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e){
            return false;
        }

    }

    public User getUser(String username) {
        open();
        String upit1 = "select * from users where Username = '" + username + "' ;";
        Cursor cursor = database.rawQuery(upit1, null);
        User u = null;
        while (cursor.moveToFirst()) {
            u = new User(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3));
            break;
        }
        cursor.close();
        close();
        return u;
    }

    public Boolean updateUser(User user) {
        open();
        ContentValues values = new ContentValues();
        values.put("Username", user.getUsername());
        values.put("Email", user.getEmail());
        values.put("Password", user.getPassword());
        values.put("Admin", user.getAdmin());
        long result = database.update("users",values,"Username="+"'"+user.getUsername()+"';", null);
        close();
        if (result==-1){
            return false;
        } else {
            return true;
        }
    }

    public List<String> getUserRatedMovies(String username) {
        open();
        List<String> listRatingItems = new ArrayList<>();
        String upit1 = "select ratings.User, movies.Title, movies.Year, ratings.Rating from ratings join movies on ratings.Movie=movies.MovieID where User = '" + username + "' ;";
        Cursor cursor = database.rawQuery(upit1,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String ri = new String(cursor.getString(1)+" ("+cursor.getInt(2)+") \nRating: "+cursor.getInt(3));
            listRatingItems.add(ri);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return listRatingItems;
    }

    public List<String> getTop10Movies() {
        open();
        List<String> lista = new ArrayList<>();
        String upit1 = "select movies.Title, movies.Year, avg(ratings.Rating) as Rating from ratings join movies on ratings.Movie=movies.MovieID group by movies.Title order by Rating desc limit 10;";
        Cursor cursor = database.rawQuery(upit1,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String r = new String(cursor.getString(0)+" ("+cursor.getInt(1)+") \nRating: "+Math.round(cursor.getDouble(2)*100.0)/100.0);
            lista.add(r);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return lista;
    }

    public boolean deleteUser(User user) {
        open();
        long result = database.delete("users","Username="+"'"+user.getUsername()+"';", null);
        database.delete("ratings","User="+"'"+user.getUsername()+"';", null);
        close();
        if (result==-1){
            return false;
        } else {
            return true;
        }
    }

    public int getRating(String username, String title, String year) {
        open();
        String upit1 = "select ratings.User, movies.Title, movies.Year, ratings.Rating as Rating from ratings join movies on ratings.Movie=movies.MovieID WHERE User = '"+username+"' and Title = '"+title+"' and Year = "+year+ ";";
        Cursor cursor = database.rawQuery(upit1, null);
        int i=0;
        while (cursor.moveToFirst()) {
            i = cursor.getInt(3);
            break;
        }
        cursor.close();
        close();
        return i;
    }
}